package com.miao.sell.entity;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/7/5 13:59
 *
 */

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;


@Data
@Entity
public class SellerInfo {

    @Id
    private String sellerId;

    private String username;

    private String password;

    private String openid;
}
