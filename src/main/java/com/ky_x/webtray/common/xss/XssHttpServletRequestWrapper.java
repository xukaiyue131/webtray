package com.ky_x.webtray.common.xss;



import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.util.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;

//防xss跨站攻击
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        //获取请求的原始数据
        String parameter = super.getParameter(name);
        //将数据进行转义
        if(!StringUtils.isEmpty(parameter)){
             parameter = HtmlUtil.filter(parameter);
        }
        return parameter;
    }

    @Override
    public String[] getParameterValues(String name) {
        //将请求值进行转义
        String[] values = super.getParameterValues(name);
        if(values!=null){
            for(int i = 0;i<values.length;i++){
                if(!StringUtils.isEmpty(values[i])){
                    values[i] = HtmlUtil.filter(values[i]);
                }
            }
        }
        return values;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String,String[]>map = super.getParameterMap();
        LinkedHashMap<String,String[]> maps = new LinkedHashMap<>();
        if(!map.isEmpty()){
            for(String key:map.keySet()){
                String[] values = map.get(key);
                if(values!=null){
                    for(int i = 0;i<values.length;i++){
                       if(!StringUtils.isEmpty(values[i])){
                           values[i] = HtmlUtil.filter(values[i]);
                       }
                    }
                }
                maps.put(key,values);
            }
        }
        return maps;
    }

    @Override
    public String getHeader(String name) {
        //对请求头进行处理
        String header = super.getHeader(name);
        if(!StringUtils.isEmpty(header)){
            header = HtmlUtil.filter(header);
        }
        return header;
    }
    //springmvc从这里提取数据，然后封装到对象里边,主要转义方法
    @Override
    public ServletInputStream getInputStream() throws IOException {
        InputStream in= super.getInputStream();
        InputStreamReader reader=new InputStreamReader(in, Charset.forName("UTF-8"));
        BufferedReader buffer=new BufferedReader(reader);
        StringBuffer body=new StringBuffer();
        String line=buffer.readLine();
        while(line!=null){
            body.append(line);
            line=buffer.readLine();
        }
        buffer.close();
        reader.close();
        in.close();
        Map<String,Object> map=JSONUtil.parseObj(body.toString());
        Map<String,Object> result=new LinkedHashMap<>();
        for(String key:map.keySet()){
            Object val=map.get(key);
            if(val instanceof String){
                if(!StrUtil.hasEmpty(val.toString())){
                    result.put(key,HtmlUtil.filter(val.toString()));
                }
            }
            else {
                result.put(key,val);
            }
        }
        String json=JSONUtil.toJsonStr(result);
        ByteArrayInputStream bain=new ByteArrayInputStream(json.getBytes());
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bain.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
    }
}
