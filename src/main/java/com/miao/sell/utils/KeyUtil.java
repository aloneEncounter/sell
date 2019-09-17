package com.miao.sell.utils;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/15 12:59
 *
 */

import java.util.Random;

public class KeyUtil {

    /*
    * 生成唯一的主键
    * 格式：时间+随机数
    *当前时间的毫秒数
    *
    * 使用synchronized  保证线程的安全
    * */

    public static synchronized String getUniqueKey(){

        Random random=new Random();

        Integer number=random.nextInt(900000)+100000;

        return System.currentTimeMillis()+String.valueOf(number);
    }

}
