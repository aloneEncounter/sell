package com.miao.sell.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/16 18:59
 *
 */

//进行表单的校验
@Data
public class OrderForm {

    /*
     * 卖家姓名
     * */
    @NotEmpty(message = "姓名必填")
    private String name;

    /*
    * 买家手机号和
    * */
    @NotEmpty(message = "手机号必填")
    private String phone;

    /*
    * 买家地址
    * */
    @NotEmpty(message = "地址必填")
    private String address;

    /*
     * 买家微信openId
     * */
    @NotEmpty(message = "openid必填")
    private String openid;


    /*
    * 购物车
    * */
    @NotEmpty(message = "购物车不能为空")
    private String items;


}
