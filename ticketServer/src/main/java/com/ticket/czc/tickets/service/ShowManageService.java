package com.ticket.czc.tickets.service;

import com.ticket.czc.tickets.model.NumStatistics;
import com.ticket.czc.tickets.model.ShowsEntity;

import java.util.ArrayList;
import java.util.Map;

    public interface ShowManageService {

    public Integer registerShow(ShowsEntity showsEntity);


    public ArrayList<ShowsEntity> getAvailableShows();

    public ShowsEntity getShowById(int id);

    public void updateShow(ShowsEntity show);

    public ArrayList<ShowsEntity> getFutureShowByVenue(int venueId);
    public ArrayList<ShowsEntity> getAllShowByVenue(int venueId);

    public ArrayList<NumStatistics> getShowNumbers();


}
