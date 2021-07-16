package com.ky_x.webtray.service;

import com.ky_x.webtray.entity.Share;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ky_x.webtray.entity.ShareCode;
import com.ky_x.webtray.entity.ShareMessage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xky
 * @since 2021-05-24
 */
public interface ShareService extends IService<Share> {
    public ShareCode createShareUrl(String expireTime,String fileId);

    public ShareMessage getShareMessage(String url);
}
