package com.ky_x.webtray.service.impl;

import com.ky_x.webtray.entity.MultipartFileParam;
import com.ky_x.webtray.mapper.FileMapper;
import com.ky_x.webtray.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ky_x.webtray.utils.FileUtil;
import com.ky_x.webtray.utils.StringMessageUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xky
 * @since 2021-05-24
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper,com.ky_x.webtray.entity.File> implements FileService {

    @Value("${File.path}")
    private String path;

    @Override
    public String upload(MultipartFileParam param,String filePath) {
        try{
            String name = param.getFile().getOriginalFilename();
            java.io.File file = new java.io.File(filePath,name);
            System.out.println(param);
            long begin = System.currentTimeMillis();
            try(
                    //将块文件写入文件中
                    InputStream fos=param.getFile().getInputStream();
                    RandomAccessFile raf =new RandomAccessFile(file,"rw")
            ) {
//                int len=-1;
                byte[] buffer=new byte[1024];
                raf.seek((param.getChunkNumber()-1)*1024*1024*1);
//                while((len=fos.read(buffer))!=-1){
//                    raf.write(buffer);
//                }
                InputStreamReader reader = new InputStreamReader(fos);

            } catch (IOException e) {
                e.printStackTrace();
                if(param.getChunkNumber()==1) {
                    file.delete();
                }
                return "exception:writeFileException";
            }
            long end = System.currentTimeMillis();
            System.out.println(end-begin);
            if(param.getChunkNumber()==param.getTotalChunks()){
                // TODO 向数据库中保存上传信息
                return "over";
            }else {
                return "ok";
            }

        }catch (Exception e){

        }

        return null;
    }

    @Override
    public void getContentMessage(HttpServletResponse response, HttpServletRequest request, String url) {
        String[] message = StringMessageUtil.getMessage(url);
        //0为文件名，1为用户名
        System.out.println(message);
        File file = new File(path+message[1]+"\\"+message[0]);
        FileUtil.export(request,response,file);
    }


}
