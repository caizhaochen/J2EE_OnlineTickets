package com.ticket.czc.tickets.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.ticket.czc.tickets.model.ShowsEntity;

import java.util.ArrayList;

public interface FavoriteService {

    public void addFavorite(String userEmail,int showId);

    public void cancelFavorite(String userEmail,int showId);

    public ArrayList<ShowsEntity> getFavoriteShowInfo(String userEmail);

    public boolean hasFavorite(int showId,String userEmail);
}
