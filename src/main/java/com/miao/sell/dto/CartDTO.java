package com.miao.sell.dto;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/15 15:57
 *
 */

import lombok.AllArgsConstructor;
import lombok.Data;

/*
* 购物车*/
@Data
@AllArgsConstructor
public class CartDTO {

    /*商品ID*/
    private String productId;

    /*数量*/
    private Integer productQuantity;
}
