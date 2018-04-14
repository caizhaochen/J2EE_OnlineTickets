package com.ticket.czc.tickets.controller.shows;

import com.ticket.czc.tickets.factory.ServiceFactory;
import com.ticket.czc.tickets.model.SeatsEntity;
import com.ticket.czc.tickets.model.ShowsEntity;
import com.ticket.czc.tickets.service.SeatsManageService;
import com.ticket.czc.tickets.service.ShowManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.PanelUI;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller

@RequestMapping("/show")
public class ShowController {

    private ShowManageService showManageService= ServiceFactory.getShowManageService();
    private SeatsManageService seatsManageService=ServiceFactory.getSeatsManageService();
//    @Autowired
//    private ShowManageService showManageService;
//    @Autowired
//    private SeatsManageService seatsManageService;

    @RequestMapping("/register/{showInfo}")
    @ResponseBody
    public String showRegister(@PathVariable("showInfo")List showInfo, HttpServletRequest request) throws IOException{
        HttpSession session=request.getSession(false);
        String idString=String.valueOf(session.getAttribute("venueId"));
        if (idString==null||idString==""){
            return "noVenue";
        }
        int venueId=Integer.valueOf(idString);
        String showName=(String)showInfo.get(0);
        String showType=(String)showInfo.get(1);
        Timestamp showTime=Timestamp.valueOf((String) showInfo.get(2));
        String showDes=(String) showInfo.get(3);
        int showLine=Integer.valueOf((String) showInfo.get(4));
        int showRow=Integer.valueOf((String) showInfo.get(5));
        ShowsEntity show=new ShowsEntity();
        show.setShowname(showName);
        show.setShowtype(showType);
        show.setShowtime(showTime);
        show.setShowdescribe(showDes);
        show.setVenueid(venueId);
        show.setShowline(showLine);
        show.setShowrow(showRow);
        int restSeats=showLine*showRow;
        show.setRestseats(restSeats);
        show.setPosttime(new Timestamp(System.currentTimeMillis()));
        show.setVenueget(0.0);
        show.setAdminget(0.0);

        int showId=showManageService.registerShow(show);

        ArrayList<SeatsEntity> seats=new ArrayList<>();
        int priceNum=Integer.parseInt((String)showInfo.get(6));
        for (int i=1;i<=priceNum;i++){
            String priceName=(String)showInfo.get(6+(i-1)*4+1);
            int seatLeft=Integer.parseInt((String)showInfo.get(6+(i-1)*4+2));
            int seatRight=Integer.parseInt((String)showInfo.get(6+(i-1)*4+3));
            double seatPrice=Double.parseDouble((String)showInfo.get(6+(i-1)*4+4));
            for(int seatNum=seatLeft;seatNum<=seatRight;seatNum++){
                SeatsEntity seat=new SeatsEntity();
                seat.setShowid(showId);
                seat.setVenueid(venueId);
                seat.setSeatnum(seatNum);
                seat.setSeatprice(seatPrice);
                seat.setSeatisbooked(0);
                seats.add(seat);
                System.out.println("----size"+seats.size());
            }
        }

        System.out.println(seats.size());
        Boolean saveSeats=seatsManageService.saveSeats(seats);
        return String.valueOf(saveSeats);
//        System.out.println(showInfo);
    }

    @RequestMapping("/getAvailableShows")
    @ResponseBody
    public ArrayList<ShowsEntity> getAvailableShows(){
        return showManageService.getAvailableShows();
    }

    @RequestMapping("/getCurrentShow")
    @ResponseBody
    public ShowsEntity getCurrentShow(HttpServletRequest request)throws IOException{
        HttpSession session=request.getSession();
        int showId=(Integer)session.getAttribute("showId");
        ShowsEntity show=showManageService.getShowById(showId);
        return show;
    }

    @RequestMapping("/getFutureShowByVenue")
    @ResponseBody
    public ArrayList<ShowsEntity> getFutureShowByVenue(HttpServletRequest request)throws IOException{
        HttpSession session=request.getSession();
        int venueId=(int)session.getAttribute("venueId");
        return showManageService.getFutureShowByVenue(venueId);
    }

    @RequestMapping("/getAllShow")
    @ResponseBody
    public ArrayList<ShowsEntity> getAllShow(HttpServletRequest request)throws IOException{
        HttpSession session=request.getSession();
        int venueId=(int) session.getAttribute("venueId");
        return showManageService.getAllShowByVenue(venueId);
    }
}
