package com.miao.sell.service.impl;

import com.miao.sell.dto.OrderDTO;
import com.miao.sell.entity.OrderDetail;
import com.miao.sell.enums.OrderStatusEnum;
import com.miao.sell.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/15 16:42
 *
 */

/*
*
* */

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID="110110";

    private final String ORDER_ID="1560591170064729082";

    @Test
    public void create() {

        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerAddress("风陵渡");
        orderDTO.setBuyerPhone("123456789012");
        orderDTO.setBuyerOpenid(BUYER_OPENID);
        orderDTO.setBuyerName("黄泉");

        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("1234568");
        o1.setProductQuantity(1);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("123457");
        o2.setProductQuantity(2);

        orderDetailList.add(o1);
        orderDetailList.add(o2);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        log.info("【创建订单】result={}", result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {

        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        log.info("【查询单个订单】result={}",orderDTO);
        Assert.assertEquals(ORDER_ID,orderDTO.getOrderId());
    }

    @Test
    public void findList() {
        Page<OrderDTO> page = orderService.findList(BUYER_OPENID, PageRequest.of(0, 2));

        Assert.assertNotEquals(0,page.getTotalElements());
    }


    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
    }

    @Test
    public void paid() {
        Page<OrderDTO> page = orderService.findList(PageRequest.of(0, 2));
        Assert.assertNotEquals(0,page.getTotalElements());

    }




    @Test
    public void findList1() {
        Page<OrderDTO> page = orderService.findList(PageRequest.of(0, 2));

        Assert.assertNotEquals(0,page.getTotalElements());

    }
}