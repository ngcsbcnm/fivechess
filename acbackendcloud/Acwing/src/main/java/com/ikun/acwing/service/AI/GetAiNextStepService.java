package com.ikun.acwing.service.AI;

import java.util.Map;
import java.util.Stack;

public interface GetAiNextStepService {
        Map<String,String> getAiNextStep(Integer[][] board, Stack<int[]> stack);
}
