package com.ticket.czc.tickets.common;

public class LevelDiscount {

    public static final double[] discount={1.0,0.95,0.9,0.85,0.8};

    public static final double[] levelMoney={200.0,2000.0,5000.0,10000.0};

    public static int getLevel(double money){
        if(money<levelMoney[0]){
            return 0;
        }

        if(money>=levelMoney[levelMoney.length-1]){
            return levelMoney.length;
        }

        int level=0;
        for(int i=0;i<levelMoney.length;i++){
            if(money>=levelMoney[i]&&money<levelMoney[i+1]){
                level=i+1;
            }
        }
        return level;
    }
}
