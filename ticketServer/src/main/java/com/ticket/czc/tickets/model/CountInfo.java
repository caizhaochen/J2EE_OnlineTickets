package com.ticket.czc.tickets.model;

import java.sql.Timestamp;

public class CountInfo {

    private int showId;
    private String showName;
    private Timestamp showTime;
    private Timestamp showPostTime;
    private String showType;
    private String showDescribe;
    private String venueName;
    private double originMoney;
    private double venueGet;
    private double adminGet;
//    private double money;

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public double getAdminGet() {
        return adminGet;
    }

    public void setAdminGet(double adminGet) {
        this.adminGet = adminGet;
    }

    public double getOriginMoney() {
        return originMoney;
    }

    public void setOriginMoney(double originMoney) {
        this.originMoney = originMoney;
    }

    public double getVenueGet() {
        return venueGet;
    }

    public void setVenueGet(double venueGet) {
        this.venueGet = venueGet;
    }

    public String getShowDescribe() {
        return showDescribe;
    }

    public void setShowDescribe(String showDescribe) {
        this.showDescribe = showDescribe;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public Timestamp getShowPostTime() {
        return showPostTime;
    }

    public void setShowPostTime(Timestamp showPostTime) {
        this.showPostTime = showPostTime;
    }

    public Timestamp getShowTime() {
        return showTime;
    }

    public void setShowTime(Timestamp showTime) {
        this.showTime = showTime;
    }
}
