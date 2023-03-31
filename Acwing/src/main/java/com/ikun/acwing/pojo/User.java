package com.ikun.acwing.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String photo;

    private Integer rating;

    private String nick;

    private String description;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date createtime;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date updatetime;

}
