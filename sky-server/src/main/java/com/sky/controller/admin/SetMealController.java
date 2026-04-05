package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetMealController {
    @Autowired
    private SetMealService setMealService;
    @GetMapping("/page")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
            log.info("查询到数据: {}", setmealPageQueryDTO);
            PageResult page = setMealService.QueryPage(setmealPageQueryDTO);
            return Result.success(page);
    }
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    @PutMapping()
    public Result update(@RequestBody SetmealDTO setmealDTO) {
        log.info("修改数据{}", setmealDTO);
        setMealService.update(setmealDTO);
        return Result.success();

    }
    @GetMapping("/{id}")
    public Result<SetmealVO> findById(@PathVariable Long id) {

        SetmealVO setmealVO = setMealService.getByIdWithDish(id);
        return Result.success(setmealVO);
    }
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    @DeleteMapping()
    public Result delete(@RequestParam("ids") List<Long> ids) {
        log.info("删除数据{}",ids);
        setMealService.deleteWithDish(ids);
        return Result.success();
    }
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    @PostMapping()
    public Result save(@RequestBody SetmealDTO setmealDTO) {
        log.info("记录收到的数据{}", setmealDTO);
        setMealService.saveWithDish(setmealDTO);
        return Result.success();
    }
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    @PostMapping("/status/{status}")
    public Result updateStatus(@PathVariable("status") Integer status, @RequestParam("id") Long id) {
        log.info("这里是记录状态{}",status,id);
        setMealService.setStatus(id,status);
        return Result.success();
    }

}
