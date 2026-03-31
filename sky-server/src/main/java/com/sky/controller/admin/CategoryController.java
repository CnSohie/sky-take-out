package com.sky.controller.admin;

import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Resource
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/page")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("分类分页查询，参数为：{}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }
    @PutMapping()
    public Result update(@RequestBody Category category){
        log.info("这里是修改分类{}", category);
        categoryService.update(category);
        return Result.success();
    }
    @PostMapping()
    public Result add(@RequestBody Category category){
        log.info("这里是新增{}", category);
        categoryService.add(category);
        return Result.success();
    }
    @DeleteMapping()
    public Result delete(@RequestParam List<Long> id){
        log.info("删除菜品"+id);
        categoryService.deleteByIds(id);
        return Result.success();
    }
    @PostMapping("/status/{status}")
    public Result status( @PathVariable Integer status,@RequestParam Long id){
        log.info("当前状态"+status);
        categoryService.status(status,id);
        return Result.success();
    }
    @GetMapping("/list")
    public Result<List<Category>> list(Integer type){
        log.info("类型为"+type);
        List<Category>  categories= categoryService.findAll(type);
        return Result.success(categories);

    }
}
