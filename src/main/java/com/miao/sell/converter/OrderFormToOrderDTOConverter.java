package com.miao.sell.converter;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/16 19:57
 *
 */

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.miao.sell.dto.OrderDTO;
import com.miao.sell.entity.OrderDetail;
import com.miao.sell.enums.ResultEnum;
import com.miao.sell.exception.SellException;
import com.miao.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.gson.GsonProperties;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderFormToOrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm) {

        //将字符串的json转换成 List
        Gson gson = new Gson();

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList=gson.fromJson(orderForm.getItems(),    //将json转换成List
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
        } catch (Exception e) {
            log.error("【对象转换】错误，string={}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;

    }
}
