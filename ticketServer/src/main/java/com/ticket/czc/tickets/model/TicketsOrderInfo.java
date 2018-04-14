package com.ticket.czc.tickets.model;

public class TicketsOrderInfo {
    private  int    onlineOrderNum;
    private  double onlineOrderPrice;
    private  double onlineOrderBenifit;

    private  int    realOrderNum    ;
    private  double realOrderPrice  ;
    private  double realOrderBenifit;

    private  int    backOrderNum    ;
    private  double backOrderPrice  ;
    private  double backOrderBenifit;


    public double getOnlineOrderPrice() {
        return onlineOrderPrice;
    }

    public double getOnlineOrderBenifit() {
        return onlineOrderBenifit;
    }

    public int getOnlineOrderNum() {
        return onlineOrderNum;
    }

    public void setOnlineOrderBenifit(double onlineOrderBenifit) {
        this.onlineOrderBenifit = onlineOrderBenifit;
    }

    public void setOnlineOrderNum(int onlineOrderNum) {
        this.onlineOrderNum = onlineOrderNum;
    }

    public void setOnlineOrderPrice(double onlineOrderPrice) {
        this.onlineOrderPrice = onlineOrderPrice;
    }

    public double getRealOrderPrice() {
        return realOrderPrice;
    }

    public double getRealOrderBenifit() {
        return realOrderBenifit;
    }

    public int getRealOrderNum() {
        return realOrderNum;
    }

    public void setRealOrderBenifit(double realOrderBenifit) {
        this.realOrderBenifit = realOrderBenifit;
    }

    public void setRealOrderNum(int realOrderNum) {
        this.realOrderNum = realOrderNum;
    }

    public void setRealOrderPrice(double realOrderPrice) {
        this.realOrderPrice = realOrderPrice;
    }

    public double getBackOrderBenifit() {
        return backOrderBenifit;
    }

    public double getBackOrderPrice() {
        return backOrderPrice;
    }

    public int getBackOrderNum() {
        return backOrderNum;
    }

    public void setBackOrderBenifit(double backOrderBenifit) {
        this.backOrderBenifit = backOrderBenifit;
    }

    public void setBackOrderNum(int backOrderNum) {
        this.backOrderNum = backOrderNum;
    }

    public void setBackOrderPrice(double backOrderPrice) {
        this.backOrderPrice = backOrderPrice;
    }
}
