package com.miao.sell.service.impl;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/13 20:39
 *
 */

import com.miao.sell.dto.CartDTO;
import com.miao.sell.entity.ProductInfo;
import com.miao.sell.enums.ProductStatusEnum;
import com.miao.sell.enums.ResultEnum;
import com.miao.sell.exception.SellException;
import com.miao.sell.respository.ProductInfoRepository;
import com.miao.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {

        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(productId);
        Example<ProductInfo> example = Example.of(productInfo);
        Optional<ProductInfo> optional = repository.findOne(example);

        productInfo = optional.get();

        return productInfo;
    }

    @Override
    public List<ProductInfo> findUpAll() {

        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    /*查询所有有分页*/
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
//    @Transactional    //开启事务
    public void increaseStock(List<CartDTO> cartDTOList) {

        for (CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo = this.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result=productInfo.getProductStock()+cartDTO.getProductQuantity();
            productInfo.setProductStock(result);

            repository.save(productInfo);
        }

    }

    @Override
    @Transactional     //开启数据库的事务
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = this.findOne(cartDTO.getProductId());   //根据id查产品信息
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();   //减库存
            if (result<0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }

    }
}
