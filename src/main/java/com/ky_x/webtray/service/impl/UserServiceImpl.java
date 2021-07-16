package com.ky_x.webtray.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ky_x.webtray.entity.Register;
import com.ky_x.webtray.entity.User;
import com.ky_x.webtray.mapper.UserMapper;
import com.ky_x.webtray.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ky_x.webtray.utils.EmailTask;
import com.ky_x.webtray.utils.JwtUtil;
import com.ky_x.webtray.utils.RedisUtil;
import org.apache.commons.codec.digest.DigestUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xky
 * @since 2021-05-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    EmailTask task;
    @Autowired
    RedisUtil redisUtil;
    @Override
    public String login(String password, String email) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("password", DigestUtils.md5Hex(password));
        query.eq("email",email);
        User user = baseMapper.selectOne(query);
        if(user==null){
            return null;
        }
        String token = JwtUtil.createToken(user.getId(),user.getNickname());
        redisUtil.set(token,user.getId(),30);
        return token;
    }

    @Override
    public void sendMail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("欢迎您来到个人网盘系统");
        message.setTo(email);
        String code = UUID.randomUUID().toString();
        code = code.substring(0,6);
        message.setText("您的注册码为"+"       "+code);
        task.sendAsync(message);
        redisUtil.set(email,code,900);
    }

    @Override
    public boolean register(Register register) {
        String email = register.getEmail();
        String code = (String)redisUtil.get(email);
        if(!code.equals(register.getCode())){
          return false;
        }
        User  user = new User();
        BeanUtils.copyProperties(register,user);
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        int insert = baseMapper.insert(user);
        return true;
    }
}
