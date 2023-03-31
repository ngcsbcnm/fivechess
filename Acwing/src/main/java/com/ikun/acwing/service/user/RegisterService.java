package com.ikun.acwing.service.user;


import java.util.Map;

public interface RegisterService {
    Map<String,String> register(String email,String password,String confirmPassword);
}
