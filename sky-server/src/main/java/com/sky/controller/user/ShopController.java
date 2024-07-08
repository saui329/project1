package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Api(tags = "店铺相关接口")
@Slf4j
public class ShopController {


    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/status")
    @ApiOperation("获取店铺的营业状态")
    public Result<String> getStatus(){
        String status = (String) redisTemplate.opsForValue().get("status");
        if(status == null){
            redisTemplate.opsForValue().set("status", 1);
            status = (String) redisTemplate.opsForValue().get("status");
        }

        log.info("获取的店铺的营业状态为：{}", status.equals("1")? "营业中":"打烊中" );
        return Result.success(status);
    }
}
