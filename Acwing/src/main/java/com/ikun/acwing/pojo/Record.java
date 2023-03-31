package com.ikun.acwing.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer aId;

    private Integer bId;

    private String loser;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Point> steps;


    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date createtime;



}
