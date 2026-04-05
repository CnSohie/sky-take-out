package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.BaseException;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetMealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Slf4j
@Service
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    private SetMealMapper setMealMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;
    @Autowired
    private DishMapper dishMapper;

    public SetMealServiceImpl(SetMealMapper setMealMapper) {
        this.setMealMapper = setMealMapper;
    }

    @Override
    public PageResult QueryPage(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setMealMapper.pageQuery(setmealPageQueryDTO);
        if (page.getResult() != null && !page.getResult().isEmpty()) {
            log.info("测试第一条数据的分类名称: {}", page.getResult().get(0).getCategoryName());
        } else {
            log.info("当前查询结果为空，没有数据可展示");
        }
        return new PageResult(page.getTotal(),page.getResult());

    }

    @Override
    public void update(SetmealDTO setmealDTO) { //需要复习参考一下这里实现
        // 1. 更新套餐表基本信息 (SQL: update setmeal set ...)
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmeal.setUpdateTime(LocalDateTime.now());
        setMealMapper.update(setmeal);

        // 2. 【关键：先删除】根据套餐ID删除旧的菜品关联
        // SQL: delete from setmeal_dish where setmeal_id = #{setmealId}
        // 修改这一行
        setMealDishMapper.deleteBySetmealIds(Collections.singletonList(setmealDTO.getId()));

        // 3. 【再插入】插入前端传来的新关联关系
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && !setmealDishes.isEmpty()) {
            setmealDishes.forEach(sd -> sd.setSetmealId(setmealDTO.getId()));
            setMealDishMapper.insertBatch(setmealDishes);
        }

    }

    @Override
    public SetmealVO getByIdWithDish(Long id) {
        // 1. 直接查询 VO，确保 XML 里的 SQL 关联了 category 表查出 categoryName
        SetmealVO setmealVO = setMealMapper.findById(id);

        if (setmealVO == null) {
            log.error("数据库里根本没有 ID 为 {} 的套餐！", id);
            throw new BaseException("数据不存在");
        }
        // 2. 查询关联菜品
        List<SetmealDish> setmealDishes = setMealDishMapper.getBySetmealId(id);

        // 3. 直接装配并返回
        setmealVO.setSetmealDishes(setmealDishes);

        return setmealVO;
    }

    @Override
    public void deleteWithDish(List<Long> ids) {
        // 1. 【防御性编程】先检查列表是否为空，避免后续浪费性能查数据库
        if (ids == null || ids.isEmpty()) {
            throw new DeletionNotAllowedException("您还没有勾选要删除的数据");
        }

        for (Long id : ids) {
            SetmealVO setmeal = setMealMapper.findById(id);

            // 2. 【核心修复】必须先判断 setmeal 是否为 null！！！
            // 报错 NullPointerException 就是因为数据库查不到这个 id，导致 setmeal 为空
            if (setmeal != null) {
                if (setmeal.getStatus() == StatusConstant.ENABLE) {
                    throw new DeletionNotAllowedException("在售的套餐禁止删除");
                }
            } else {
                // 如果查不到，说明该数据可能已被他人删除或 ID 传输有误，直接跳过即可
                continue;
            }
        }

        // 3. 执行删除逻辑
        setMealMapper.deleteByIds(ids);
        setMealDishMapper.deleteBySetmealIds(ids);
    }
@Transactional(rollbackFor = Exception.class)
    @Override
    public void saveWithDish(SetmealDTO setmealDTO) {
    Setmeal setmeal = new Setmeal();
    // 1. 对象拷贝：将 DTO 中的基本属性（名称、价格等）拷贝到实体类
    BeanUtils.copyProperties(setmealDTO, setmeal);
    setMealMapper.insert(setmeal);
    Long setmealId = setmeal.getId();
    List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
    if (setmealDishes != null && setmealDishes.size() > 0) {
        setmealDishes.forEach(setmealDish -> {
            // 为每一个关联菜品设置其所属的套餐 ID
            setmealDish.setSetmealId(setmealId);
        });
        // 5. 向套餐菜品关系表批量插入数据
        setMealDishMapper.insertBatch(setmealDishes);
    }

    }
@Transactional
    @Override
    public void setStatus(Long id, Integer status) {
        if (status == StatusConstant.ENABLE) {
            // select * from dish where id in (select dish_id from setmeal_dish where setmeal_id = #{id})
            List<Dish> dishList = dishMapper.getBySetmealId(id);
            if (dishList != null && dishList.size() > 0) {
            dishList.forEach(dish -> {
                if (StatusConstant.DISABLE.equals(dish.getStatus())) {
                    throw new SetmealEnableFailedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
                }
            });}
        }
    Setmeal setmeal = Setmeal.builder()
            .id(id)
            .status(status)
            .updateTime(LocalDateTime.now()) // 如果没做自动填充，手动补一下
            .build();
    setMealMapper.update(setmeal);
    }
    @Override
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setMealMapper.list(setmeal);
        return list;
    }

    @Override
    public List<DishItemVO> getDishItemById(Long id) {
        return setMealMapper.getDishItemBySetmealId(id);
    }
}
