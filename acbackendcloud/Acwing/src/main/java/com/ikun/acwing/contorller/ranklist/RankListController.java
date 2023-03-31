package com.ikun.acwing.contorller.ranklist;


import com.alibaba.fastjson2.JSONObject;
import com.ikun.acwing.service.ranklist.RankListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RankListController {
    @Autowired
    private RankListService rankListService;

    @GetMapping("/api/ranklist/getlist")
    public JSONObject getRankList(@RequestParam Map<String,String> data){
            Integer page = Integer.parseInt(data.get("page"));
            return rankListService.getRankList(page);
    }
}
