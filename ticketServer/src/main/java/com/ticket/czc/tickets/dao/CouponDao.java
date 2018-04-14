package com.ticket.czc.tickets.dao;

import com.ticket.czc.tickets.model.CouponsEntity;

import java.util.ArrayList;

public interface CouponDao {

    public void creatCoupon(CouponsEntity couponsEntity);

    public ArrayList<CouponsEntity> getCoupon(String userEmail);
    public ArrayList<CouponsEntity> getAvailableCoupon(String userEmail);
    public ArrayList<CouponsEntity> getAvailableCouponForOrderPrice(String userEmail,double price);
    public CouponsEntity getCoupon(String userEmail,double price);

    public void updateCoupon(CouponsEntity couponsEntity);
}
