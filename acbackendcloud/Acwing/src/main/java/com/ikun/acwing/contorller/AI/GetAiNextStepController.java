package com.ikun.acwing.contorller.AI;


import com.alibaba.fastjson2.JSONObject;
import com.ikun.acwing.service.AI.GetAiNextStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

@RestController
public class GetAiNextStepController {

        @Autowired
        private GetAiNextStepService getAiNextStepService;

        @GetMapping("/api/ai/get/nextstep/")
        public Map<String,String> getAiNextStep(@RequestParam Map<String,String> data){

            String boardJson = data.get("board");
            Integer[][] board = parse(boardJson);
            for(Integer[] a:board){
                for(Integer b:a){
                    System.out.print(b+ " ");
                }
                System.out.println();
            }
            Stack<int[]> stack = parseArr(data.get("stack"));
            //System.out.println(data.get("stack"));
            System.out.println(stack.toString());
            return getAiNextStepService.getAiNextStep(board,stack);
        }
        public Stack<int[]> parseArr(String s){
                Stack<int[]> stack = new Stack<>();

                int i = 1;
                while(i<s.length()-1){
                    int j = i;
                    while(s.charAt(j)!=']')j++;
                    String[] t = s.substring(i+1,j).split(",");
                    stack.push(new int[]{Integer.parseInt(t[0]),Integer.parseInt(t[1]),Integer.parseInt(t[2])});
                    i = j + 2;
                }
                return stack;
        }
        public Integer[][] parse(String s){
                Integer[][] board = new Integer[15][15];

                for(int i = 0,k=0;i<15;i++){
                    for(int j = 0;j<15;k++){
                        if(s.charAt(k)=='0'){
                            board[i][j++] = 0;
                        }else if(s.charAt(k)=='2'){
                            board[i][j++] = 2;
                            k ++;
                        }else if (s.charAt(k) == '1'){
                            board[i][j++] = 1;
                        }
                    }
                }

                return board;
        }

}
