package com.ticket.czc.tickets.controller.admin;

import com.ticket.czc.tickets.factory.ServiceFactory;
import com.ticket.czc.tickets.model.NumStatistics;
import com.ticket.czc.tickets.model.TicketsOrderInfo;
import com.ticket.czc.tickets.model.VenueShowInfo;
import com.ticket.czc.tickets.model.VenuesEntity;
import com.ticket.czc.tickets.service.OrderManageService;
import com.ticket.czc.tickets.service.ShowManageService;
import com.ticket.czc.tickets.service.UsersManageService;
import com.ticket.czc.tickets.service.VenueManageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")

public class AdminController {
    private UsersManageService usersManageService= ServiceFactory.getUserManageService();
    private VenueManageService venueManageService=ServiceFactory.getVenueManageService();
    private ShowManageService showManageService=ServiceFactory.getShowManageService();
    private OrderManageService orderManageService=ServiceFactory.getOrderManageService();

    //统计场馆数，会员数，演出总数
    @RequestMapping("/getUserNumbers")
    @ResponseBody
    public ArrayList<NumStatistics> getUserNumbers(){

        return  usersManageService.getUserNum();

    }
    @RequestMapping("/getVenueNumbers")
    @ResponseBody
    public ArrayList<NumStatistics> getVenueNumbers(){
        return venueManageService.getVenueNum();
    }
    @RequestMapping("/getShowNumbers")
    @ResponseBody
    public ArrayList<NumStatistics> getShowNumbers(){

        return  showManageService.getShowNumbers();
    }

    @RequestMapping("/getLevelNum")
    @ResponseBody
    public ArrayList<NumStatistics> getLevelNum(){
        return usersManageService.getLevelNum();
    }

    @RequestMapping("/getAllVenues")
    @ResponseBody
    public ArrayList<VenuesEntity> getAllVenues(){
        return venueManageService.getAllVenues();
    }

    @RequestMapping("/getVenueShowInfo/{venueId}")
    @ResponseBody
    public ArrayList<VenueShowInfo> getVenueShowInfo(@PathVariable("venueId")int venueId) {

        return orderManageService.getVenueShowByVenue(venueId);
    }

    @RequestMapping("/getTicketsOrderInfo")
    @ResponseBody
    public TicketsOrderInfo getTicketsOrderInfo(){
        return orderManageService.getTicketsOrderInfo();
    }
}
