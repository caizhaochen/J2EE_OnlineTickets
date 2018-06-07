package com.ticket.czc.tickets.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StartController {
    @RequestMapping("/")
    public String start(){
        return "/user/login";
    }

    @RequestMapping("/venues")
    public String startVenues(){
        return "/venue/welcomeVenue";
    }

    @RequestMapping("/admin")
    public String startAdmin(){
        return "/managers/managerLogin";
    }

    @RequestMapping("/userLogin")
    public String userLogin(){
        return "/user/login";
    }

    @RequestMapping("/venueLogin")
    public String venueLogin(){
        return "/venue/venueLogin";
    }
}
