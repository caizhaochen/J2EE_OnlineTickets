package com.ticket.czc.tickets.dao.impl;

import com.ticket.czc.tickets.dao.VenueDao;
import com.ticket.czc.tickets.model.SeatsEntity;
import com.ticket.czc.tickets.model.VenuesEntity;
import com.ticket.czc.tickets.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class VenueDaoImpl extends BaseDaoImpl implements VenueDao {
    private static VenueDaoImpl venueDao=new VenueDaoImpl();

    public VenueDaoImpl(){

    }

    public static VenueDaoImpl getInstance(){
        return venueDao;
    }

    @Override
    public VenuesEntity findVenue(int id) {
        VenuesEntity venue=(VenuesEntity)super.load(VenuesEntity.class,id);
        return venue;
    }

    @Override
    public VenuesEntity findVenue(String name) {
        List<Object> venuesEntities=super.load(VenuesEntity.class,"venuename",name);
        if(venuesEntities==null||venuesEntities.size()==0){
            return null;
        }
        VenuesEntity venue=(VenuesEntity)venuesEntities.get(0);
        return venue;
    }

    @Override
    public VenuesEntity saveVenue(VenuesEntity venuesEntity) {
        super.save(venuesEntity);
        VenuesEntity venue=(new VenueDaoImpl()).findVenue(venuesEntity.getVenuename());
        return venue;
    }

    @Override
    public VenuesEntity updateVenue(VenuesEntity venuesEntity) {
        super.update(venuesEntity);
        VenuesEntity venue=(new VenueDaoImpl()).findVenue(venuesEntity.getVenueid());
        return venue;
    }

    @Override
    public ArrayList<VenuesEntity> getUncheckedVenue() {
        ArrayList<VenuesEntity> venues=new ArrayList<>();
        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();
            String hql = "from com.ticket.czc.tickets.model.VenuesEntity v where v.ischecked= 0";
//            String hql = "from com.ticket.czc.tickets.model.SeatsEntity s,com.ticket.czc.tickets.model.ShowsEntity sh  where s.showid= :p and sh.showid= :p";
            Query query = session.createQuery(hql);
            venues =(ArrayList<VenuesEntity>) query.list();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

        return venues;
    }

    @Override
    public ArrayList<VenuesEntity> getAllVenues() {
        ArrayList<VenuesEntity> venues=new ArrayList<>();
        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();
            String hql = "from com.ticket.czc.tickets.model.VenuesEntity";
            Query query = session.createQuery(hql);
            venues =(ArrayList<VenuesEntity>) query.list();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

        return venues;
    }

    @Override
    public ArrayList<VenuesEntity> getCheckedVenue() {
        ArrayList<VenuesEntity> venues=new ArrayList<>();
        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();
            String hql = "from com.ticket.czc.tickets.model.VenuesEntity v where v.ischecked=1";
            Query query = session.createQuery(hql);
            venues =(ArrayList<VenuesEntity>) query.list();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

        return venues;
    }
}
