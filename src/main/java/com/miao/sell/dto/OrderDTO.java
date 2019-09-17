package com.miao.sell.dto;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/15 11:40
 *
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.miao.sell.entity.OrderDetail;
import com.miao.sell.enums.OrderStatusEnum;
import com.miao.sell.enums.PayStatusEnum;
import com.miao.sell.utils.EnumUtil;
import com.miao.sell.utils.serializer.DateToLongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//@JsonInclude(JsonInclude.Include.NON_NULL)      //以json 格式返回不为空的字符串
public class OrderDTO {

    /** 订单id. */
    private String orderId;

    /** 买家名字. */
    private String buyerName;

    /** 买家手机号. */
    private String buyerPhone;

    /** 买家地址. */
    private String buyerAddress;;

    /** 买家微信Openid. */
    private String buyerOpenid;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /** 订单状态, 默认为0新下单. */
    private Integer orderStatus;

    /** 支付状态, 默认为0未支付. */
    private Integer payStatus;

    /** 创建时间. */
    // 该注解将时间序列化成时间戳  以秒为单位
    @JsonSerialize(using = DateToLongSerializer.class)
    private Date createTime;

    /** 更新时间. */
    @JsonSerialize(using = DateToLongSerializer.class)
    private Date updateTime;

    List<OrderDetail> orderDetailList;


    //通过Code 获取枚举
    @JsonIgnore   //转成json 格式后会忽略该字段

    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);
    }

    //通过Code 获取枚举
    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus,PayStatusEnum.class);
    }
}
