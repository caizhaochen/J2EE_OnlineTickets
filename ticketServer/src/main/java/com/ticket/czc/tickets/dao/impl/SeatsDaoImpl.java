package com.ticket.czc.tickets.dao.impl;

import com.ticket.czc.tickets.dao.SeatsDao;
import com.ticket.czc.tickets.model.SeatsEntity;
import com.ticket.czc.tickets.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class SeatsDaoImpl extends BaseDaoImpl implements SeatsDao {

    private static SeatsDaoImpl seatsDao=new SeatsDaoImpl();

    public static SeatsDaoImpl getInstance(){
        return seatsDao;
    }

    @Override
    public void save(ArrayList<SeatsEntity> seatsEntities) {
        try {
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();
            for(int i=0;i<seatsEntities.size();i++){
                SeatsEntity seat=seatsEntities.get(i);
                session.merge(seat);
                if(i%20==0){
                    session.flush();
                    session.clear();
                }
            }
            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<SeatsEntity> getSeatsByShowId(int showId) {
        ArrayList<SeatsEntity> seats=new ArrayList<>();

        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();
            String hql = "from com.ticket.czc.tickets.model.SeatsEntity s where s.showid= :p order by s.seatnum";
//            String hql = "from com.ticket.czc.tickets.model.SeatsEntity s,com.ticket.czc.tickets.model.ShowsEntity sh  where s.showid= :p and sh.showid= :p";
            Query query = session.createQuery(hql);
            query.setInteger("p", showId);
            seats =(ArrayList<SeatsEntity>) query.list();

            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

        return seats;
    }

    @Override
    public ArrayList<SeatsEntity> getSeatsByOrderId(int orderId) {
        ArrayList<SeatsEntity> seats=new ArrayList<>();
        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();
            String hql = "from com.ticket.czc.tickets.model.SeatsEntity s where s.orderid= :p";
            Query query = session.createQuery(hql);
            query.setInteger("p", orderId);
            seats =(ArrayList<SeatsEntity>) query.list();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

        return seats;

    }

    @Override
    public ArrayList<SeatsEntity> getSeatsByNum(int showId, int num) {
        ArrayList<SeatsEntity> seats=new ArrayList<>();

        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();
            String hql = "from com.ticket.czc.tickets.model.SeatsEntity s where s.showid= :p and s.seatisbooked=0 order by s.seatprice,s.seatnum";
            Query query = session.createQuery(hql);
            query.setInteger("p", showId);
            query.setMaxResults(num);
            seats =(ArrayList<SeatsEntity>) query.list();

            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

        return seats;
    }

    @Override
    public Boolean isAvailable(int showId, int num) {

        ArrayList<SeatsEntity> seats=new ArrayList<>();

        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();
            String hql = "from com.ticket.czc.tickets.model.SeatsEntity s where s.showid= :p and s.seatnum= :n";
            Query query = session.createQuery(hql);
            query.setInteger("p", showId);
            query.setInteger("n",num);
            seats =(ArrayList<SeatsEntity>) query.list();

            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

        if (seats.size()==0){
            return false;
        }

        SeatsEntity seat=seats.get(0);
        if(seat.getSeatisbooked()==1){
            return false;
        }

        return true;
    }

    @Override
    public void lockSeat(int show, int num) {
//        ArrayList<SeatsEntity> seats=new ArrayList<>();
//
//        try {
//            Session session = HibernateUtil.getSession() ;
//            Transaction tx=session.beginTransaction();
//            String hql = "from com.ticket.czc.tickets.model.SeatsEntity s where s.showid= :p and s.seatnum= :n";
//            Query query = session.createQuery(hql);
//            query.setInteger("p", show);
//            query.setInteger("n",num);
//            seats =(ArrayList<SeatsEntity>) query.list();
//
//            tx.commit();
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        SeatsEntity seat=SeatsDaoImpl.getInstance().getSeat(show,num);
        seat.setSeatisbooked(1);
        super.update(seat);
        System.out.println("锁住了座位："+num);
    }

    @Override
    public void updateSeat(SeatsEntity seat) {
        super.update(seat);
    }

    @Override
    public SeatsEntity getSeat(int showId, int num) {
        ArrayList<SeatsEntity> seats=new ArrayList<>();

        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();
            String hql = "from com.ticket.czc.tickets.model.SeatsEntity s where s.showid= :p and s.seatnum= :n";
            Query query = session.createQuery(hql);
            query.setInteger("p", showId);
            query.setInteger("n",num);
            seats =(ArrayList<SeatsEntity>) query.list();

            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

        SeatsEntity seat=seats.get(0);
        return seat;
    }

    @Override
    public void updateSeats(ArrayList<SeatsEntity> seats) {
        try {
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();
            for(int i=0;i<seats.size();i++){
                SeatsEntity seat=seats.get(i);
                session.update(seat);
            }
            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        ArrayList<SeatsEntity> seatsEntities=SeatsDaoImpl.getInstance().getSeatsByNum(100026,70);
        System.out.println(seatsEntities.size());
    }
}
