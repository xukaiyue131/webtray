package com.ky_x.webtray.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ky_x.webtray.annotation.PassToken;
import com.ky_x.webtray.common.Result;
import java.io.*;
import java.util.List;

import com.ky_x.webtray.entity.MultipartFileParam;


import com.ky_x.webtray.service.FileService;
import com.ky_x.webtray.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
public class FileController {

   @Resource
   @Qualifier("fileServiceImpl")
    private FileService service;
   @Value("${File.path}")
  private  String filePath;
   @Value("${localhost.url}")
   private String url;

    @PostMapping("/upload")
    @PassToken
    public Result upload(MultipartFileParam param,HttpServletRequest request){
        String token  = request.getHeader("Authorization");
        String name = JwtUtil.getName(token,"username");
        File files = new File(filePath+name);
        if(!files.exists()){
            files.mkdir();
        }
        String path = files.getAbsolutePath();
        service.upload(param,path);
        return Result.success();
    }


    @PostMapping("/saveFile")
    public Result saveFile(@RequestBody com.ky_x.webtray.entity.File file,HttpServletRequest request){
        String token  = request.getHeader("Authorization");
        String name = JwtUtil.getName(token,"username");
        String userId = JwtUtil.getName(token,"userId");
        File dir = new File(filePath+name);
        if(!dir.exists()){
            dir.mkdir();
        }
        QueryWrapper<com.ky_x.webtray.entity.File> query = new QueryWrapper<>();
        query.eq("file_name",file.getFileName());
        com.ky_x.webtray.entity.File  one =  service.getOne(query);
        if(one!=null){
            return Result.success();
        }
        file.setType("FILE");
        file.setUrl(url+name+"/"+file.getFileName());
        file.setUserId(Integer.parseInt(userId));
        service.save(file);
        return Result.success();
    }
    //分页获取
    @GetMapping("/getFiles/{current}/{limit}")
    public Result getFiles(@PathVariable long current,@PathVariable long limit, HttpServletRequest request){
        String token  = request.getHeader("Authorization");
        String userId = JwtUtil.getName(token,"userId");
        Page<com.ky_x.webtray.entity.File> pages = new Page<>();
        QueryWrapper<com.ky_x.webtray.entity.File> query = new QueryWrapper<>();
        query.eq("user_id",userId);
        service.page(pages,query);
        List<com.ky_x.webtray.entity.File> list = pages.getRecords();
        return Result.success().data("list",list);
    }

    @GetMapping("/getFile")
    public Result getFile( HttpServletRequest request){
        String token  = request.getHeader("Authorization");
        String userId = JwtUtil.getName(token,"userId");
        QueryWrapper<com.ky_x.webtray.entity.File> query = new QueryWrapper<>();
        query.eq("user_id",userId);
        List<com.ky_x.webtray.entity.File> list = service.list(query);
        return Result.success().data("list",list);
    }

    //获取文件
    @GetMapping("/getContent")
    public void getContent(HttpServletRequest request, HttpServletResponse response,String content){
        service.getContentMessage(response,request,content);
        return ;
    }
}
