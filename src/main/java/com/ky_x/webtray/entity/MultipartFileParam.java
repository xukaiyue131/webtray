package com.ky_x.webtray.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class MultipartFileParam implements Serializable {
    private static final long serialVersionUID = 1L;
    String taskId;
    //分片数
    private int chunkNumber;
    //分片大小
    private long chunkSize;
    //总的分片数
    private int totalChunks;
    //唯一标识
    private String identifier;
    private MultipartFile file;
}
