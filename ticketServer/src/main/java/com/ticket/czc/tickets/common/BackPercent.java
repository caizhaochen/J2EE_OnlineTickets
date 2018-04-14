package com.ticket.czc.tickets.common;

public class BackPercent {

    public static final long[] backTime={1000*60*60*24,1000*60*60*24*5,1000*60*60*24*10,1000*60*60*24*15};

    public static final double[] percent={0.7,0.8,0.85,0.9,0.95};

    public static double getReducePercent(long time){
        if(time<=backTime[0]){
            return percent[0];
        }

        if(time>backTime[backTime.length-1]){
            return percent[percent.length-1];
        }

        double res=1.0;
        for(int i=0;i<backTime.length;i++){
            if(time>backTime[i]&&time<=backTime[i+1]){
                res= percent[i+1];
            }
        }
        return res;
    }


}
