package com.ticket.czc.tickets.service;

import com.ticket.czc.tickets.model.CouponsEntity;

import java.util.ArrayList;

public interface CouponManageService {

    public ArrayList<CouponsEntity> getCoupons(String userEmail);
    public ArrayList<CouponsEntity> getAvailableCoupons(String userEmail);
    public ArrayList<CouponsEntity> getAvailableCouponForOrderPrice(String userEmail,double price);

    public void createCoupon(String userEmail,double price);

    public void addCoupon(String userEmail,double price);

    public void reduceCoupon(String userEmail,double price);
}
