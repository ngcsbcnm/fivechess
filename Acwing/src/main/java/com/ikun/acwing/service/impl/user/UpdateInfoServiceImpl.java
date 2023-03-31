package com.ikun.acwing.service.impl.user;

import com.ikun.acwing.mapper.UserMapper;
import com.ikun.acwing.pojo.User;
import com.ikun.acwing.service.user.UpdateInfoService;
import com.ikun.acwing.service.util.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class UpdateInfoServiceImpl implements UpdateInfoService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public Map<String, String> updateInfo(String nick,String description) {

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        user.setDescription(description);
        user.setNick(nick);
        userMapper.updateById(user);

        Map<String,String> map = new HashMap<>();
        map.put("msg","success");
        map.put("nick",nick);
        map.put("description",description);
        return map;
    }
}
