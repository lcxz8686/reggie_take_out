package com.harmony.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harmony.reggie.dto.DishDto;
import com.harmony.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.harmony.reggie.common.R;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> saveDishWithFlavor(@RequestBody DishDto dishDto) {
        return dishService.saveDishWithFlavor(dishDto);
    }

    /**
     * 菜品管理-分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> pageByDish(int page,int pageSize,String name) {
        return dishService.pageByDish(page, pageSize,name);
    }

    /**
     * 修改菜品
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> updateDishWithFlavor(@RequestBody DishDto dishDto) {
        return dishService.updateDishWithFlavor(dishDto);
    }


    /**
     * 根据id查询菜品信息与对应的口味信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> getDishInfoById(@PathVariable Long id) {
        return dishService.getDishInfoById(id);
    }

    /**
     * 删除菜品
     * @param ids
     * @return
     *
     * @@RequestParam: 用于将请求参数区数据映射到功能处理方法的参数上
     */
    @DeleteMapping
    public R<String> deleteDish(@RequestParam List<Long> ids) {
        log.info("ids:{}",ids);
        return dishService.deleteDish(ids);
    }

}

