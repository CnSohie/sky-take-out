package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    PageResult QueryPage(DishPageQueryDTO dishPageQueryDTO);

    DishVO getByIdWithFlavor(Long id);

    void deleteByid(List<Long> ids);

    void editStatus(Integer status, List<Long> ids);

    List<DishVO> findAll();

    DishVO addDish(DishDTO dishDTO);

    void update(DishDTO dishDTO);
}
