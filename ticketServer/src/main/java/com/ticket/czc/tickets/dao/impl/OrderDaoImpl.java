package com.ticket.czc.tickets.dao.impl;

import com.ticket.czc.tickets.dao.OrderDao;
import com.ticket.czc.tickets.model.OrdersEntity;
import com.ticket.czc.tickets.model.SeatsEntity;
import com.ticket.czc.tickets.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.util.ArrayList;

@Repository
public class OrderDaoImpl extends BaseDaoImpl implements OrderDao {
    private static OrderDaoImpl orderDao=new OrderDaoImpl();
    public static OrderDaoImpl getInstance(){
        return orderDao;
    }

    @Override
    public Integer saveOrder(OrdersEntity ordersEntity) {
        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();
            session.save((ordersEntity));
            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ordersEntity.getOrderid();
    }

    @Override
    public OrdersEntity getOrder(int orderId) {
        OrdersEntity order=(OrdersEntity) super.load(OrdersEntity.class,orderId);
        return order;
    }

    @Override
    public void updateOrder(OrdersEntity ordersEntity) {
       super.update(ordersEntity);
    }

    @Override
    public ArrayList<OrdersEntity> getOverDueOrders() {
        ArrayList<OrdersEntity> list=new ArrayList<>();

        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();

            String hql = "from com.ticket.czc.tickets.model.OrdersEntity o where o.orderdeadline< :p and o.orderstatus=0";
            Query query = session.createQuery(hql);
            query.setTimestamp("p", new Timestamp(System.currentTimeMillis()));
            list =(ArrayList<OrdersEntity>) query.list();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<OrdersEntity> getOrdersByUser(String userEmail) {
        ArrayList<OrdersEntity> list=new ArrayList<>();

        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();

            String hql = "from com.ticket.czc.tickets.model.OrdersEntity o," +
                    "com.ticket.czc.tickets.model.ShowsEntity s," +
                    "com.ticket.czc.tickets.model.VenuesEntity v where o.showid=s.showid and s.venueid=v.venueid and o.useremail= :p and o.orderstatus<4 order by o.ordertime desc ";
            Query query = session.createQuery(hql);
            query.setString("p",userEmail);
            list =(ArrayList<OrdersEntity>) query.list();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<OrdersEntity> getUnCountOrderByShow(int showId) {
        ArrayList<OrdersEntity> list=new ArrayList<>();

        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();

//            String hql = "select distinct o.showid from com.ticket.czc.tickets.model.OrdersEntity o where o.iscount=0 ";
            String hql = "from com.ticket.czc.tickets.model.OrdersEntity o where o.iscount=0 and o.showid= :s and (o.orderstatus=2 or o.orderstatus=3)";
            Query query = session.createQuery(hql);
            query.setInteger("s",showId);
            list =(ArrayList<OrdersEntity>) query.list();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<Integer> getUncountShowId() {
        ArrayList<Integer> list=new ArrayList<>();

        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();

            String hql = "select distinct o.showid from com.ticket.czc.tickets.model.OrdersEntity o where o.iscount=0 and o.showtime< :t and (o.orderstatus=2 or o.orderstatus=3) ";
            Query query = session.createQuery(hql);
            query.setTimestamp("t",new Timestamp(System.currentTimeMillis()));
            list =(ArrayList<Integer>) query.list();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("目前有"+list.size()+"个showId没有结算");
        return list;
    }

    @Override
    public void updateOrders(ArrayList<OrdersEntity> orders) {
        try {
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();
            for(int i=0;i<orders.size();i++){
                OrdersEntity order=orders.get(i);
                session.update(order);
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
    public ArrayList<OrdersEntity> getPayOnlineOrdersByShow(int showId) {
        ArrayList<OrdersEntity> list=new ArrayList<>();

        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();
            String hql = "from com.ticket.czc.tickets.model.OrdersEntity o where o.orderstatus=2 and o.showid= :v";
            Query query = session.createQuery(hql);
            query.setInteger("v",showId);
            System.out.println(query.list().toString());
            list =(ArrayList<OrdersEntity>) query.list();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<OrdersEntity> getPayRealOrdersByShow(int showId) {
        ArrayList<OrdersEntity> list=new ArrayList<>();

        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();
            String hql = "from com.ticket.czc.tickets.model.OrdersEntity o where o.orderstatus=4 and o.showid= :v";
            Query query = session.createQuery(hql);
            query.setInteger("v",showId);
            System.out.println(query.list().toString());
            list =(ArrayList<OrdersEntity>) query.list();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<OrdersEntity> getBackOrderByShow(int showId) {
        ArrayList<OrdersEntity> list=new ArrayList<>();

        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();
            String hql = "from com.ticket.czc.tickets.model.OrdersEntity o where o.orderstatus=3 and o.showid= :v";
            Query query = session.createQuery(hql);
            query.setInteger("v",showId);
            System.out.println(query.list().toString());
            list =(ArrayList<OrdersEntity>) query.list();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<OrdersEntity> getAllOnlineOrders() {
        ArrayList<OrdersEntity> list=new ArrayList<>();
        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();
            String hql = "from com.ticket.czc.tickets.model.OrdersEntity o where o.orderstatus=2";
            Query query = session.createQuery(hql);
            System.out.println(query.list().toString());
            list =(ArrayList<OrdersEntity>) query.list();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<OrdersEntity> getAllRealOrders() {
        ArrayList<OrdersEntity> list=new ArrayList<>();
        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();
            String hql = "from com.ticket.czc.tickets.model.OrdersEntity o where o.orderstatus=4";
            Query query = session.createQuery(hql);
            System.out.println(query.list().toString());
            list =(ArrayList<OrdersEntity>) query.list();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<OrdersEntity> getAllBackOrders() {
        ArrayList<OrdersEntity> list=new ArrayList<>();
        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();
            String hql = "from com.ticket.czc.tickets.model.OrdersEntity o where o.orderstatus=3";
            Query query = session.createQuery(hql);
            System.out.println(query.list().toString());
            list =(ArrayList<OrdersEntity>) query.list();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args){
        OrderDaoImpl orderDao=new OrderDaoImpl();
//        ArrayList<Integer> ordersEntities=orderDao.getUncountShowId();
//        ArrayList<OrdersEntity> ordersEntities=orderDao.getPayOrdersByVenue(1000001);
//        System.out.println(ordersEntities.size());
    }
}


