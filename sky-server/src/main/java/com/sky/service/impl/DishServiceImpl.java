package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Override
    public PageResult QueryPage(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<Category> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public DishVO getByIdWithFlavor(Long id) {
        Dish dish = dishMapper.getById(id);
        List<DishFlavor> flavors = dishFlavorMapper.getByDishId(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(flavors);
        return dishVO;
    }
@Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByid(List<Long> ids) {
    for (Long id : ids) {
        // 第一步：先查询！用 getById 查出 Dish 对象
        Dish dish = dishMapper.getById(id);
        if (dish != null && dish.getStatus() == 1) {
            throw new DeletionNotAllowedException("在售商品不能删除");
        }
    }
    dishMapper.deleteById(ids);
    dishFlavorMapper.deleteByDishIds(ids);
    }

    @Override
    public void editStatus(Integer status, List<Long> ids) {
        dishMapper.editStatus(status,ids);
    }

    @Override
    public List<DishVO> findAll() {
        return dishMapper.findAllVO();
    }
@Transactional(rollbackFor = Exception.class)
    @Override
    public DishVO addDish(DishDTO dishDTO) {
    Dish dish = new Dish();
    BeanUtils.copyProperties(dishDTO, dish); // 对象拷贝
    dishMapper.insert(dish);
    Long dishId = dish.getId();
    List<DishFlavor> flavors = dishDTO.getFlavors();
    if (flavors != null && flavors.size() > 0) {
        flavors.forEach(dishFlavor -> {
            dishFlavor.setDishId(dishId);
        });
        dishFlavorMapper.insertBatch(flavors); // 批量插入持久化
    }
    return null;
}
@Transactional(rollbackFor = Exception.class)
    @Override
    public void update(DishDTO dishDTO) {
    Dish dish = new Dish();
    BeanUtils.copyProperties(dishDTO, dish);
    dish.setUpdateTime(LocalDateTime.now());
    dishMapper.update(dish);
    dishFlavorMapper.deleteByDishId(dishDTO.getId());
    List<DishFlavor> flavors = dishDTO.getFlavors();
    if (flavors != null && flavors.size() > 0) {
        flavors.forEach(dishFlavor -> {
            dishFlavor.setDishId(dishDTO.getId());
        });
        dishFlavorMapper.insertBatch(flavors);
    }
    }
}
