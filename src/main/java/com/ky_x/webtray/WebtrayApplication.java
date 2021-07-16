package com.ky_x.webtray;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.ky_x.webtray")
public class WebtrayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebtrayApplication.class, args);
    }

}
