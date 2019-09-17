package com.miao.sell;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/13 12:16
 *
 */

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest

public class LoggerTest {

    private final Logger logger= LoggerFactory.getLogger(LoggerTest.class);
    @Test
    public void test1(){
        String name="miao";
        String password="123457";


        logger.trace("trace... ");
        logger.debug("debug..");
        logger.info("info.. ");
        logger.info("name:{},password{}",name,password);
        logger.warn("warn...");
        logger.error("error..");

    }
}
