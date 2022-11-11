package com.harmony.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.harmony.reggie.common.R;
import com.harmony.reggie.dto.SetmealDto;
import com.harmony.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    R<String> saveSetmeal(SetmealDto setmealDto);

    R<Page> pageBySetmeal(int page, int pageSize, String name);

    R<String> sellStatus(Integer status, List<Long> ids);

    R<String> deleteSetmeal(List<Long> ids);
}
