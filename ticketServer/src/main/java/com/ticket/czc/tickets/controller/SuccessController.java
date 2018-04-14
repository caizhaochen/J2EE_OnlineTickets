package com.ticket.czc.tickets.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SuccessController {

    @RequestMapping("/registerSuccess")
    public String registerSuccess(){
        return "/user/registerSuccess";
    }

    @RequestMapping("/venues/registerSuccess")
    public String venueRegisterSuccess(){
        return "/venue/venueRegisterSuccess";
    }

    @RequestMapping("/venues/modifySuccess")
    public String venueModifySuccess(){
        return "/venue/venueModifySuccess";
    }

    @RequestMapping("/order/paySuccess")
    public String paySuccess(){
        return "/order/paySuccess";
    }

    @RequestMapping("/order/backSuccess")
    public String orderBackSuccess(){
        return "/order/backSuccess";
    }
}
