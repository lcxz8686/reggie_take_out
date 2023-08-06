package com.harmony.reggie.dto;

import com.harmony.reggie.entity.Dish;
import com.harmony.reggie.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO
 */
@Data
public class DishDto extends Dish {

    // 菜品对应的口味数据
    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
