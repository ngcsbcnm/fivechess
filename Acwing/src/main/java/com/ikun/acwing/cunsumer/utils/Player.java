package com.ikun.acwing.cunsumer.utils;


import com.ikun.acwing.pojo.Point;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private Integer id;
    private String color;
    private Integer isMe;

    private List<Point> steps;


}
