package com.ikun.acwing.service.impl.record;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ikun.acwing.mapper.RecordMapper;
import com.ikun.acwing.mapper.UserMapper;
import com.ikun.acwing.pojo.Record;
import com.ikun.acwing.pojo.User;
import com.ikun.acwing.service.record.GetRecordListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GetRecordListServiceImpl implements GetRecordListService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RecordMapper recordMapper;



    public static String getStringToday(Date currentTime) {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(currentTime);
            return dateString;
  }

    @Override
    public JSONObject getRecordList(Integer page) {

        IPage<Record>  recordIPage = new Page<>(page,6);
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        List<Record> recordList = recordMapper.selectPage(recordIPage,queryWrapper).getRecords();

        JSONObject resp = new JSONObject();
        List<JSONObject> items = new ArrayList<>();
        for(Record record:recordList){
            User userA = userMapper.selectById(record.getAId());
            User userB = userMapper.selectById(record.getBId());
            JSONObject item = new JSONObject();
            item.put("id",record.getId());
            item.put("a_photo",userA.getPhoto());
            item.put("a_username",userA.getUsername());
            item.put("b_photo",userB.getPhoto());
            item.put("b_username",userB.getUsername());
            String result = "平局";
            if(record.getLoser().equals("A")){
                result = "玩家B获胜";
            }else{
                result = "玩家A获胜";
            }
            //System.out.println(record);
            item.put("steps",record.getSteps());
            item.put("winner",result);
            item.put("createtime",getStringToday(record.getCreatetime()));

           // System.out.println(item.getString("createtime"));
            items.add(item);
        }
        resp.put("records",items);
        resp.put("records_count",recordMapper.selectCount(null));
        return resp;
    }
}
