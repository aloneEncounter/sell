package com.miao.sell.entity;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/13 17:25
 *
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfo {

    @Id
    private String productId;

    private String productName;        //商品名称

    private BigDecimal productPrice;       //商品的单价

    private Integer productStock;      //库存

    private String productDescription;    //商品描述

    private String productIcon;       //商品的小图

    private Integer productStatus;   //商品的状态   0正常 1下架

    private Integer categoryType;     //类目编号

//    private Date createTime;
//
//    private Date updateTime;

}
