package com.miao.sell.VO;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/13 22:50
 *
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import com.miao.sell.entity.ProductInfo;
import com.miao.sell.entity.ProductInfoVO;
import lombok.Data;

import java.util.List;

@Data
public class ProductVO {

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;

}
