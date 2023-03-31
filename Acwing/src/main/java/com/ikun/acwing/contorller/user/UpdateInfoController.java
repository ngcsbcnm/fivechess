package com.ikun.acwing.contorller.user;


import com.ikun.acwing.service.user.UpdateInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UpdateInfoController {

    @Autowired
    private UpdateInfoService updateInfoService;

    @PostMapping("user/update/info/")
    public Map<String,String> updateInfo(@RequestParam Map<String,String> data){
            String nick = data.get("nick");
            String description = data.get("description");

            
            return updateInfoService.updateInfo(nick,description);
    }

}
