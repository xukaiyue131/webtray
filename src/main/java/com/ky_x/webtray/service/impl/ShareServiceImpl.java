package com.ky_x.webtray.service.impl;

import com.ky_x.webtray.entity.Share;
import com.ky_x.webtray.entity.ShareCode;
import com.ky_x.webtray.entity.ShareMessage;
import com.ky_x.webtray.mapper.ShareMapper;
import com.ky_x.webtray.service.ShareService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ky_x.webtray.utils.RedisUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class ShareServiceImpl extends ServiceImpl<ShareMapper, Share> implements ShareService {

    @Autowired
    RedisUtil redisUtil;
    @Value("${localhost.url}")
    private String url;
    @Override
    public ShareCode createShareUrl(String expireTime, String fileId) {
        //随机生成一个24位的数
        String code_url = UUID.randomUUID().toString();
        String content = code_url.substring(0, 24);
        content = DigestUtils.md5Hex(content);
        String code = UUID.randomUUID().toString();
        code = code.substring(0,4);
        Share share = new Share();
        content = "http://localhost:8080/#/"+"share/"+content;
        share.setCode(code);
        share.setContent(content);
        share.setFileId(Integer.parseInt(fileId));
        baseMapper.insert(share);
        ShareCode shareCode = new ShareCode(code,content);
        if(Long.parseLong(expireTime)==-1){
            redisUtil.set(content,code);
        }else{
            redisUtil.set(content,code,Long.parseLong(expireTime));
        }
        return shareCode;
    }

    @Override
    public ShareMessage getShareMessage(String url) {

        return baseMapper.getShareMessage(url);
    }
}
