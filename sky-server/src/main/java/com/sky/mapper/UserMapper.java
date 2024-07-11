package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface UserMapper {

    /**
     * 根据openid查询用户
     * @param openId（同一用户在同一小程序中的openid是不变的
     * @return
     */
    @Select("select * from user where openid = #{openId}")
    User getByOpenId(String openId);

    /**
     * 插入新用户
     * @param user
     */
    void insert(User user);

    @Select("select * from user where id=#{id}")
    User getById(Long userId);

    /**
     * 根据动态条件统计用户数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);
}
