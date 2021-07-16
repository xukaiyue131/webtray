package com.ky_x.webtray.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShareCode implements Serializable {
    public ShareCode(String code,String url){
        this.code = code;
        this.url =url;
    }
    public String code;
    public String url;
}
