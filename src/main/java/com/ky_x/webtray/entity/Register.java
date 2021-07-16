package com.ky_x.webtray.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Register implements Serializable {
    private static final long serialVersionUID = 1L;
    private String password;
    private String nickname;
    private String code;
    private String email;
}
