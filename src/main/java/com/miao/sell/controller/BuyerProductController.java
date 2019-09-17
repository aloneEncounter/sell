package com.miao.sell.controller;

import com.miao.sell.VO.ProductVO;
import com.miao.sell.VO.ResultVO;
import com.miao.sell.entity.ProductCategory;
import com.miao.sell.entity.ProductInfo;
import com.miao.sell.entity.ProductInfoVO;
import com.miao.sell.service.CategoryService;
import com.miao.sell.service.ProductService;
import com.miao.sell.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/13 21:42
 *
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO list() {

        //1、查询所有上架的商品
        List<ProductInfo> productInfoList = productService.findUpAll();

        //2、查询类目（一次性查询所有）
      /*  List<Integer> categoryTypeList=new ArrayList<>();
        //传统方法
        for (ProductInfo productInfo:productInfoList){
            categoryTypeList.add(productInfo.getCategoryType());
        }*/
        //简单方法 lambda 表达式
        List<Integer> categoryTypeList = productInfoList.stream()
                                                 .map(e -> e.getCategoryType())
                                                 .collect(Collectors.toList());

        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        //3、数据拼装
        List<ProductVO> productVOList=new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoVO> productInfoVOList=new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {    //商品信息的显示
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);      //  将商品信息放入 集合
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);   //放入 productVO中
            productVOList.add(productVO);

        }


        /*ResultVO resultVO = new ResultVO();
//        ProductVO productVO = new ProductVO();
//        ProductInfoVO productInfoVO = new ProductInfoVO();
//
//        //将前端显示的信息使用 集合的方式输出
//        productVO.setProductInfoVOList(Arrays.asList(productInfoVO));
//
//        resultVO.setData(Arrays.asList(productVO));

        resultVO.setData(productVOList);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;*/

        return ResultVOUtil.success(productVOList);
    }

}
