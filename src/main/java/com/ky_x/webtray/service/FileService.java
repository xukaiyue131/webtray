package com.ky_x.webtray.service;

import com.ky_x.webtray.entity.File;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ky_x.webtray.entity.MultipartFileParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xky
 * @since 2021-05-24
 */
public interface FileService extends IService<File> {

    public String upload(MultipartFileParam param,String filePath);
    public void getContentMessage(HttpServletResponse response, HttpServletRequest request,String url);
}
