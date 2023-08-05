package com.harmony.reggie.dto;

import com.harmony.reggie.entity.Setmeal;
import com.harmony.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDishDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
