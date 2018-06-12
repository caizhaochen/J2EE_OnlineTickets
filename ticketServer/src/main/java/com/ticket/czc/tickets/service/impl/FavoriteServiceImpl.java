package com.ticket.czc.tickets.service.impl;

import com.ticket.czc.tickets.dao.FavoriteDao;
import com.ticket.czc.tickets.dao.ShowDao;
import com.ticket.czc.tickets.dao.impl.FavoriteDaoImpl;
import com.ticket.czc.tickets.factory.DaoFactory;
import com.ticket.czc.tickets.model.FavoritesEntity;
import com.ticket.czc.tickets.model.ShowsEntity;
import com.ticket.czc.tickets.service.FavoriteService;

import java.util.ArrayList;

public class FavoriteServiceImpl implements FavoriteService {
    private static FavoriteServiceImpl favoriteService=new FavoriteServiceImpl();
    public static FavoriteServiceImpl getInstance(){
        return favoriteService;
    }

    private FavoriteDao favoriteDao= DaoFactory.getFavoriteDao();
    private ShowDao showDao=DaoFactory.getShowDao();

    @Override
    public void addFavorite(String userEmail, int showId) {
        FavoritesEntity favoritesEntity=new FavoritesEntity();
        favoritesEntity.setShowId(showId);
        favoritesEntity.setUserEmail(userEmail);
        favoriteDao.addFavorite(favoritesEntity);
    }

    @Override
    public void cancelFavorite(String userEmail, int showId) {
        favoriteDao.deleteFavorite(userEmail,showId);
    }

    @Override
    public ArrayList<ShowsEntity> getFavoriteShowInfo(String userEmail) {
        ArrayList<FavoritesEntity> favorites=favoriteDao.getFavoritesByEmail(userEmail);
        if (favorites==null||favorites.size()==0){
            return null;
        }
        ArrayList<Integer> showIds=new ArrayList<>();
        for (FavoritesEntity favorite:favorites){
            showIds.add(favorite.getShowId());
        }
        return showDao.getShowsByIds(showIds);
    }
}
