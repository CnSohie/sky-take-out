//package com.sky.controller.user;
//
//import com.sky.context.BaseContext;
//import com.sky.entity.ShoppingCart;
//import com.sky.result.Result;
//import com.sky.service.ShoppingCartService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Slf4j
//@RestController
//@RequestMapping("/user/shoppingCart")
//public class ShoppingCartController {
//
//    @Autowired
//    private ShoppingCartService shoppingCartService;
//
//    // 查询购物车列表 ← 前端启动就会调这个
//    @GetMapping("/list")
//    public Result<List<ShoppingCart>> list() {
//        List<ShoppingCart> list = shoppingCartService.showShoppingCart();
//        return Result.success(list);
//    }
//
//    // 添加购物车
//    @PostMapping("/add")
//    public Result add(@RequestBody ShoppingCart shoppingCart) {
//        shoppingCartService.addShoppingCart(shoppingCart);
//        return Result.success();
//    }
//
//    // 删除购物车中一个商品
//    @PostMapping("/sub")
//    public Result sub(@RequestBody ShoppingCart shoppingCart) {
//        shoppingCartService.subShoppingCart(shoppingCart);
//        return Result.success();
//    }
//
//    // 清空购物车
//    @DeleteMapping("/clean")
//    public Result clean() {
//        shoppingCartService.cleanShoppingCart();
//        return Result.success();
//    }
//}