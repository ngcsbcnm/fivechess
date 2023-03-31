package com.ikun.acwing.service.impl.user;

import com.ikun.acwing.pojo.User;
import com.ikun.acwing.service.user.InfoService;
import com.ikun.acwing.service.util.UserDetailsImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InfoServiceImpl implements InfoService {
    @Override
    public Map<String, String> getInfo() {

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();
        Map<String,String> map = new HashMap<>();
        map.put("msg","success");
        map.put("id",user.getId().toString());
        map.put("username",user.getUsername());
        map.put("photo", user.getPhoto());
        map.put("description",user.getDescription());
        map.put("nick", user.getNick());
        map.put("rating",user.getRating().toString());
        return map;
    }
}
