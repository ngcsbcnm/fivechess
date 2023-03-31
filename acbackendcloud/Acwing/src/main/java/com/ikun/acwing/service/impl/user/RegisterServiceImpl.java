package com.ikun.acwing.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ikun.acwing.mapper.UserMapper;
import com.ikun.acwing.pojo.User;
import com.ikun.acwing.service.user.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, String> register(String username, String password, String confirmPassword) {
                Map<String,String> map = new HashMap<>();
                if (username == null || username.isEmpty()){
                    map.put("msg","用户名不能为空");
                    return map;
                }
                username = username.trim();
                if(username.isEmpty()){
                    map.put("msg","用户名不能为空");
                    return map;
                }
                if(username.length()>100){
                    map.put("msg","用户名长度不能大于100");
                    return map;
                }
                if(password == null || confirmPassword == null){
                    map.put("msg","密码不能为空");
                    return map;
                }
                if(password.isEmpty() || confirmPassword.isEmpty()){
                    map.put("msg","密码不能为空");
                    return map;
                }
                if(!password.equals(confirmPassword)){
                    map.put("msg","两次密码不一致");
                    return map;
                }
                if(password.length()>100){
                    map.put("msg","密码长度不能大于100");
                    return map;
                }

                QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("username",username);
                User user = userMapper.selectOne(queryWrapper);
                if(user!=null){
                    map.put("msg","该用户名已存在");
                    return map;
                }
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encodePassword = passwordEncoder.encode(password);
                user = new User(
                        null,username,encodePassword,null,null,"萌新","这个人太懒了，什么也没留下",new Date(),new Date()
                );
                userMapper.insert(user);

                map.put("msg","success");
                return map;

    }
}
