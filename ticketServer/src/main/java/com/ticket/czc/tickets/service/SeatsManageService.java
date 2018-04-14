package com.ticket.czc.tickets.service;

import com.ticket.czc.tickets.dao.impl.SeatsDaoImpl;
import com.ticket.czc.tickets.model.SeatsEntity;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;

public interface SeatsManageService {

    public boolean saveSeats(ArrayList<SeatsEntity> seatsEntities);

    public ArrayList<SeatsEntity> getSeatsByShow(int showID);
    public ArrayList<SeatsEntity> getSeatsByOrder(int orderID);

    public ArrayList<SeatsEntity> getRandomSeats(int showId,int num);

    public Boolean isAvailable(int showId,ArrayList<Integer> nums);

    public void lockSeats(int showId,ArrayList<Integer> nums);

    public SeatsEntity getSeat(int showId,int num);

    public void updateSeats(ArrayList<SeatsEntity> seats);

    public ArrayList<SeatsEntity> getSeats(int showId, ArrayList<Integer> nums);
}
