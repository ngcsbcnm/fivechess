package com.ikun.acwing.cunsumer.utils.Ai;

public class Point {
    private int x;
    private int y;
    private Integer color;
    private int type;
    public static final int DIAMETER=30;

    public Point(int x,int y){
        this.x=x;
        this.y=y;
    }
    public Point(int x,int y,Integer color){
        this.x=x;
        this.y=y;
        this.color=color;
    }
    public Point(int x,int y,int type){
        this.x=x;
        this.y=y;
        this.type=type;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Integer getColor() {
        return color;
    }
    public int getType() {
        return type;
    }
}
