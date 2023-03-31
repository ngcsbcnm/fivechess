package com.ikun.acwing.service.ranklist;

import com.alibaba.fastjson2.JSONObject;

public interface RankListService {
    JSONObject getRankList(Integer page);
}
