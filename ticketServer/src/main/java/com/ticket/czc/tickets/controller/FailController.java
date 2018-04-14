package com.ticket.czc.tickets.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FailController {

    @RequestMapping("/venueNotCheck")
    public String venueNotCheck(){
        return "/venue/venueNotCheck";
    }
}
