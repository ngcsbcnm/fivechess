package com.ikun.acwing.service.impl.AI;



import com.ikun.acwing.cunsumer.utils.Ai.Ai;
import com.ikun.acwing.service.AI.GetAiNextStepService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Stack;

@Service
public class GetAiNextStepServiceImpl implements GetAiNextStepService {

    @Override
    public Map<String, String> getAiNextStep(Integer[][] board, Stack<int[]> stack) {
            Ai ai = new Ai(15,15,board,stack);

            return ai.AiNextSteps();


    }
}
