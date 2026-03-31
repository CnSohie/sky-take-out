package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    List<DishFlavor> getByDishId(Long id);

    void deleteByDishIds(@Param("dishIds") List<Long> ids);

    void insertBatch(List<DishFlavor> flavors);

    void deleteByDishId(Long id);
}
