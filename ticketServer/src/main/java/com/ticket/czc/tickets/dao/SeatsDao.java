package com.ticket.czc.tickets.dao;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.ticket.czc.tickets.model.SeatsEntity;

import java.util.ArrayList;

public interface SeatsDao {

    public void save(ArrayList<SeatsEntity> seatsEntities);

    public ArrayList<SeatsEntity> getSeatsByShowId(int showId);
    public ArrayList<SeatsEntity> getSeatsByOrderId(int orderId);

    public ArrayList<SeatsEntity> getSeatsByNum(int showId,int num);

    public Boolean isAvailable(int showId,int num);

    public void lockSeat(int show,int num);

    public void updateSeat(SeatsEntity seat);

    public void updateSeats(ArrayList<SeatsEntity> seats);

    public SeatsEntity getSeat(int showId,int num);
}
