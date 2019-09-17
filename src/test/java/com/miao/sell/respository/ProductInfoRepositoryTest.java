package com.miao.sell.respository;

import com.miao.sell.entity.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/13 19:56
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    ProductInfoRepository repository;

    @Test
    public void saveTest(){

        ProductInfo productInfo=new ProductInfo("123456","煎饼果子",new BigDecimal(3.2),
                100,"好吃不贵","xxxxxx",0,3);

        ProductInfo save = repository.save(productInfo);
        Assert.assertNotNull(save);
    }

    @Test
    public void findByProductStatus() {
        //查询上架的商品
         List<ProductInfo> infoList = repository.findByProductStatus(0);
        Assert.assertNotNull(infoList);
    }
}