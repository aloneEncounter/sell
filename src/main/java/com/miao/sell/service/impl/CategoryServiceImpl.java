package com.miao.sell.service.impl;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/13 16:58
 *
 */

import com.miao.sell.entity.ProductCategory;
import com.miao.sell.respository.ProductCategoryRepository;
import com.miao.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    ProductCategoryRepository repository;


    @Override
    public ProductCategory findOne(Integer category) {

        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(category);

        Example<ProductCategory> example = Example.of(productCategory);
        Optional<ProductCategory> productOptional = repository.findOne(example);

        productCategory = productOptional.get();

        return productCategory;
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}
