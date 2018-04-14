package com.ticket.czc.tickets.service;

import com.ticket.czc.tickets.model.NumStatistics;
import com.ticket.czc.tickets.model.VenuesEntity;

import java.util.ArrayList;

public interface VenueManageService {

    public String validateVenue(int id,String password);

    public String registerVenue(VenuesEntity venuesEntity);

    public VenuesEntity getVenueInfo(int id);

    public VenuesEntity updateVenue(VenuesEntity venuesEntity);

    public ArrayList<VenuesEntity> getUncheckedVenues();
    public ArrayList<VenuesEntity> getCheckedVenues();

    public void checkVenue(int venueId,int state,String checkInfo);

    public ArrayList<NumStatistics> getVenueNum();

    public ArrayList<VenuesEntity> getAllVenues();
}
