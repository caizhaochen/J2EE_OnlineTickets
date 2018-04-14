package com.ticket.czc.tickets.controller;

import com.ticket.czc.tickets.factory.ServiceFactory;
import com.ticket.czc.tickets.model.UsersEntity;
import com.ticket.czc.tickets.service.UsersManageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/tickets")
public class HomeController {
    private UsersManageService usersManageService= ServiceFactory.getUserManageService();

    @RequestMapping("/getUserInfo")
    @ResponseBody
    public UsersEntity getUserInfo(HttpServletRequest request)throws IOException{
        HttpSession session=request.getSession(false);
        String email=(String)session.getAttribute("userId");
        UsersEntity usersEntity=usersManageService.getUserInfo(email);
        return usersEntity;
    }

    @RequestMapping("/home")
    public String home(){
        return "/user/home";
    }

    @RequestMapping("/venueHome")
    public String venueHome(){
        return "/venue/venueHome";
    }

}
