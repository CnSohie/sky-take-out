package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SetMealDishMapper {
    List<SetmealDish> getBySetmealId(Long id);

    void deleteBySetmealIds(@Param("setmealIds")List<Long> ids);

    void insertBatch(List<SetmealDish> setmealDishes);
}
