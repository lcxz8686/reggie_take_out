package com.harmony.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.harmony.reggie.common.R;
import com.harmony.reggie.entity.Orders;

public interface OrdersService extends IService<Orders> {
    R<String> submitOrder(Orders orders);
}
