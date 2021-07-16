package com.ky_x.webtray.controller;

import com.ky_x.webtray.common.Result;
import com.ky_x.webtray.service.FileService;
import com.ky_x.webtray.utils.StringMessageUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
public class DownloadController {
    @Value("${File.path}")
    private  String filePath;
    @Resource
    @Qualifier("fileServiceImpl")
    private FileService service;
    @GetMapping("/download")
    public void download(String url, HttpServletRequest request, HttpServletResponse response){

        service.getContentMessage(response,request,url);
        return ;
    }

}
