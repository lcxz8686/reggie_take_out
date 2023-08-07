package com.harmony.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.harmony.reggie.common.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harmony.reggie.entity.Orders;
import com.harmony.reggie.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 订单
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrdersService ordersService;


    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submitOrder(@RequestBody Orders orders) {
        return ordersService.submitOrder(orders);
    }

    /**
     * 订单分页查询
     *
     * @param page
     * @param pageSize
     * @param number
     * @return
     */
    @GetMapping("/page")
    public R<Page<Orders>> page(
        int page,
        int pageSize,
        String number,
        @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss") Date beginTime,
        @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss") Date endTime) {
        log.info(
                "订单分页查询：page={}，pageSize={}，number={},beginTime={},endTime={}",
                page,
                pageSize,
                number,
                beginTime,
                endTime);
        // 根据以上信息进行分页查询。
        // 创建分页对象
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        // 创建查询条件对象。
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(number), Orders::getNumber, number);
        if (beginTime != null) {
            queryWrapper.between(Orders::getOrderTime, beginTime, endTime);
        }
        ordersService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 用户订单分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/userPage")
    public R<Page> page(int page, int pageSize){

        //分页构造器对象
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        //构造条件查询对象
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();

        //添加排序条件，根据更新时间降序排列
        queryWrapper.orderByDesc(Orders::getOrderTime);
        ordersService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }
}
