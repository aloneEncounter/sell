package com.miao.sell.respository;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/14 21:05
 *
 */

import com.miao.sell.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,String>{

    //一个订单对应多个商品
    List<OrderDetail> findByOrderId(String orderId);

}
