package com.miao.sell.service;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/7/8 19:10
 *
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /*
     * param key
     * prarm value  当前时间+超时时间
     * */

    public boolean lock(String key, String value) {

        //可以设置返回true 不能设置返回false
        if (redisTemplate.opsForValue().setIfAbsent(key, value)) {

            return true;
        }

        // currentValue =a  假设有两个线程同时执行，这两个线程的value都是B  只会有其中一个线程拿到锁
        String currentValue = redisTemplate.opsForValue().get(key);
        //获取当前时间
        if (!StringUtils.isEmpty(currentValue) &&
                    Long.parseLong(currentValue) < System.currentTimeMillis()){

            //获取上一个锁的时间
            String oldValue=redisTemplate.opsForValue().getAndSet(key,value);

            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)){
                return true;
            }
        }

            return false;
    }


    /*
    * 解锁
    * */
    public void unlock(String key,String value){

        try {
            String currentValue=redisTemplate.opsForValue().get(key);
            //如果当前的值不为空 并且当前的值与传入的值相等
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)){
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
            log.error("[redis分布式锁] 解锁异常 ，{}",e );
        }

    }


}
