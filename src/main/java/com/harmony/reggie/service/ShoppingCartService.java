package com.harmony.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.harmony.reggie.common.R;
import com.harmony.reggie.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService extends IService<ShoppingCart> {

    R<ShoppingCart> subDishOrSetmeal(ShoppingCart shoppingCart);

    R<ShoppingCart> addDishOrSetmeal(ShoppingCart shoppingCart);

    R<String> cleanShoppingCart();

    R<List<ShoppingCart>> selectShoppingCart();
}
