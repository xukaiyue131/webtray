package com.ky_x.webtray.utils;

import io.swagger.annotations.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Scope(name = "prototype",description = "多例")
public class EmailTask implements Serializable {
    @Autowired
    JavaMailSender javaMailSender;

    private String  mailbox = "k13580168390@163.com";
    @Async
    public void sendAsync(SimpleMailMessage message){
        message.setFrom(mailbox);
        message.setCc(mailbox);
        javaMailSender.send(message);
    }
}
