package com.miao.sell.respository;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/14 20:52
 *
 */

import com.miao.sell.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

    //根据用户 id  查询所有的id   并且分页
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenId, Pageable pageable);

}
