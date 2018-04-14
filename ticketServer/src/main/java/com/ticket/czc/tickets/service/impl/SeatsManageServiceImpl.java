package com.ticket.czc.tickets.service.impl;

import com.ticket.czc.tickets.dao.SeatsDao;
import com.ticket.czc.tickets.factory.DaoFactory;
import com.ticket.czc.tickets.model.SeatsEntity;
import com.ticket.czc.tickets.service.SeatsManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SeatsManageServiceImpl implements SeatsManageService {
    private static SeatsManageServiceImpl seatsManageService=new SeatsManageServiceImpl();

    public static SeatsManageServiceImpl getInstance(){
        return seatsManageService;
    }

    private SeatsDao seatsDao= DaoFactory.getSeatsDao();
//    @Autowired
//    private SeatsDao seatsDao;

    @Override
    public boolean saveSeats(ArrayList<SeatsEntity> seatsEntities) {
        Boolean res=false;
        if (seatsEntities.size()<1||seatsEntities==null){
            return res;
        }

        seatsDao.save(seatsEntities);
        res=true;
        return res;
    }

    @Override
    public ArrayList<SeatsEntity> getSeatsByShow(int showID) {
        ArrayList<SeatsEntity> seats=seatsDao.getSeatsByShowId(showID);
        return seats;
    }

    @Override
    public ArrayList<SeatsEntity> getSeatsByOrder(int orderID) {
        return seatsDao.getSeatsByOrderId(orderID);
    }

    @Override
    public ArrayList<SeatsEntity> getRandomSeats(int showId, int num) {
        return seatsDao.getSeatsByNum(showId,num);
    }

    @Override
    public Boolean isAvailable(int showId, ArrayList<Integer> nums) {
        if(nums.size()==0){
            return true;
        }

        for(int i=0;i<nums.size();i++){
            if(!seatsDao.isAvailable(showId,nums.get(i))){
                return false;
            }
        }

        return true;
    }

    @Override
    public void lockSeats(int showId, ArrayList<Integer> nums) {
        for(int i=0;i<nums.size();i++){
            seatsDao.lockSeat(showId,nums.get(i));
        }
    }

    @Override
    public SeatsEntity getSeat(int showId, int num) {
        return seatsDao.getSeat(showId,num);
    }

    @Override
    public void updateSeats(ArrayList<SeatsEntity> seats) {
        if(seats.size()==1){
            seatsDao.updateSeat(seats.get(0));
        }
        else if(seats.size()>1){
            seatsDao.updateSeats(seats);
        }
    }

    @Override
    public ArrayList<SeatsEntity> getSeats(int showId, ArrayList<Integer> nums) {
        ArrayList<SeatsEntity> seatsEntities=new ArrayList<>();
        for(int i=0;i<nums.size();i++){
            seatsEntities.add(seatsDao.getSeat(showId,nums.get(i)));
        }
        return seatsEntities;
    }
}
