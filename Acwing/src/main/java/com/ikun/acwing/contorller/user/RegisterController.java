package com.ikun.acwing.contorller.user;


import com.ikun.acwing.service.user.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("user/register/")
    public Map<String,String> register(@RequestParam Map<String,String> data){
            String username = data.get("username");
            String password = data.get("password");
            String confirmPassword = data.get("confirmPassword");

            return registerService.register(username,password,confirmPassword);
    }
}
