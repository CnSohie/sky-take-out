package com.sky.controller.user;

import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController("userShopController")
@RequestMapping("/user/shop")
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;
    @GetMapping("/status")
    public Result<Integer> getStatus() {
        // 从 Redis 中获取状态
        Integer status = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");
        log.info("用户端查询到营业状态为：{}", status == 1 ? "营业中" : "打烊中");
        return Result.success(status);
    }
}
