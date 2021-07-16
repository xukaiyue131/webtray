package com.ky_x.webtray.controller;

import com.ky_x.webtray.common.Result;

import com.ky_x.webtray.entity.ShareCode;
import com.ky_x.webtray.entity.ShareMessage;
import com.ky_x.webtray.service.ShareService;
import com.ky_x.webtray.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@CrossOrigin
public class ShareController {
    @Autowired
    RedisUtil redisUtil;
    @Resource
    @Qualifier("shareServiceImpl")
    ShareService service;
    @Value("${localhost.url}")
    private String url;
    //创建分享链接
    @GetMapping("/createShare")
    public Result  createShare(String expireTime, String fileId){
        ShareCode code = service.createShareUrl(expireTime, fileId);
        return Result.success().data("code",code);
    }
//校验提取码是否正确
    @GetMapping("CheckShare")
    public Result CheckShare(String code,String content){
        String s = (String)redisUtil.get(content);
        if(code.equals(s)){
            ShareMessage message = service.getShareMessage(content);
            return Result.success().data("message",message);
        }
        return Result.error().data("message","验证码错误");
    }

    @GetMapping("/getShareMessage")
    public Result getShareMessage(String content){
        ShareMessage message = service.getShareMessage(content);
        return Result.success().data("message",message);
    }
}
