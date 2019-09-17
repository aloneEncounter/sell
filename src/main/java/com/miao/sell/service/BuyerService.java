package com.miao.sell.service;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/26 16:38
 *
 */

import com.miao.sell.dto.OrderDTO;

public interface BuyerService {


    //查询一个订单
    OrderDTO findOrderOne(String openid, String orderId);

    //取消订单
    OrderDTO cancelOrder(String openid, String orderId);
}
