package com.miao.sell.service.impl;

import com.miao.sell.entity.ProductInfo;
import com.miao.sell.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/13 21:02
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    ProductServiceImpl productService;

    @Test
    public void findOne() {

        ProductInfo productInfo = productService.findOne("123456");
        Assert.assertEquals("123456",productInfo.getProductId());
    }

    @Test
    public void findUpAll() {

        List<ProductInfo> list = productService.findUpAll();
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void findAll() {


        Page<ProductInfo> page = productService.findAll(PageRequest.of(0,2));

        System.out.println(page.getTotalElements());
         Assert.assertNotEquals(0,page.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo productInfo=new ProductInfo("123457","皮皮虾",new BigDecimal(3.2),
                100,"好吃不贵","xxxxxx", ProductStatusEnum.DOWN.getCode(),3);

        ProductInfo save = productService.save(productInfo);

        Assert.assertNotNull(save);


    }
}