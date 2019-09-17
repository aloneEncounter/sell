package com.miao.sell.service.impl;

import com.miao.sell.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/13 17:05
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void findOne() {
        ProductCategory category = categoryService.findOne(1);
        Assert.assertEquals(new Integer(1),category.getCategoryId());
    }

    @Test
    public void findAll() {

        List<ProductCategory> list = categoryService.findAll();
        Assert.assertNotEquals(0,list.size());
    }


    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> list = categoryService.findByCategoryTypeIn(Arrays.asList(2, 3, 4));
        Assert.assertNotEquals(0,list.size());

    }

    @Test
    public void save() {
        ProductCategory productCategory=new ProductCategory("女生专享",8);
        ProductCategory save = categoryService.save(productCategory);
        Assert.assertNotNull(save);
    }
}