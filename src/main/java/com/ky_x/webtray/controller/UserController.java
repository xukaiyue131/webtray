package com.ky_x.webtray.controller;

import com.ky_x.webtray.annotation.PassToken;
import com.ky_x.webtray.common.Result;
import com.ky_x.webtray.entity.Register;
import com.ky_x.webtray.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@CrossOrigin
public class UserController {

    @Resource
    @Qualifier("userServiceImpl")
    UserService service;

    @GetMapping("login")
    @PassToken
    public Result login(@RequestParam String password,@RequestParam String email){
       String token =  service.login( password, email);
       if(token==null){
           return Result.error().data("message","该用户不存在或账号密码错误");
       }
       return Result.success().data("token",token);
    }

    @PostMapping("register")
    @PassToken
    public Result register(@RequestBody Register register){
        boolean flag = service.register(register);
        return flag==false?Result.error().data("message","验证码错误"):Result.success().data("message","注册成功");
    }
    @GetMapping("sendMail")
    @PassToken
    public Result sendMail(@RequestParam String email){
        service.sendMail(email);
        return Result.success().data("message","发送成功");
    }
}
