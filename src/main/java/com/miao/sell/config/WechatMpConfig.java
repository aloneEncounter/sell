package com.miao.sell.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/27 0:59
 *
 */
@Component
public class WechatMpConfig {

    @Autowired
    WechatAccountConfig wechatAccountConfig;

    @Bean
    public WxMpService wxMpService() {

        WxMpService wxMpService = new WxMpServiceImpl();

        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage() {

        WxMpInMemoryConfigStorage memoryConfigStorage = new WxMpInMemoryConfigStorage();
        memoryConfigStorage.setAppId(wechatAccountConfig.getMpAppId());
        memoryConfigStorage.setSecret(wechatAccountConfig.getMpAppSecret());

        return  memoryConfigStorage;

    }


}
