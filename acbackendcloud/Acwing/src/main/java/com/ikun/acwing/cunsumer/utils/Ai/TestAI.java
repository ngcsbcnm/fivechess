package com.ikun.acwing.cunsumer.utils.Ai;


import java.util.*;

public class TestAI {

    private Set<Integer> AI = new HashSet<>();
    private Set<Integer> human = new HashSet<>();
    private Deque<Integer> all = new ArrayDeque<>();//当前所有已经下过的点
    private int nextStepX;
    private int nextStepY;
    private final static int DEPTH = 3;
    private final static int radio = 1;
    private final static int SIZE = 15;
    private int[][] st;



    public List<Integer> getBlankPoint(){
        List<Integer> blank = new ArrayList<>();

        for(int i =  0;i<SIZE;i++){
            for(int j =0;j<SIZE;j++){
                if(this.st[i][j]==0){
                    blank.add(i*SIZE+j);
                }
            }
        }

        return blank;
    }
    private int distance(int x1,int y1,int x2,int y2){
            return Math.abs(x1-x2) + Math.abs(y1-y2);
    }
    private void order(List<Integer> list){
        Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                int x1 = o1/SIZE,y1 = o1%SIZE;
                int x2 = o2/SIZE,y2 = o2%SIZE;
                int x3 = all.getLast()/SIZE,y3 = all.getLast()%SIZE;
                return distance(x1,x2,x3,y3) - distance(x2,y2,x3,y3);
            }
        });
    }


    public boolean  isWin(int x,int y,Set<Integer> set){
            boolean ok = true;
            for(int i = 1;i<5;i++){
                int step = x*SIZE + y + i;
                if(!set.contains(step)){
                    ok = false;
                    break;
                }
            }
            if(ok)return ok;
            ok = true;
            for(int i = 1;i<5;i++){
                int step = (x+i)*SIZE + y;
                if(!set.contains(step)){
                    ok = false;
                    break;
                }
            }
            if(ok)return ok;
            ok = true;
            for(int i = 1;i<5;i++){
                int step = (x+i)*SIZE + y+i;
                if(!set.contains(step)){
                    ok = false;
                    break;
                }
            }
            if(ok)return ok;
            ok = true;
            for(int i = 1;i<5;i++){
                int step = (x+i)*SIZE + y-i;
                if(!set.contains(step)){
                    ok = false;
                    break;
                }
            }
           return ok;

    }
    public boolean isWin(Set<Integer> set){
        for(int step:set){
            if(isWin(step/SIZE,step%SIZE,set)){
                return true;
            }
        }
        return false;
    }


    private int negamax(boolean is_ai,int depth,int alpha,int beta){
                // if gameover or d == 0:
                // return
                if(depth==0 || isWin(AI) || isWin(human)){
                    return 0;
                }
                List<Integer> blank = getBlankPoint();
                order(blank);
                for(int next:blank){

                    if(!hasNearPoints(next/SIZE,next%SIZE))continue;

                    if(is_ai){
                        AI.add(next);
                        st[next/SIZE][next%SIZE] = 1;
                    }else{
                        human.add(next);
                        st[next/SIZE][next%SIZE] = 2;
                    }
                    all.add(next);


                    int value = -negamax(false,depth-1,-alpha,-beta);

                    if(is_ai){
                        AI.remove(next);
                    }else{
                        human.remove(next);
                    }
                    all.removeLast();
                    st[next/SIZE][next%SIZE] = 0;
                    if (value>alpha){
                        if(depth == DEPTH){
                            nextStepX = next/SIZE;
                            nextStepY = next%SIZE;
                        }
                        if(value>=beta){
                            return beta;
                        }
                        alpha = value;
                    }

                }

                return alpha;
    }
    public boolean isValid(int x,int y){
        return x>=0 && x< SIZE && y>=0 && y< SIZE;
    }
    private int getId(int x,int y){
        return x*SIZE + y;
    }
    public boolean hasNearPoints(int x,int y) {

        int row, column;
        //在一个点周围半径为2的范围内取点
        for (int i = -2; i <= 2; i++) {
            row = x + i;
            column = y + i;
            if (isValid(row, column)) {
                return true;
            }

            row = x - i;
            column = y + i;
            if (isValid(row, column)) {
                return true;
            }

            row = x;
            column = y + i;
            if (isValid(row, column)) {
                return true;
            }

            row = x + i;
            column = y;
            if (isValid(row, column)) {
                return true;
            }
        }
        return false;
    }


}
