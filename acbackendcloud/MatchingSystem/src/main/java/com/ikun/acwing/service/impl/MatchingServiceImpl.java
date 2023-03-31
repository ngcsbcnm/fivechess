package com.ikun.acwing.service.impl;

import com.ikun.acwing.service.MatchingService;
import com.ikun.acwing.service.impl.utils.MatchPool;
import org.springframework.stereotype.Service;


@Service
public class MatchingServiceImpl implements MatchingService {

    public final static MatchPool matchPool = new MatchPool();
    @Override
    public String addPlayer(Integer userId, Integer rating) {

        //System.out.println("add player"  + userId + " " + rating);
        matchPool.addPlayer(userId,rating);
        return "add" + userId.toString() + "successfully!";

    }

    @Override
    public String removePlayer(Integer userId) {

        //System.out.println("remove player "  + userId);
        matchPool.removePlayer(userId);
        return "remove" + userId.toString() + "successfully!";
    }
}
