package com.sky;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement //开启注解方式的事务管理
@Slf4j
@EnableCaching // 开启缓存注解
@EnableScheduling // 开启定时任务
public class SkyApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SkyApplication.class, args);
//        RedisTemplate bean = (RedisTemplate) applicationContext.getBeanFactory().getBean("redisTemplate");
//        bean.opsForValue().set("1", "3");
//        System.out.println("bean"+ bean );

        log.info("server started");
    }
}
