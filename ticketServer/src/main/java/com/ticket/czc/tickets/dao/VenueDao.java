package com.ticket.czc.tickets.dao;

import com.ticket.czc.tickets.model.VenuesEntity;

import java.util.ArrayList;

public interface VenueDao {
    public VenuesEntity saveVenue(VenuesEntity venuesEntity);

    public VenuesEntity updateVenue(VenuesEntity venuesEntity);

    public VenuesEntity findVenue(int id);
    public VenuesEntity findVenue(String name);

    public ArrayList<VenuesEntity> getUncheckedVenue();
    public ArrayList<VenuesEntity> getAllVenues();
    public ArrayList<VenuesEntity> getCheckedVenue();

}
