package com.miao.sell.controller;
/*
 *卖家用户的controller
 * Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/7/5 15:17
 *
 */

import com.miao.sell.config.ProjectUrlConfig;
import com.miao.sell.constant.CookieConstant;
import com.miao.sell.constant.RedisConstant;
import com.miao.sell.entity.SellerInfo;
import com.miao.sell.enums.ResultEnum;
import com.miao.sell.service.SellerService;
import com.miao.sell.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/seller")
public class SellerUserController {


    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String, Object> map) {

        //1. openid去和数据库里的数据匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if (sellerInfo == null  ) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "/seller/order/list");
            return new ModelAndView("common/error");
        }

        //2. 设置token至redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;        //redis的过期时间
        // redis 的key  ，value  过期时间 ，时间的单位
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);

        //3. 设置token至cookie  不希望将其写死
        CookieUtil.set(response, CookieConstant.TOKEN, token, expire);

        return new ModelAndView("redirect:" + projectUrlConfig.getSell() + "/seller/order/list");

    }

    //登出功能 将cookie与redis中的值删除
    //先从cookie中获取值 在操作redis
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String, Object> map) {
        //1. 从cookie里查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null) {
            //2. 清除redis                          从cookie中获取 token  根据键删除值
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));

            //3. 清除cookie
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
        }

        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url", "/seller/order/list");
        return new ModelAndView("common/success", map);
    }

}
