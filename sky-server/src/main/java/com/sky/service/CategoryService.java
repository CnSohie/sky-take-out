package com.sky.service;

import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {
    //PageResult getpage();

    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void update(Category category);

    void add(Category category);

    void deleteByIds(List<Long> id);


    void status(Integer status, Long id);

    List<Category> findAll(Integer type);
}
