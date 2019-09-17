package com.miao.sell.handler;

import com.miao.sell.VO.ResultVO;
import com.miao.sell.config.ProjectUrlConfig;
import com.miao.sell.exception.ReponseBankException;
import com.miao.sell.exception.SellException;
import com.miao.sell.exception.SellerAuthorizeException;
import com.miao.sell.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/7/5 18:36
 *
 */
// @ControllerAdvice 注解定义全局异常处理类
@ControllerAdvice
public class SellExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    //@ExceptionHandler 注解声明异常处理方法
    //拦截登陆异常  出现异常 跳回登陆页面
    //http://sell.natapp4.cc/sell/wechat/qrAuthorize?returnUrl=http://sell.natapp4.cc/sell/seller/login
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException(){
        return new ModelAndView("redirect:"
              .concat(projectUrlConfig.getWechatOpenAuthorize())
              .concat("/sell/wechat/qrAuthorize")
              .concat("?returnUrl=")
              .concat(projectUrlConfig.getSell())
              .concat("/sell/seller/login"));
    }

    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handlerSellerException(SellException e){

        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }
    //模拟返回状态吗
    @ExceptionHandler(value = ReponseBankException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResultVO handlerReponseBankException(SellException e){

        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }
}
