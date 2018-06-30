package com.ticket.czc.tickets.dao;

import com.ticket.czc.tickets.model.FavoritesEntity;

import java.util.ArrayList;

public interface FavoriteDao {

    public void addFavorite(FavoritesEntity favorite);

    public void deleteFavorite(String userEmail,int showId);

    public ArrayList<FavoritesEntity> getFavoritesByEmail(String userEmail);

    public ArrayList<FavoritesEntity> getFavoriteByEmailShow(int showId,String userEmail);
}
