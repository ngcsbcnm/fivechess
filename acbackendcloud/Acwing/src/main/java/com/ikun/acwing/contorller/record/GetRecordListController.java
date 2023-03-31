package com.ikun.acwing.contorller.record;


import com.alibaba.fastjson2.JSONObject;
import com.ikun.acwing.service.record.GetRecordListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetRecordListController {
    @Autowired
    private GetRecordListService getRecordListService;


    @GetMapping("/api/get/recordlist/")
    public JSONObject getRecordList(@RequestParam MultiValueMap<String,String> data){

        if(data.getFirst("page")==null)return null;
        Integer page = Integer.parseInt(data.getFirst("page"));

        return getRecordListService.getRecordList(page);
    }
}
