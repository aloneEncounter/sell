package com.miao.sell.exception;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/15 12:19
 *
 */

import com.miao.sell.enums.ResultEnum;
import lombok.Getter;

//订单的 自定义异常
@Getter
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }
    //
    public SellException(Integer code,String message) {
        super(message );
        this.code = code;

    }
}
