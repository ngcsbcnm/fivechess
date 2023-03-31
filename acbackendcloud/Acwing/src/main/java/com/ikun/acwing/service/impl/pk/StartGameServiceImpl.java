package com.ikun.acwing.service.impl.pk;

import com.ikun.acwing.cunsumer.WebSocketServer;
import com.ikun.acwing.service.pk.StartGameService;
import org.springframework.stereotype.Service;


@Service
public class StartGameServiceImpl implements StartGameService {
    @Override
    public String startGame(Integer a_id, Integer b_id) {


        WebSocketServer.startGame(a_id,b_id);

        return "start game successfully!";
    }
}
