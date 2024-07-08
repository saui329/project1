package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "店铺相关接口")
@Slf4j
public class ShopController {


    @Autowired
    private RedisTemplate redisTemplate;

    @PutMapping("/{status}")
    @ApiOperation("设置店铺的营业状态")
    public Result<Integer> setStatus(@PathVariable("status") Integer status){
        log.info("设置店铺的营业状态为：{}", status == 1? "营业中":"打烊中" );
        redisTemplate.opsForValue().set("status", status+"");
        return Result.success(status);
    }

    @GetMapping("/status")
    @ApiOperation("获取店铺的营业状态")
    public Result<String> getStatus(){
        String status = (String) redisTemplate.opsForValue().get("status");
        if(status == null){
            redisTemplate.opsForValue().set("status", "1");
            status = (String) redisTemplate.opsForValue().get("status");
        }
        log.info("获取的店铺的营业状态为：{}", status.equals("1")? "营业中":"打烊中" );
        return Result.success(status);
    }
}
