package com.ticket.czc.tickets.service.impl;

import com.ticket.czc.tickets.dao.CouponDao;
import com.ticket.czc.tickets.factory.DaoFactory;
import com.ticket.czc.tickets.model.CouponsEntity;
import com.ticket.czc.tickets.service.CouponManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CouponManageServiceImpl implements CouponManageService {
    private static CouponManageServiceImpl couponManageService=new CouponManageServiceImpl();
    public static CouponManageServiceImpl getInstance(){
        return couponManageService;
    }

    private CouponDao couponDao= DaoFactory.getCouponDao();

//    @Autowired
//    private CouponDao couponDao;
    @Override
    public ArrayList<CouponsEntity> getCoupons(String userEmail) {
        ArrayList<CouponsEntity> coupons=couponDao.getCoupon(userEmail);
        return coupons;
    }

    @Override
    public ArrayList<CouponsEntity> getAvailableCoupons(String userEmail) {
        ArrayList<CouponsEntity> couponsEntities=couponDao.getAvailableCoupon(userEmail);
        return couponsEntities;
    }

    @Override
    public ArrayList<CouponsEntity> getAvailableCouponForOrderPrice(String userEmail, double price) {
        return couponDao.getAvailableCouponForOrderPrice(userEmail,price);
    }

    @Override
    public void createCoupon(String userEmail, double price) {
        CouponsEntity couponsEntity=new CouponsEntity();
        couponsEntity.setUserid(userEmail);
        couponsEntity.setPrice(price);
        couponsEntity.setCouponquantity(1);
        couponDao.creatCoupon(couponsEntity);
    }

    @Override
    public void addCoupon(String userEmail, double price) {
        CouponsEntity coupon=couponDao.getCoupon(userEmail,price);
        if (coupon==null){
            CouponManageServiceImpl.getInstance().createCoupon(userEmail, price);
        }else {
            coupon.setCouponquantity(coupon.getCouponquantity()+1);
            couponDao.updateCoupon(coupon);
        }
    }

    @Override
    public void reduceCoupon(String userEmail, double price) {
        CouponsEntity coupon=couponDao.getCoupon(userEmail,price);
        coupon.setCouponquantity(coupon.getCouponquantity()-1);
        couponDao.updateCoupon(coupon);

    }
}
