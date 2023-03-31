package com.ikun.acwing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ikun.acwing.mapper.UserMapper;
import com.ikun.acwing.pojo.User;
import com.ikun.acwing.service.util.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User user = userMapper.selectOne(queryWrapper);


        if (user == null){
            throw new RuntimeException("此用户不存在");
        }else{

             return  new UserDetailsImpl(user);
        }


    }
}
