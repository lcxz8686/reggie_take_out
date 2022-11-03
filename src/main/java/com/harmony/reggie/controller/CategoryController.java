package com.harmony.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harmony.reggie.common.R;
import com.harmony.reggie.entity.Category;
import com.harmony.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 分类管理
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品
     * @param category
     * @return
     */
    @PostMapping
    public R<String> saveSortInfo(@RequestBody Category category) {
        return categoryService.saveSortInfo(category);
    }

    /**
     * 删除菜品,根据id删除分类，删除之前做一下判断，是否有关联
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> deleteSortInfo(Long id) {
        return categoryService.deleteSortInfo(id);
    }

    /**
     * 修改菜品
     * @param category
     * @return
     */
    @PutMapping
    public R<String> updateSortInfo(@RequestBody Category category) {
        return categoryService.updateSortInfo(category);
    }

    /**
     * 分页
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> pageSortInfo(int page, int pageSize) {
        return categoryService.pageSortInfo(page, pageSize);
    }

}
