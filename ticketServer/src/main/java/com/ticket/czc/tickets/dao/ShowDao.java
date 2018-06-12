package com.ticket.czc.tickets.dao;

import com.ticket.czc.tickets.model.ShowsEntity;

import java.util.ArrayList;

public interface ShowDao {

    public Integer save(ShowsEntity showsEntity);

    public ArrayList<ShowsEntity> getAllAvailableShows();

    public ShowsEntity getShowById(int showId);

    public void updateShow(ShowsEntity show);

    public ArrayList<ShowsEntity> getFutureShowByVenue(int venueId);

    public ArrayList<ShowsEntity> getShowsByVenue(int venueId);
    public ArrayList<ShowsEntity> getShowsByType(String type);
    public ArrayList<ShowsEntity> getAvailableShowsByType(String type);

    public ArrayList<String> getAllTypes();

    public ArrayList<ShowsEntity> getShowsByIds(ArrayList<Integer> showIds);



}
