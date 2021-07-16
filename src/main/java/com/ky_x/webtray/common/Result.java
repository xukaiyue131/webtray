package com.ky_x.webtray.common;
import lombok.Data;

import java.util.*;

@Data
public class Result {
    private String msg;
    private int code;
    private Map<String,Object> data = new HashMap<>();
    public static Result success(){
        Result result = new Result();
        result.setCode(200);
        result.setMsg("成功");
        return result;
    }
    public static Result error(){
        Result result = new Result();
        result.setCode(506);
        result.setMsg("请求失败");
        return result;
    }
    public  Result data(String key,Object value){
        this.data.put(key,value);
        return this;
    }
    public Result data(Map<String,Object>map){
        this.data = map;
        return this;
    }
    public Result message(String msg){
        this.msg = msg;
        return this;
    }

}
