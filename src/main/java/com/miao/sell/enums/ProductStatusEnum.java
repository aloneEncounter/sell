package com.miao.sell.enums;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/13 20:51
 *
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public enum ProductStatusEnum {

    UP(0,"在架"),
    DOWN(1,"下架");


    private Integer code;

    private String message;



}
