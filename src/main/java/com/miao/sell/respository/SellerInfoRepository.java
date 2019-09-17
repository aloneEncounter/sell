package com.miao.sell.respository;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/7/5 14:02
 *
 */

import com.miao.sell.entity.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoRepository extends JpaRepository<SellerInfo,String> {

    SellerInfo findByOpenid(String opendid);
}
