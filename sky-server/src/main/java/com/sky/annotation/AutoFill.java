package com.sky.annotation;

import com.sky.enumeration.OperationType;
import jdk.dynalink.Operation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解AutoFill，用于标识需要进行公共字段填充的方法
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AutoFill {
    // 数据库操作类型：UPDATE INSERT
    OperationType value();
}
