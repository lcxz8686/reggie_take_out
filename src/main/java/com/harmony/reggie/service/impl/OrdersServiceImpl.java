package com.harmony.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.harmony.reggie.entity.Orders;
import com.harmony.reggie.mapper.OrdersMapper;
import com.harmony.reggie.service.OrdersService;
import org.springframework.stereotype.Service;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

}
