package com.harmony.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.harmony.reggie.entity.OrderDetail;
import com.harmony.reggie.mapper.OrderDetailMapper;
import com.harmony.reggie.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}