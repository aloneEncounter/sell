package com.miao.sell.utils;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/30 16:41
 *
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//json 格式化工具
public class JsonUtil {

    public static String toJson(Object object){
        GsonBuilder gsonBuilder=new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson=new Gson();
        return gson.toJson(object);
    }
}
