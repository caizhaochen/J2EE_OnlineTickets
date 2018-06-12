package com.ticket.czc.tickets.service;

import java.util.List;

public interface FavoriteService {

    public void addFavorite(String userEmail,int showId);

    public void cancelFavorite(String userEmail,int showId);

    public List getFavoriteShowInfo(String userEmail);
}
