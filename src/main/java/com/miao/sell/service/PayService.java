package com.miao.sell.service;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/30 16:00
 *
 */

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.miao.sell.dto.OrderDTO;

public interface PayService {


    PayResponse create(OrderDTO orderDTO);

    PayResponse notify(String notifyData);

    RefundResponse refund(OrderDTO orderDTO);


}
