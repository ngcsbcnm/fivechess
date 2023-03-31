package com.ikun.acwing;


import com.ikun.acwing.service.impl.MatchingServiceImpl;
import com.ikun.acwing.service.impl.utils.MatchPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MatchingSystemApplication {
    public static void main(String[] args) {
        MatchingServiceImpl.matchPool.start();
        SpringApplication.run(MatchingSystemApplication.class,args);
    }
}
