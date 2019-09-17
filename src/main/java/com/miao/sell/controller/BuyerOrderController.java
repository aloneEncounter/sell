package com.miao.sell.controller;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/16 14:28
 *
 */

import com.miao.sell.VO.ResultVO;
import com.miao.sell.converter.OrderFormToOrderDTOConverter;
import com.miao.sell.dto.OrderDTO;
import com.miao.sell.enums.ResultEnum;
import com.miao.sell.exception.SellException;
import com.miao.sell.form.OrderForm;
import com.miao.sell.service.BuyerService;
import com.miao.sell.service.impl.OrderServiceImpl;
import com.miao.sell.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private BuyerService buyerService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
                                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.error("【创建订单参数不正确】orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),    //抛出异常 及异常的信息
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderFormToOrderDTOConverter.convert(orderForm);
        //此时需要将前端页面传进来的OrderForm 转换成 orderDTO
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderDTO createResult = orderService.create(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());

        return ResultVOUtil.success(map);
    }

    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {

        //先判断openid是否为空
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid 为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        //之后 调用service层的方法生成订单列表

        Page<OrderDTO> orderDTOPage = orderService.findList(openid, PageRequest.of(page, size));
        // getContent() 获取一个list 将其封装到 ResultVO中
        return ResultVOUtil.success(orderDTOPage.getContent());

    }

    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {

        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderDTO);

    }

    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId) {

        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();

    }


}
