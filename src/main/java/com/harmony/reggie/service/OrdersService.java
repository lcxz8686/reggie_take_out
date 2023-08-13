package com.harmony.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.harmony.reggie.common.R;
import com.harmony.reggie.entity.Orders;

import java.util.Date;

public interface OrdersService extends IService<Orders> {
    R<String> submitOrder(Orders orders);

    Page<Orders> pageOrders(int page, int pageSize, String number, Date beginTime, Date endTime);
}
