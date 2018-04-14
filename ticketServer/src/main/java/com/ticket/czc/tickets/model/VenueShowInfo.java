package com.ticket.czc.tickets.model;

public class VenueShowInfo {

//    private int venueId;
//    private int showId;
    private ShowsEntity show;
    private int onlinePayNum;
    private double onlinePayIncome;
    private int realPayNum;
    private double realPayIncome;
    private int backNum;
    private double backIncome;
    private int checkOrders;
    private int checkSeats;

    public ShowsEntity getShow() {
        return show;
    }

    public void setShow(ShowsEntity show) {
        this.show = show;
    }

    public int getOnlinePayNum() {
        return onlinePayNum;
    }

    public void setOnlinePayNum(int onlinePayNum) {
        this.onlinePayNum = onlinePayNum;
    }

    public double getOnlinePayIncome() {
        return onlinePayIncome;
    }

    public void setOnlinePayIncome(double onlinePayIncome) {
        this.onlinePayIncome = onlinePayIncome;
    }

    public int getRealPayNum() {
        return realPayNum;
    }

    public void setRealPayNum(int realPayNum) {
        this.realPayNum = realPayNum;
    }

    public double getRealPayIncome() {
        return realPayIncome;
    }

    public void setRealPayIncome(double realPayIncome) {
        this.realPayIncome = realPayIncome;
    }

    public double getBackIncome() {
        return backIncome;
    }

    public void setBackIncome(double backIncome) {
        this.backIncome = backIncome;
    }

    public int getBackNum() {
        return backNum;
    }

    public void setBackNum(int backNum) {
        this.backNum = backNum;
    }

    public int getCheckOrders() {
        return checkOrders;
    }

    public void setCheckOrders(int checkOrders) {
        this.checkOrders = checkOrders;
    }

    public int getCheckSeats() {
        return checkSeats;
    }

    public void setCheckSeats(int checkSeats) {
        this.checkSeats = checkSeats;
    }
}
