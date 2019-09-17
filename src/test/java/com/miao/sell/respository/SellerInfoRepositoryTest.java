package com.miao.sell.respository;

import com.miao.sell.entity.SellerInfo;
import com.miao.sell.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Key;

import static org.junit.Assert.*;

/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/7/5 14:04
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {

    @Autowired
    private SellerInfoRepository repository;

    @Test
    public void save(){

        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.getUniqueKey());
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("admin");
        sellerInfo.setOpenid("abc");

        SellerInfo save = repository.save(sellerInfo);
        Assert.assertNotNull(save);
    }

    @Test
    public void findByOpenid() {

        SellerInfo result = repository.findByOpenid("abc");
        Assert.assertEquals("abc",result.getOpenid());
    }
}