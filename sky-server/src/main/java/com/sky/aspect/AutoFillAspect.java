package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.weaver.reflect.JoinPointMatchImpl;
import org.aspectj.weaver.tools.JoinPointMatch;
import org.springframework.stereotype.Component;

import java.awt.print.Printable;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面类，统一拦截加入了AutoFill注解的方法
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    /**
     * 切入点
     */
    // 将mapper package下的所有方法中注解了autofill的方法进行切入
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointcut() {

    }

    /**
     * 前置通知，在通知中进行公共字段的赋值
     *
     * @param joinPoint
     */
    // 可以直接在 before 方法中使用注解来定义切入点，而不需要单独定义一个 autoFillPointcut 方法。
    // @Before("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")

    @Before("autoFillPointcut()")
    public void before(JoinPoint joinPoint) {
        log.info("AutoFillPointcut before " + joinPoint.getSignature().getName());
        // 获取到当前被拦截的方法的数据库操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature(); // 方法签名对象
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class); // 获得方法上的注解对象
        OperationType operationType = autoFill.value(); // 获取数据库操作类型

        // 获取到当前被拦截方法的参数--实体对象 （比如 void update(Employee employee);中的employee）
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length == 0) {
            return;
        }

        Object entity = args[0]; // 默认第一个是需要的实体类

        // 准备赋值的数据
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        //根据当前不同的操作类型，为对应的属性通过反射进行赋值
        if(operationType == OperationType.INSERT) {
            // 为4个公共字段赋值
            try {
                // 获得对象方法
                Method setCreateUser = entity.getClass().getMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setCreateTime = entity.getClass().getMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                Method setUpdateTime = entity.getClass().getMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);

                // 通过反射进行赋值
                setCreateTime.invoke(entity, now);
                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, currentId);
                setCreateUser.invoke(entity, currentId);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        else if(operationType == OperationType.UPDATE) {
            // 为2个公共字段赋值
            // 为4个公共字段赋值
            try {
                // 获得对象方法
                Method setUpdateUser = entity.getClass().getMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                Method setUpdateTime = entity.getClass().getMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);

                // 通过反射进行赋值
                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, currentId);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
