package com.miao.sell.respository;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/13 15:39
 *
 */

import com.miao.sell.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository  extends JpaRepository<ProductCategory,Integer> {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
