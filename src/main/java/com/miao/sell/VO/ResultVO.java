package com.miao.sell.VO;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/13 21:45
 *
 */

import lombok.Data;

import javax.persistence.Table;

// http 请求返回最外层对象
@Data
public class ResultVO<T> {

    /*错误码*/
    private Integer  code;

    /*提示信息*/
    private String msg;

    /*具体内容*/
    private T data;


}
