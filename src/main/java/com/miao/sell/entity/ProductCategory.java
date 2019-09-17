package com.miao.sell.entity;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/13 15:06
 *
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

//使用JPA注解配置映射关系
@Entity       //告诉JPA 这是一个实体类（和数据表映射的类）
//@Table   //@Table 来指定和那个数据表对应；如果省略默认表名时类名小写
@DynamicUpdate    //动态的更新时间
@Data   //默认生成 get  set  tostring
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory {

    /*
    * 类目 id*/
    @Id
    @GeneratedValue
    private Integer categoryId;

    /*类目名*/
    private String categoryName;

    /*类目编号*/
    private Integer categoryType;


    private Date createTime;

    private Date updateTime;



    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
