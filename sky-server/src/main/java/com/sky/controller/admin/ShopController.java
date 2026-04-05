package com.sky.controller.admin;

import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;
    public Result setStatus(@PathVariable Integer status) {
        log.info("设置店铺的状态{}", status);
        redisTemplate.opsForValue().set("SHOP_STATUS", status);
        return Result.success(status);
    }
    @GetMapping("/status")
public Result<Integer> getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");
        log.info("获取到当前的营业状态{}", status);
        return Result.success(status);
}
@PutMapping("/{status}")
    public Result updateStatus(@PathVariable Integer status) {
        log.info("更新店铺的状态为{}", status);
        redisTemplate.opsForValue().set("SHOP_STATUS", status);
        return Result.success(status);
}
}
