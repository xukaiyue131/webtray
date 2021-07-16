package com.ky_x.webtray.mapper;

import com.ky_x.webtray.entity.Share;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ky_x.webtray.entity.ShareMessage;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xky
 * @since 2021-05-24
 */
public interface ShareMapper extends BaseMapper<Share> {
    public ShareMessage getShareMessage(String url);

}
