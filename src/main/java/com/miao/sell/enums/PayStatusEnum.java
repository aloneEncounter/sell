package com.miao.sell.enums;

import lombok.Getter;

/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/14 20:34
 *
 */
@Getter
public enum PayStatusEnum implements CodeEnum {

    WAIT(0,"等待支付"),
    SUCCESS(1,"支付成功"),

    ;

    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
