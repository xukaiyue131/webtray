package com.ky_x.webtray.utils;

public class StringMessageUtil {
    public static String[] getMessage(String content){
        int index =content.indexOf("82");
        content = content.substring(index+2);
        String [] s= new String[2];
        int index1 = content.lastIndexOf("/");
        s[0] = content.substring(index1+1);
        int index2=content.indexOf("/");
        s[1] = content.substring(index2+1,index1);
        return s;
    }
}
