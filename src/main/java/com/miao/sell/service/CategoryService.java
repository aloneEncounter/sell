package com.miao.sell.service;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/13 16:53
 *
 */

import com.miao.sell.entity.ProductCategory;

import java.util.List;

public interface CategoryService {

    ProductCategory findOne(Integer category);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
