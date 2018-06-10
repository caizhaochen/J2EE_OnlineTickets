package com.ticket.czc.tickets.dao.impl;

import com.ticket.czc.tickets.dao.CouponDao;
import com.ticket.czc.tickets.model.CouponsEntity;
import com.ticket.czc.tickets.model.SeatsEntity;
import com.ticket.czc.tickets.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class CouponDaoImpl extends BaseDaoImpl implements CouponDao {
    private static CouponDaoImpl couponDao=new CouponDaoImpl();
    public static CouponDaoImpl getInstance(){
        return couponDao;
    }

    @Override
    public ArrayList<CouponsEntity> getCoupon(String userEmail) {
//        ArrayList<Object> coupons=(ArrayList<Object>) super.load(CouponsEntity.class,"userid",userEmail);
//        ArrayList<CouponsEntity> couponsEntities=new ArrayList<>();
//        if(coupons==null||coupons.size()==0){
//            return null;
//        }
//
//        for(int i=0;i<coupons.size();i++){
//            couponsEntities.add((CouponsEntity)coupons.get(i));
//        }
//        return couponsEntities;
        ArrayList<CouponsEntity> coupons=new ArrayList<>();

        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();
            String hql = "from com.ticket.czc.tickets.model.CouponsEntity c where c.userid= :p order by c.price desc ";
            Query query = session.createQuery(hql);
            query.setString("p",userEmail);
            coupons =(ArrayList<CouponsEntity>) query.list();

            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

        return coupons;
    }

    @Override
    public ArrayList<CouponsEntity> getAvailableCoupon(String userEmail) {
        ArrayList<CouponsEntity> coupons=new ArrayList<>();

        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();
            String hql = "from com.ticket.czc.tickets.model.CouponsEntity c where c.userid= :p and c.couponquantity>0 order by c.price desc ";
            Query query = session.createQuery(hql);
            query.setString("p",userEmail);
            coupons =(ArrayList<CouponsEntity>) query.list();

            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

        return coupons;
    }

    @Override
    public ArrayList<CouponsEntity> getAvailableCouponForOrderPrice(String userEmail, double price) {
        ArrayList<CouponsEntity> coupons=new ArrayList<>();

        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();
            String hql = "from com.ticket.czc.tickets.model.CouponsEntity c where c.userid= :p and c.couponquantity>0 and c.price< :m order by c.price";
            Query query = session.createQuery(hql);
            query.setString("p",userEmail);
            query.setDouble("m",price);
            coupons =(ArrayList<CouponsEntity>) query.list();

            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

        return coupons;
    }

    @Override
    public CouponsEntity getCoupon(String userEmail, double price) {
        ArrayList<CouponsEntity> coupons=new ArrayList<>();

        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();
            String hql = "from com.ticket.czc.tickets.model.CouponsEntity c where c.userid= :p and c.price= :n";
            Query query = session.createQuery(hql);
            query.setString("p",userEmail);
            query.setDouble("n",price);
            coupons =(ArrayList<CouponsEntity>) query.list();

            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (coupons==null||coupons.size()==0){
            return null;
        }

        return coupons.get(0);
    }

    @Override
    public void creatCoupon(CouponsEntity couponsEntity) {
        super.save(couponsEntity);
    }

    @Override
    public void updateCoupon(CouponsEntity couponsEntity) {
        super.update(couponsEntity);
    }
}
