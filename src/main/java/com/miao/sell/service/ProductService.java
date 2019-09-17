package com.miao.sell.service;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/13 20:33
 *
 */

import com.miao.sell.dto.CartDTO;
import com.miao.sell.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductInfo findOne(String productId);
    /*
    * 查询所有 在架商品列表 */
    List<ProductInfo> findUpAll();

    /*管理员 查询*/
    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);

}
