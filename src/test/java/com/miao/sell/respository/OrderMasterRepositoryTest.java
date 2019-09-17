package com.miao.sell.respository;

import com.miao.sell.entity.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/14 21:10
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    private final String OPENID="664330242";

    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123456");
        orderMaster.setBuyerName("师兄");
        orderMaster.setBuyerPhone("123456789123");
        orderMaster.setBuyerAddress("黄泉路 ");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(2.5));

        OrderMaster result = repository.save(orderMaster);
        Assert.assertNotNull(result);

    }

    @Test
    public void findByBuyerOpenid() {
        //从第一页开始  每页一条记录
        Page<OrderMaster> page = repository.findByBuyerOpenid(OPENID, PageRequest.of(0, 1));

        Assert.assertNotEquals(0, page.getTotalElements());    //判断得到的页面参
    }
}