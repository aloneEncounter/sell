package com.miao.sell.respository;

import com.miao.sell.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/13 15:41
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    ProductCategoryRepository repository;


    @Test
    public void findOneTest() {


        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(1);

        Example<ProductCategory> example = Example.of(productCategory);
        Optional<ProductCategory> productOptional = repository.findOne(example);

        productCategory = productOptional.get();
        System.out.println(productCategory);

    }

    @Test
    @Transactional    //测试成功回滚事务
    public void saveTest() {

//        ProductCategory productCategory=new ProductCategory();
//        productCategory.setCategoryName("越光宝盒");
//        productCategory.setCategoryType(3);
//        repository.save(productCategory);

        ProductCategory productCategory1 = new ProductCategory("男生最爱", 3);
        ProductCategory save = repository.save(productCategory1);
        Assert.assertNotNull(save);    //使用断言判断
//        Assert.assertNotEquals(null,save);
    }

    @Test
    public void findByCategoryTypeInTest() {

        List<Integer> list = Arrays.asList(2, 3, 4);
        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0, result.size());


    }

}