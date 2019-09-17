package com.miao.sell.controller;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/30 15:52
 *
 */

import com.lly835.bestpay.model.PayResponse;
import com.miao.sell.dto.OrderDTO;
import com.miao.sell.enums.ResultEnum;
import com.miao.sell.exception.SellException;
import com.miao.sell.service.OrderService;
import com.miao.sell.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @RequestMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl")String returnUrl,
                               Map<String,Object> map){

        //查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //发起支付
        PayResponse payResponse = payService.create(orderDTO);

        map.put("payRespons",payResponse);
        map.put("returnUrl",returnUrl);

        return new ModelAndView("pay/create",map);
    }

    //接收微信异步通知
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData){

        payService.notify(notifyData);

        //返回给微信处理结果
        return new ModelAndView("pay/success");

    }
}
