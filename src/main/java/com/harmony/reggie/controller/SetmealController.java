package com.harmony.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harmony.reggie.dto.SetmealDishDto;
import com.harmony.reggie.entity.Setmeal;
import com.harmony.reggie.service.SetmealDishService;
import com.harmony.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.harmony.reggie.common.R;

import java.util.List;

/**
 * 套餐管理
 */
@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * 新增套餐
     * @param setmealDishDto
     * @return
     */
    @PostMapping
    public R<String> saveSetmeal(@RequestBody SetmealDishDto setmealDishDto) {
        return setmealService.saveSetmeal(setmealDishDto);
    }

    @PutMapping
    public R<String> updateSetmeal(@RequestBody SetmealDishDto setmealDishDto){

        return setmealService.updateSetmeal(setmealDishDto);
    }


    /**
     * 查询套餐
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public R<SetmealDishDto> getSetmealDish(@PathVariable("id") Long id) {
        return setmealService.getSetmealDish(id);
    }

    /**
     * 套餐隔离-分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> pageBySetmeal(int page, int pageSize, String name) {
        log.info("page = {},pageSize = {},name = {}", page, pageSize, name);
        return setmealService.pageBySetmeal(page, pageSize, name);
    }

    /**
     * 起售套餐、停售套餐
     * 请求 URL: http://localhost:8080/setmeal/status/0?ids=1415580119015145474
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> sellStatus(@PathVariable("status") Integer status, @RequestParam List<Long> ids) {
        return setmealService.sellStatus(status, ids);
    }

    /**
     * 删除套餐
     * @param ids
     * @return
     *
     * @@RequestParam: 用于将请求参数区数据映射到功能处理方法的参数上
     */
    @DeleteMapping
    public R<String> deleteSetmeal(@RequestParam List<Long> ids) {
        log.info("ids:{}",ids);
        return setmealService.deleteSetmeal(ids);
    }

    /**
     * 根据条件查询套餐数据
     * @param setmeal
     * @return
     */
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null,Setmeal::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null,Setmeal::getStatus,setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = setmealService.list(queryWrapper);

        return R.success(list);
    }

}
