package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private  DishService dishService;
    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){
        log.info("这里是菜品管理"+dishPageQueryDTO);
        PageResult pageResult =  dishService.QueryPage(dishPageQueryDTO);
        return Result.success(pageResult);
    }
    @GetMapping("/{id}")
    public Result<DishVO> findById(@PathVariable Long id){
        log.info("查询id");
        DishVO dishVO = dishService.getByIdWithFlavor(id);
       return Result.success(dishVO);
    }
    @DeleteMapping
    public Result delete(@RequestParam("ids") List<Long> ids){
        log.info("删除信息"+ids);
        dishService.deleteByid(ids);
        return Result.success();
    }
    @PostMapping("/status/{status}")
    public Result updateStatus(@PathVariable("status") Integer status,@RequestParam("id") List<Long> ids){
        log.info("操作当前状态id:{ids},status:{status}"+ids+status);
        dishService.editStatus(status,ids);
        return Result.success();
    }
    @GetMapping("/list")
    public Result<List<DishVO>>  findAll(){
        List<DishVO> dish = dishService.findAll();
        log.info("记录所有分类"+dish);
        return Result.success(dish);
    }
    @PostMapping
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("这里增加菜品"+dishDTO);
        DishVO dish =dishService.addDish(dishDTO);
        return Result.success();
    }
    @PutMapping
    public Result update(@RequestBody DishDTO dishDTO){
        log.info("接受到修改的数据"+dishDTO);
        dishService.update(dishDTO);
        return Result.success();
    }
}
