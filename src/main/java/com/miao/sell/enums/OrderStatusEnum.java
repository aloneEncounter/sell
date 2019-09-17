package com.miao.sell.enums;

import lombok.Data;
import lombok.Getter;

/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/14 20:27
 *
 */
//实现CodeEnum 接口 实现code的转换
@Getter
public enum OrderStatusEnum implements CodeEnum {

    NEW(0,"新订单"),
    FINISHED(1,"完结"),
    CANCEL(2,"已取消"),
    ;

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
