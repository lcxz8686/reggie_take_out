package com.harmony.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.harmony.reggie.dto.DishDto;
import com.harmony.reggie.entity.Dish;
import com.harmony.reggie.common.R;


public interface DishService extends IService<Dish> {

    public R<String> saveDishWithFlavor(DishDto dishDto);

    R<Page> pageByDish(int page, int pageSize, String name);

    R<String> updateDishWithFlavor(DishDto dishDto);

    R<DishDto> getDishInfoById(Long id);
}
