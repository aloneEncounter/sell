package com.miao.sell.service.impl;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/15 11:53
 *
 */

import com.miao.sell.converter.OrderMasterToOrderDTOConverter;
import com.miao.sell.dto.CartDTO;
import com.miao.sell.dto.OrderDTO;
import com.miao.sell.entity.OrderDetail;
import com.miao.sell.entity.OrderMaster;
import com.miao.sell.entity.ProductInfo;
import com.miao.sell.enums.OrderStatusEnum;
import com.miao.sell.enums.PayStatusEnum;
import com.miao.sell.enums.ResultEnum;
import com.miao.sell.exception.ReponseBankException;
import com.miao.sell.exception.SellException;
import com.miao.sell.respository.OrderDetailRepository;
import com.miao.sell.respository.OrderMasterRepository;
import com.miao.sell.service.*;
import com.miao.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private PayService payService;

    @Autowired
    private PushMessageService  pushMessageService;

    @Autowired     // 引入websocket  进行消息的推送
    private WebSocket webSocket;

    //创建订单
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtil.getUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);  //总和初始为0

        //List<CartDTO> cartDTOList=new ArrayList<>();

        //1、查询商品（数量，价格）
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());  //根据商品id查询商品信息
            if (productInfo == null) {    //如果商品信息为空，抛出商品不存在的异常
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
//                throw new ReponseBankException();
            }
            //2、计算订单总价
            orderAmount = productInfo.getProductPrice()
                                  .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                                  .add(orderAmount);

            //订单详情入库
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);      //订单号在商品创建前就创建
            BeanUtils.copyProperties(productInfo, orderDetail);     //进行对象之间属性的copy
            orderDetailRepository.save(orderDetail);

            //CartDTO cartDTO=new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
            //cartDTOList.add(cartDTO);
        }

        //3、将订单写入数据库（orderMaster和orderDetail） 注意先拷贝在设置
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);   //先设置在进行copy
        BeanUtils.copyProperties(orderDTO, orderMaster);       // 将传过来的属性copy到订单管理中
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        //保存到orderMaster数据库
        orderMasterRepository.save(orderMaster);

        //4、下单成功扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList()
                                            .stream().map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                                            .collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);

        //创建订单 发送websocket消息
//        webSocket.sendMessage("有新的订单");
        webSocket.sendMessage(orderDTO.getOrderId());

        return orderDTO;

    }

    //根据订单id 查询商品
    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = new OrderMaster();
//        orderMaster.setOrderId(orderId);
//
//        Example<OrderMaster> example = Example.of(orderMaster);
        Optional<OrderMaster> optional = orderMasterRepository.findById(orderId);  //查询后返回一个optional

            orderMaster = optional.get();    //获取对象



        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //根据订单的id 获取订单详情
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (orderDetailList == null) {
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);


        return orderDTO;
    }

    @Override
    //查询订单列表  返回的是 page<OrderDTO>  分页显示
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {

        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);

        List<OrderDTO> orderDTOList = OrderMasterToOrderDTOConverter
                                              .convert(orderMasterPage.getContent());

        //使用 pageImpl 构造一个page
        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

    @Override
    //取消订单
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();


        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.info("【取消了订单】 订单状态不正确,orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态   注意此时是修改的 orderDTO的状态  先修改后在进行copy
        //orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);     //注意
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【取消订单】 更新失败 orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //返回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【取消订单】订单中无商品详情，orderDTO{}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }

        //加库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                                            .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                                            .collect(Collectors.toList());

        productService.increaseStock(cartDTOList);

        //如果已经支付了，就需要退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            //TODO
            payService.refund(orderDTO);  // 微信退款

        }

        return orderDTO;
    }

    //完成订单
    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.info("【完结订单】 订单状态不正确,orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);

        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);    //将属性copy到master中 进行保存
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【完结订单】 更新失败 orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        // 推送微信模板消息
        pushMessageService.orderStatus(orderDTO);

        return orderDTO;
    }

    @Override
    @Transactional
    //支付订单
    public OrderDTO paid(OrderDTO orderDTO) {

        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.info("【订单支付 订单】 订单状态不正确,orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);

        }
        //判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【订单支付完成】订单支付状态不正确 orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【订单支付完成】 更新失败 orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }

//    查询订单 并分页
    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);

        List<OrderDTO> orderDTOList = OrderMasterToOrderDTOConverter
                                              .convert(orderMasterPage.getContent());

        //使用 pageImpl 构造一个page
        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());


    }


}
