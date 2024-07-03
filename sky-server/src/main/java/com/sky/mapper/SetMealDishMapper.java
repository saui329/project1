package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetMealDishMapper {
    /**
     * 根据菜品id查询套餐id
     * @param setId
     * @return
     */
    List<Long> getMealDishIdsBySetId(List<Long> setId);
}
