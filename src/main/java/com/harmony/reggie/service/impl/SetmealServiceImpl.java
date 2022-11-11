package com.harmony.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.harmony.reggie.common.CustomException;
import com.harmony.reggie.common.R;
import com.harmony.reggie.dto.SetmealDto;
import com.harmony.reggie.entity.*;
import com.harmony.reggie.mapper.CategoryMapper;
import com.harmony.reggie.mapper.SetmealDishMapper;
import com.harmony.reggie.mapper.SetmealMapper;
import com.harmony.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    @Transactional
    public R<String> saveSetmeal(SetmealDto setmealDto) {

        this.save(setmealDto);

        //
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        // 向 setmeal_dish 表插入多条数据
        for (int i = 0; i < setmealDishes.size(); i++) {
            setmealDishMapper.insert(setmealDishes.get(i));
        }
        return R.success("条件成功！");
    }

    @Override
    public R<Page> pageBySetmeal(int page, int pageSize, String name) {
        Page<Setmeal> mealPage = new Page<>(page, pageSize);
        Page<SetmealDto> mealDtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Setmeal::getName, name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealMapper.selectPage(mealPage, queryWrapper);

        // pageInfo 里面的 records 是没有categoryName的！
        // 所以 records 直接 忽略，要在后面手动赋值
        BeanUtils.copyProperties(mealPage, mealDtoPage, "records");
        List<Setmeal> records = mealPage.getRecords();

        // 处理 records
        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            // item是一条records(list)记录，将Setmeal中的字段赋值给 setmealDto
            BeanUtils.copyProperties(item, setmealDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryMapper.selectById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());

        mealDtoPage.setRecords(list);

        return R.success(mealDtoPage);
    }

    @Override
    public R<String> sellStatus(Integer status, List<Long> ids) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ids != null, Setmeal::getId, ids);

        List<Setmeal> list = setmealMapper.selectList(queryWrapper);

        for (Setmeal setmeal : list) {
            if (setmeal != null) {
                setmeal.setStatus(status);
                setmealMapper.updateById(setmeal);
            }
        }

        return R.success("售卖状态修改成功");
    }

    @Override
    @Transactional
    public R<String> deleteSetmeal(List<Long> ids) {
        // 查菜品的状态，是否可以删除（）
        LambdaQueryWrapper<Setmeal> setmealQueryWrapper = new LambdaQueryWrapper<>();
        setmealQueryWrapper.in(Setmeal::getId, ids);
        setmealQueryWrapper.eq(Setmeal::getStatus, 1);

        int count = this.count(setmealQueryWrapper);
        if(count > 0) {
            throw new CustomException("菜品正在售卖中！不能删除！");
        }

        setmealMapper.deleteBatchIds(ids);

        LambdaQueryWrapper<SetmealDish> setmealDishQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishQueryWrapper.in(SetmealDish::getSetmealId, ids);

        setmealDishMapper.delete(setmealDishQueryWrapper);
        return R.success("删除菜品成功！");
    }
}