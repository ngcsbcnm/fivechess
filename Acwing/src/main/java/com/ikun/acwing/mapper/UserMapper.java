package com.ikun.acwing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ikun.acwing.pojo.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper extends BaseMapper<User> {
}
