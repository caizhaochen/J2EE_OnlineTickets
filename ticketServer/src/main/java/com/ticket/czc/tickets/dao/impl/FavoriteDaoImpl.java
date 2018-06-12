package com.ticket.czc.tickets.dao.impl;

import com.ticket.czc.tickets.dao.FavoriteDao;
import com.ticket.czc.tickets.model.FavoritesEntity;
import com.ticket.czc.tickets.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class FavoriteDaoImpl extends BaseDaoImpl implements FavoriteDao {
    private static FavoriteDaoImpl favoriteDao=new FavoriteDaoImpl();
    public static FavoriteDaoImpl getInstance(){
        return favoriteDao;
    }

    @Override
    public void addFavorite(FavoritesEntity favorite) {
        super.save(favorite);
    }

    @Override
    public void deleteFavorite(String userEmail, int showId) {
        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();

            String hql = "delete from com.ticket.czc.tickets.model.FavoritesEntity f where f.userEmail= :u and f.showId= :s";
            Query query = session.createQuery(hql);
            query.setString("u",userEmail);
            query.setInteger("s",showId);
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<FavoritesEntity> getFavoritesByEmail(String userEmail) {
        ArrayList<FavoritesEntity> favorites=new ArrayList<>();
        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();

            String hql = "from com.ticket.czc.tickets.model.FavoritesEntity f where f.userEmail= :u";
            Query query = session.createQuery(hql);
            query.setString("u",userEmail);
            favorites =(ArrayList<FavoritesEntity>) query.list();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return favorites;
    }
}
