package com.ticket.czc.tickets.dao.impl;

import com.ticket.czc.tickets.dao.ShowDao;
import com.ticket.czc.tickets.model.ShowsEntity;
import com.ticket.czc.tickets.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;


@Repository
public class ShowDaoImpl extends BaseDaoImpl implements ShowDao{

    private static ShowDaoImpl showDao=new ShowDaoImpl();

    public static ShowDaoImpl getInstance(){
        return showDao;
    }

    @Override
    public Integer save(ShowsEntity showsEntity) {
//        super.save(showsEntity);
        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();
            session.save((showsEntity));
            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return showsEntity.getShowid();
    }

    @Override

    public ArrayList<ShowsEntity> getAllAvailableShows() {
        ArrayList<ShowsEntity> list=new ArrayList<>();

        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();

            String hql = "select s,v.venuename,v.location from com.ticket.czc.tickets.model.ShowsEntity s,com.ticket.czc.tickets.model.VenuesEntity v where s.venueid=v.venueid and showtime> :p order by  s.posttime";
            Query query = session.createQuery(hql);
            query.setTimestamp("p", new Timestamp(System.currentTimeMillis()));
            list =(ArrayList<ShowsEntity>) query.list();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;

    }

    @Override
    public ShowsEntity getShowById(int showId) {
        ShowsEntity show=(ShowsEntity)super.load(ShowsEntity.class,showId);
        return show;
    }

    @Override
    public void updateShow(ShowsEntity show) {
        super.update(show);
    }

    @Override
    public ArrayList<ShowsEntity> getFutureShowByVenue(int venueId) {
        ArrayList<ShowsEntity> list=new ArrayList<>();
        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();
            String hql = "from com.ticket.czc.tickets.model.ShowsEntity s where s.venueid= :v and s.showtime> :p";
            Query query = session.createQuery(hql);
            query.setInteger("v",venueId);
            query.setTimestamp("p", new Timestamp(System.currentTimeMillis()));
            list =(ArrayList<ShowsEntity>) query.list();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<ShowsEntity> getShowsByVenue(int venueId) {
        ArrayList<ShowsEntity> list=new ArrayList<>();
        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();
            String hql = "from com.ticket.czc.tickets.model.ShowsEntity s where s.venueid= :v order by s.posttime desc ";
            Query query = session.createQuery(hql);
            query.setInteger("v",venueId);
            list =(ArrayList<ShowsEntity>) query.list();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<ShowsEntity> getShowsByType(String type) {
        ArrayList<ShowsEntity> list=new ArrayList<>();
        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();
            String hql = "from com.ticket.czc.tickets.model.ShowsEntity s where s.showtype= :v order by s.posttime desc ";
            Query query = session.createQuery(hql);
            query.setString("v",type);
            list =(ArrayList<ShowsEntity>) query.list();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<ShowsEntity> getAvailableShowsByType(String type) {
        ArrayList<ShowsEntity> list=new ArrayList<>();
        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();
            String hql = "from com.ticket.czc.tickets.model.ShowsEntity s where s.showtype= :v and s.showtime > :p order by s.posttime desc ";
            Query query = session.createQuery(hql);
            query.setString("v",type);
            query.setTimestamp("p", new Timestamp(System.currentTimeMillis()));
            list =(ArrayList<ShowsEntity>) query.list();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<String> getAllTypes() {
        ArrayList<String> list=new ArrayList<>();
        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();
            String hql = "select distinct s.showtype from com.ticket.czc.tickets.model.ShowsEntity s ";
            Query query = session.createQuery(hql);
            list =(ArrayList<String>) query.list();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<ShowsEntity> getShowsByIds(ArrayList<Integer> showIds) {
        ArrayList<ShowsEntity> result=new ArrayList<>();
        try {
            Session session = HibernateUtil.getSession();
            Transaction tx=session.beginTransaction();
            for (int i=0;i<showIds.size();i++){
                System.out.println(showIds.get(i));
                ShowsEntity show=session.get(ShowsEntity.class,showIds.get(i));
                result.add(show);
            }
            tx.commit();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public static void main(String[] args){
////        ShowDaoImpl showDao=new ShowDaoImpl();
////        ArrayList<ShowsEntity> showsEntities=showDao.getAllAvailableShows();
////        System.out.println(showsEntities.size());
//        ArrayList<Integer> showIds=new ArrayList<>();
//        showIds.add(100086);
//        showIds.add(100087);
//        ArrayList<ShowsEntity> shows=new ShowDaoImpl().getShowsByIds(showIds);
//        System.out.println(shows.size()+":"+shows.get(0).getShowname()+";"+shows.get(1).getShowname());
//    }
}
