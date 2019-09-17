package com.miao.sell.service.impl;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/7/5 14:22
 *
 */

import com.miao.sell.entity.SellerInfo;
import com.miao.sell.respository.SellerInfoRepository;
import com.miao.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository repository;


    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}
