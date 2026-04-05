package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DishMapper {
    Page<Category> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    Dish getById(Long id);


    void deleteById(@Param("ids")List<Long> ids);

    void editStatus(@Param("status")Integer status, @Param("ids")List<Long> ids);

    List<DishVO> findAllVO();

    void insert(Dish dish);

    void update(Dish dish);

    List<Dish> getBySetmealId(Long id);

    List<Dish> list(Dish dish);
}
