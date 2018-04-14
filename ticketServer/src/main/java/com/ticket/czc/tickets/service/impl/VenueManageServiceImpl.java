package com.ticket.czc.tickets.service.impl;

import com.ticket.czc.tickets.common.Constant;
import com.ticket.czc.tickets.dao.UserDao;
import com.ticket.czc.tickets.dao.VenueDao;
import com.ticket.czc.tickets.factory.DaoFactory;
import com.ticket.czc.tickets.model.NumStatistics;
import com.ticket.czc.tickets.model.VenuesEntity;
import com.ticket.czc.tickets.service.VenueManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class VenueManageServiceImpl implements VenueManageService {
    private static VenueManageServiceImpl venueManageService=new VenueManageServiceImpl();
    public static VenueManageServiceImpl getInstance(){
        return venueManageService;
    }

    private VenueDao venueDao= DaoFactory.getVenueDao();
//    @Autowired
//    private VenueDao venueDao;

    @Override
    public String registerVenue(VenuesEntity venuesEntity) {
        if(venuesEntity==null){
            return "fail";
        }

        VenuesEntity venue=venueDao.findVenue(venuesEntity.getVenuename());
        if(venue!=null){
            return "exists";
        }

        venuesEntity.setIschecked(0);
        VenuesEntity venuesEntity1=venueDao.saveVenue(venuesEntity);
        if(venuesEntity1==null){
            return "fail";
        }
        return "success:"+venuesEntity1.getVenueid();
    }

    @Override
    public String validateVenue(int id, String password) {
        VenuesEntity venue=venueDao.findVenue(id);
        if(venue==null){
            return Constant.USER_NOT_EXITS;
        }

        if(!venue.getVenuepassword().equals(password)){
            return Constant.USER_ERROR_PASSWORD;
        }

        if(venue.getIschecked()==0){
            return Constant.USER_NOT_CHECK;
        }

        if(venue.getIschecked()==2){
            return Constant.USER_NOT_PASS;
        }



        return Constant.USER_SUCCESS_LOGIN;
    }

    @Override
    public VenuesEntity getVenueInfo(int id) {
        VenuesEntity venue=venueDao.findVenue(id);
        return venue;
    }

    @Override
    public VenuesEntity updateVenue(VenuesEntity venuesEntity) {
        VenuesEntity venue=venueDao.updateVenue(venuesEntity);
        return venue;
    }

    @Override
    public ArrayList<VenuesEntity> getUncheckedVenues() {
        return venueDao.getUncheckedVenue();
    }

    @Override
    public ArrayList<VenuesEntity> getCheckedVenues() {
        return venueDao.getCheckedVenue();
    }

    @Override
    public void checkVenue(int venueId, int state,String checkInfo) {
        VenuesEntity venuesEntity=venueDao.findVenue(venueId);
        venuesEntity.setIschecked(state);
        venuesEntity.setCheckInfo(checkInfo);
        venueDao.updateVenue(venuesEntity);
    }

    @Override
    public ArrayList<NumStatistics> getVenueNum() {
        ArrayList<NumStatistics> numStatistics=new ArrayList<>();
        ArrayList<VenuesEntity> checkedVenues=new ArrayList<>();
        ArrayList<VenuesEntity> unCheckedVenues=new ArrayList<>();
        checkedVenues=venueDao.getCheckedVenue();
        unCheckedVenues=venueDao.getUncheckedVenue();
        int checkNum=0;
        int unCheck=0;
        if(checkedVenues!=null){
            checkNum=checkedVenues.size();
        }
        if(unCheckedVenues!=null){
            unCheck=unCheckedVenues.size();
        }
        NumStatistics num1=new NumStatistics();
        NumStatistics num2=new NumStatistics();
        num1.setName("已审核场馆");
        num1.setNum(checkNum);
        num2.setName("未审核场馆");
        num2.setNum(unCheck);

        numStatistics.add(num1);
        numStatistics.add(num2);

        return numStatistics;
    }

    @Override
    public ArrayList<VenuesEntity> getAllVenues() {
        return venueDao.getAllVenues();
    }
}
