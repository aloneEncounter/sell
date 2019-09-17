package com.miao.sell.service;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/7/5 20:57
 * 推送消息
 */

import com.miao.sell.dto.OrderDTO;

public interface PushMessageService {

    /**
     * 订单状态变更消息
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);

}
