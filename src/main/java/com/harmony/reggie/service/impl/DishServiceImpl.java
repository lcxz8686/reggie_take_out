package com.harmony.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.harmony.reggie.dto.DishDto;
import com.harmony.reggie.entity.Category;
import com.harmony.reggie.entity.Dish;
import com.harmony.reggie.entity.DishFlavor;
import com.harmony.reggie.mapper.CategoryMapper;
import com.harmony.reggie.mapper.DishFlavorMapper;
import com.harmony.reggie.mapper.DishMapper;
import com.harmony.reggie.service.DishFlavorService;
import com.harmony.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.harmony.reggie.common.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Override
    @Transactional
    public R<String> saveDishWithFlavor(DishDto dishDto) {
        this.save(dishDto);

        Long dishId = dishDto.getId();

        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishId);
            dishFlavorMapper.insert(flavor);
        }

//        flavors = flavors.stream().map((item) -> {
//            item.setDishId(dishId);
//            return item;
//        }).collect(Collectors.toList());
//        dishFlavorService.saveBatch(flavors);
//        for (int i = 0; i < flavors.size(); i++) {
//            dishFlavorMapper.insert()
//        }

        return R.success("新增菜品成功！");
    }

    @Override
    public R<Page> pageByDish(int page, int pageSize, String name) {
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(name != null, Dish::getName, name);
        lambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);
        dishMapper.selectPage(pageInfo, lambdaQueryWrapper);
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        List<Dish> records = pageInfo.getRecords();

        /**
         *
         * .stream().map((item) -> {} 等价用法
         *
        List<DishDto> collect = new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(records.get(i),dishDto);
            Long categoryId = records.get(i).getCategoryId();
            Category category = categoryMapper.selectById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            collect.add(dishDto);
        }
        dishDtoPage.setRecords(collect);
         */

        List<DishDto> collect = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryMapper.selectById(categoryId);

            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(collect);

        return R.success(dishDtoPage);
    }

    @Override
    @Transactional
    public R<String> updateDishWithFlavor(DishDto dishDto) {
        this.updateById(dishDto);
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorMapper.delete(queryWrapper);

        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishDto.getId());
            dishFlavorMapper.insert(flavor);
        }
        return R.success("修改成功！");
    }

    @Override
    public R<DishDto> getDishInfoById(Long id) {
        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);

        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());

        List<DishFlavor> flavors = dishFlavorMapper.selectList(queryWrapper);
        dishDto.setFlavors(flavors);
        return R.success(dishDto);
    }
}
