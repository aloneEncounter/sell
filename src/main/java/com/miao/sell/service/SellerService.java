package com.miao.sell.service;
/*
 *卖家端的service
 * Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/7/5 14:17
 *
 */

import com.miao.sell.entity.SellerInfo;

public interface SellerService {

    /*
    * 通过openid查询卖家端信息
    *
    * */
    SellerInfo findSellerInfoByOpenid(String openid);
}
