package com.miao.sell.utils;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/7/3 21:45
 *
 */

import com.miao.sell.enums.CodeEnum;

//自定义一个枚举类
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code,Class<T> enumClass ){

        for (T each:enumClass.getEnumConstants()){
            if (code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }
}
