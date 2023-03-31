package com.ikun.acwing.cunsumer;



import com.alibaba.fastjson2.JSONObject;
import com.ikun.acwing.cunsumer.utils.Game;
import com.ikun.acwing.cunsumer.utils.JwtAuthentication;
import com.ikun.acwing.mapper.RecordMapper;
import com.ikun.acwing.mapper.UserMapper;
import com.ikun.acwing.pojo.Point;
import com.ikun.acwing.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/websocket/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {

    public final static ConcurrentHashMap<Integer,WebSocketServer> users = new ConcurrentHashMap<>();

    //public final static CopyOnWriteArraySet<User> matchpool = new CopyOnWriteArraySet<>();
    private User user;


    private static UserMapper userMapper;

    public static RecordMapper recordMapper;

    private static RestTemplate restTemplate;

    private Session session = null;

    public Game game = null;

    @Autowired
    private void setRestTemplate(RestTemplate restTemplate){

        WebSocketServer.restTemplate = restTemplate;
    }

    @Autowired
    private void setUserMapper(UserMapper userMapper){
        WebSocketServer.userMapper = userMapper;
    }

    @Autowired
    private void setRecordMapper(RecordMapper recordMapper){
        WebSocketServer.recordMapper = recordMapper;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        // 建立连接
        this.session = session;
        System.out.println("connect!");
        //System.out.println(token);
        Integer userId = JwtAuthentication.getUserId(token);
        this.user = userMapper.selectById(userId);
        if(user!=null){
            users.put(userId,this);
            //matchpool.add(user);
            //System.out.println(user);
        }else{
            session.close();
        }


        //System.out.println(user);
    }

    @OnClose
    public void onClose() {
        // 关闭链接
        System.out.println("disconnect!");
        if(this.user!=null){
            users.remove(user.getId());

        }

    }

    private void startGame(Integer aId,Integer bId){
        User a = userMapper.selectById(aId);
        User b = userMapper.selectById(bId);

        Game game = new Game(a.getId(),b.getId(),1,0);


        users.get(a.getId()).game = game;
        users.get(b.getId()).game = game;

        game.start();

        JSONObject respA = new JSONObject();
        respA.put("event","start-matching");
        respA.put("opponent_username",b.getUsername());
        respA.put("opponent_photo",b.getPhoto());
        respA.put("color","black");
        users.get(a.getId()).sendMessage(respA.toJSONString());

        JSONObject respB = new JSONObject();
        respB.put("event","start-matching");
        respB.put("opponent_username",a.getUsername());
        respB.put("opponent_photo",a.getPhoto());
        respB.put("color","white");
        users.get(b.getId()).sendMessage(respB.toJSONString());
    }
    public void startMatching(){

        System.out.println("start-matching");



    }
    public void move(int x,int y,String color){
            if(color.equals("black")&&game.getIsA().equals(0)){
                return;
            }if(color.equals("white")&&game.getIsB().equals(0)){
                return;
            }if(game.isOver!=0){
                game.savaToDatabase();
                return;
            }
            int value = color.equals("black") ? 1: 2;
            game.g[x-1][y-1] = value;
            game.isOver = game.isWin(x-1,y-1);

            if(color.equals("black")){
                game.setAPos(x,y);
                game.getPlayerA().getSteps().add(new Point(x,y,"black"));
            }else{
                game.setBPos(x,y);
                game.getPlayerB().getSteps().add(new Point(x,y,"white"));
            }

            JSONObject respA = new JSONObject();
            respA.put("event","move");
            respA.put("x",x);
            respA.put("y",y);
            respA.put("color",color);
            respA.put("is_over",game.isOver);
            game.setIsA(1-game.getIsA());



            JSONObject respB = new JSONObject();
            respB.put("event","move");
            respB.put("x",x);
            respB.put("y",y);
            respB.put("color",color);
            respB.put("is_over",game.isOver);
            game.setIsB(1-game.getIsB());



            users.get(game.getPlayerA().getId()).sendMessage(respA.toJSONString());
            users.get(game.getPlayerB().getId()).sendMessage(respB.toJSONString());


    }
    public void stopMatching(){

        System.out.println("stop-matching");
    }

    public void dealMessage(String photo,String message,Integer id,Integer userId){
        if(message==null || message.length()==0)return;
        JSONObject respA = new JSONObject();
        respA.put("event","send-message");
        respA.put("photo",photo);
        respA.put("message",message);
        respA.put("id",id);

        JSONObject respB = new JSONObject();
        respB.put("event","send-message");
        respB.put("photo",photo);
        respB.put("message",message);
        respB.put("id",id);


        if(userId.equals(game.getPlayerA().getId())){
            respA.put("who","me");
            respB.put("who","he");
        }else{
            respB.put("who","me");
            respA.put("who","he");
        }





        users.get(game.getPlayerA().getId()).sendMessage(respA.toJSONString());
        users.get(game.getPlayerB().getId()).sendMessage(respB.toJSONString());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // 从Client接收消息
        JSONObject data = JSONObject.parseObject(message);
        String event = data.getString("event");
        if("start-matching".equals(event)){
            startMatching();
        }else if("stop-matching".equals(event)){
            stopMatching();
        }else if("move".equals(event)){
            int x = data.getIntValue("x");
            int y = data.getIntValue("y");
            String color = data.getString("color");
            move(x,y,color);
        }else if("send-message".equals(event)){
            System.out.println(data);
            dealMessage(data.getString("photo"),data.getString("message")
                   ,data.getInteger("id"),data.getInteger("userid"));
        }


        System.out.println("onMessage!");
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    //发送消息到client
    public void sendMessage(String message){

            synchronized (this.session){
                try{
                    this.session.getBasicRemote().sendText(message);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
    }
}
