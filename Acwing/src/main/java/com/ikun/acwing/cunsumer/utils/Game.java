package com.ikun.acwing.cunsumer.utils;


import com.alibaba.fastjson2.JSONObject;
import com.ikun.acwing.cunsumer.WebSocketServer;
import com.ikun.acwing.pojo.Point;
import com.ikun.acwing.pojo.Record;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Data
public class Game extends Thread{


    private Player playerA;
    private Player playerB;

    private final ReentrantLock lock = new ReentrantLock();

    public int[][] g = new int[15][15];
    private Integer aX = null,aY = null;
    private Integer bX = null,bY = null;

    private Integer isA;

    private Integer isB;
    public int isOver = 0;
    private String loser = "";
    public Game(Integer idA,Integer idB,Integer isA,Integer isB){
        playerA = new Player(idA,"black",1,new ArrayList<>());
        playerB = new Player(idB,"white",0,new ArrayList<>());
        this.isA = isA;
        this.isB = isB;
    }
    public Player getPlayerA(){
        return playerA;
    }
    public Player getPlayerB(){
        return playerB;
    }


    public Integer getIsA() {

        return isA;
    }

    public Integer getIsB() {
        return isB;
    }

    public int isWin(int x, int y){
        int value = g[x][y];
        int cnt = 0;
        //竖方向
        for(int i = x-4;i<=x+4;i++){
            if(i<0||i>14)continue;
            if(g[i][y]==value){
                cnt++;
            }else{
                cnt =0 ;
            }if(cnt==5)return value;
        }
        //横放向
        cnt = 0;
        for(int j = y-4;j<=y+4;j++){
            if(j<0||j>14)continue;
            if(g[x][j]==value){
                cnt++;
            }else{
                cnt =0 ;
            }if(cnt==5)return value;
        }
        //左斜
        cnt = 0;
        for(int i = x+4,j = y-4;i>=x-4;i--,j++){
            if(i<0||i>14||j<0||j>14)continue;
            if(g[i][j]==value)cnt++;
            else cnt = 0;
            if(cnt==5)return value;
        }
        cnt = 0;
        for(int i = x-4,j = y-4;i<=x+4;i++,j++){
            if(i<0||i>14||j<0||j>14)continue;
            if(g[i][j]==value)cnt++;
            else cnt = 0;
            if(cnt==5)return value;
        }


        return 0;
    }

    public void setBPos(Integer bx,Integer by){
        lock.lock();
        try{
            this.bX = bx;
            this.bY = by;
        }finally {
            lock.unlock();
        }

    }
    public void setAPos(Integer ax,Integer ay){
        lock.lock();
        try{
            this.aX = ax;
            this.aY = ay;
        }finally {
            lock.unlock();
        }

    }

    public void setIsA(Integer isA) {
        lock.lock();
        try{
            this.isA = isA;
        }finally {
            lock.unlock();
        }
    }

    public void setIsB(Integer isB){
        lock.lock();
        try {
            this.isB = isB;
        }finally {
            lock.unlock();
        }
    }

    private void sendAllMessage(String message){
        if (WebSocketServer.users.get(playerA.getId()) != null)
            WebSocketServer.users.get(playerA.getId()).sendMessage(message);
        if (WebSocketServer.users.get(playerB.getId()) != null)
            WebSocketServer.users.get(playerB.getId()).sendMessage(message);

    }

    private boolean nextStep(){
        for (int i = 0; i < 50; i ++ ) {
            try {
                Thread.sleep(100);
                lock.lock();
                try {
                    if (aX != null && isA == 0) {
                        aX = null;aY = null;
                        return true;
                    }else if(bX!=null && isB == 0){
                        bX = null;bY = null;
                        return true;
                    }
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return false;

    }

    private List<Point> getSteps(){
        List<Point> steps = new ArrayList<>();
        int i = 0,j = 0;
        int n = playerA.getSteps().size();
        int m = playerB.getSteps().size();
        while(i<n || j<m){
            if(i<n){
                steps.add(playerA.getSteps().get(i++));
            }if(j<m){
                steps.add(playerB.getSteps().get(j++));
            }
        }
        return steps;
    }
    public void savaToDatabase(){
        Record record = new Record(null,playerA.getId(),
                playerB.getId(),loser,getSteps(),new Date());
        if(record.getSteps().isEmpty())return;

        WebSocketServer.recordMapper.insert(record);
    }
    private void sendTimeOutMessage(){

        JSONObject resp = new JSONObject();
        resp.put("event","timeout");
        resp.put("loser",loser);
        resp.put("Ax",aX);
        resp.put("Ay",aY);
        resp.put("Bx",bX);
        resp.put("By",bY);
        resp.put("isA",isA);
        resp.put("isB",isB);
        sendAllMessage(resp.toJSONString());
    }
    @Override
    public void run(){

        for (int i = 0; i < 1000; i ++ ) {
            if (!nextStep()) {  // 是否获取了两条蛇的下一步操作
                lock.lock();
                try {
                    if (aX == null && isA==1) {
                        loser = "A";
                    } else if (bX == null && isB==1) {
                        loser = "B";
                    } else {
                        loser = "all";
                    }

                    savaToDatabase();
                    sendTimeOutMessage();

                } finally {
                    lock.unlock();
                }
                break;
            }
        }

    }
}
