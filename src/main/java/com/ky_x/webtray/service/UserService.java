package com.ky_x.webtray.service;

import com.ky_x.webtray.entity.Register;
import com.ky_x.webtray.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xky
 * @since 2021-05-24
 */
public interface UserService extends IService<User> {
    public String login(String password,String email);
    public void sendMail(String email);
    public boolean register(Register user);
}
