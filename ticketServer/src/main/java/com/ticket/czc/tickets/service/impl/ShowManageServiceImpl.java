package com.ticket.czc.tickets.service.impl;

import com.ticket.czc.tickets.dao.ShowDao;
import com.ticket.czc.tickets.factory.DaoFactory;
import com.ticket.czc.tickets.model.NumStatistics;
import com.ticket.czc.tickets.model.ShowsEntity;
import com.ticket.czc.tickets.service.ShowManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ShowManageServiceImpl implements ShowManageService {
    private static ShowManageServiceImpl showManageService=new ShowManageServiceImpl();
    public static ShowManageServiceImpl getInstance(){
        return showManageService;
    }

    private ShowDao showDao= DaoFactory.getShowDao();
//    @Autowired
//    private ShowDao showDao;

    @Override
    public Integer registerShow(ShowsEntity showsEntity) {
        int showId=showDao.save(showsEntity);
        return showId;
    }

    @Override
    public ArrayList<ShowsEntity> getAvailableShows() {
//        List shows=showDao.getAllAvailableShows();
        ArrayList<ShowsEntity> shows=showDao.getAllAvailableShows();
        return shows;
    }

    @Override
    public ShowsEntity getShowById(int id) {
        ShowsEntity show=showDao.getShowById(id);
        return show;
    }

    @Override
    public void updateShow(ShowsEntity show) {
        showDao.updateShow(show);
    }

    @Override
    public ArrayList<ShowsEntity> getFutureShowByVenue(int venueId) {
        return showDao.getFutureShowByVenue(venueId);
    }

    @Override
    public ArrayList<ShowsEntity> getAllShowByVenue(int venueId) {
        return showDao.getShowsByVenue(venueId);
    }

    @Override
    public ArrayList<NumStatistics> getShowNumbers() {
       ArrayList<NumStatistics> numStatistics=new ArrayList<>();
        ArrayList<String> types=showDao.getAllTypes();
        if(types==null){
            return null;
        }
        for (int i=0;i<types.size();i++){
            ArrayList<ShowsEntity> shows=showDao.getShowsByType(types.get(i));
            NumStatistics num=new NumStatistics();
            num.setName(types.get(i));
            num.setNum(shows.size());
            numStatistics.add(num);
        }
        return numStatistics;
    }
}
