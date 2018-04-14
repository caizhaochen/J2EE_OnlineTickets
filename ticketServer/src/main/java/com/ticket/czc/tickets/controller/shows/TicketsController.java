package com.ticket.czc.tickets.controller.shows;

import com.ticket.czc.tickets.factory.ServiceFactory;
import com.ticket.czc.tickets.model.SeatsEntity;
import com.ticket.czc.tickets.service.SeatsManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

@Controller
public class TicketsController {
    private SeatsManageService seatsManageService= ServiceFactory.getSeatsManageService();
//    @Autowired
//    private SeatsManageService seatsManageService;

    @RequestMapping("/showTickets")
    public String showTickets(){
        return "/show/showTickets";
    }

    @RequestMapping("/bookSeats/{showid}")
    public String bookSeats(@PathVariable("showid") int showid, HttpServletRequest request)throws IOException{
        HttpSession session=request.getSession();
        session.setAttribute("showId",showid);
        return "/user/bookSeats";
    }
    @RequestMapping("/randomSeats/{showid}")
    public String randomSeats(@PathVariable("showid") int showid, HttpServletRequest request)throws IOException{
        HttpSession session=request.getSession();
        session.setAttribute("showId",showid);
        return "/user/randomSeats";
    }
    @RequestMapping("/venueBookSeats/{showid}")
    public String venueBookSeats(@PathVariable("showid") int showid, HttpServletRequest request)throws IOException{
        HttpSession session=request.getSession();
        session.setAttribute("showId",showid);
        return "/venue/venueBookSeats";
    }
    @RequestMapping("/venueRandomSeats/{showid}")
    public String venueRandomSeats(@PathVariable("showid") int showid, HttpServletRequest request)throws IOException{
        HttpSession session=request.getSession();
        session.setAttribute("showId",showid);
        return "/venue/venueRandomSeats";
    }

    @RequestMapping("/chooseSeats")
    @ResponseBody
    public ArrayList<SeatsEntity> getSeatsStatus(HttpServletRequest request)throws IOException{
        HttpSession session=request.getSession();
        int showId=(Integer)session.getAttribute("showId");
        ArrayList<SeatsEntity> seatsEntities=seatsManageService.getSeatsByShow(showId);
        return seatsEntities;
    }
}
