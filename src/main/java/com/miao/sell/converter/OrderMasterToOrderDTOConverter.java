package com.miao.sell.converter;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/15 20:14
 *
 */

import com.miao.sell.dto.OrderDTO;
import com.miao.sell.entity.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMasterToOrderDTOConverter {

    //将orderMaster 中的属性 复制到orderDTO中
    public static OrderDTO convert(OrderMaster orderMaster){

        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);

        return orderDTO;
    }

//    将orderMasterList 中的属性 转成 OrderDTO
    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList){

       return orderMasterList.stream()
                      .map(e->convert(e))
                      .collect(Collectors.toList());

    }
}
