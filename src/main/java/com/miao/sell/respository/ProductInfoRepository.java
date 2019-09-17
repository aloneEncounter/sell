package com.miao.sell.respository;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/13 17:38
 *
 */

import com.miao.sell.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo,Integer> {

    //根据商品的状态查询商品
    List<ProductInfo> findByProductStatus(Integer productStatus);

}
