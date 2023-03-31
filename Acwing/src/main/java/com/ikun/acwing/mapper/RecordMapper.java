package com.ikun.acwing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ikun.acwing.pojo.Record;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordMapper extends BaseMapper<Record> {
}
