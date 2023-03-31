package com.ikun.acwing.service.impl.ranklist;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ikun.acwing.mapper.UserMapper;
import com.ikun.acwing.pojo.User;
import com.ikun.acwing.service.ranklist.RankListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class RankListServiceImpl implements RankListService {

    @Autowired
    private UserMapper userMapper;


    public String Rank(Integer rating){
        if(rating<=1500){
            return "黑铁";
        }if(rating<=1600){
            return "白银";
        }if(rating<=1700){
            return "黄金";
        }if(rating<=1800){
            return "铂金";
        }if(rating<=1900){
            return "钻石";
        }if(rating<=2000){
            return "超凡大师";
        }
        return "最强王者";
    }

    @Override
    public JSONObject getRankList(Integer page) {

        IPage<User> pages = new Page<>(page,6);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("rating");
        List<User> users = userMapper.selectPage(pages,queryWrapper).getRecords();
        List<JSONObject> items = new ArrayList<>();
        JSONObject resp = new JSONObject();
        for(User user:users){
            JSONObject item = new JSONObject();
            item.put("photo",user.getPhoto());
            item.put("rating",user.getRating());
            item.put("username",user.getUsername());
            item.put("nick",user.getNick());

            item.put("rank",Rank(user.getRating()));
            items.add(item);
        }

        resp.put("users",items);
        resp.put("users_count",userMapper.selectCount(null));
        return resp;
    }
}
