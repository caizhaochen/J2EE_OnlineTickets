package com.ticket.czc.tickets.controller.coupons;

import com.ticket.czc.tickets.factory.ServiceFactory;
import com.ticket.czc.tickets.model.CouponsEntity;
import com.ticket.czc.tickets.model.OrdersEntity;
import com.ticket.czc.tickets.service.CouponManageService;
import com.ticket.czc.tickets.service.OrderManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.ldap.PagedResultsControl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;

@Controller

@RequestMapping("/coupon")
public class CouponController {

    private CouponManageService couponManageService= ServiceFactory.getCouponManageService();
    private OrderManageService orderManageService=ServiceFactory.getOrderManageService();

//    @Autowired
//    private CouponManageService couponManageService;

    @RequestMapping("/getCoupons")
    @ResponseBody
    public ArrayList<CouponsEntity> getCoupons(HttpServletRequest request)throws IOException{
        HttpSession session=request.getSession();
        String userEmail=(String )session.getAttribute("userId");
        int orderId=(int)session.getAttribute("orderId");
        OrdersEntity order=orderManageService.getOrder(orderId);
        double price=order.getTotalprice();
        return couponManageService.getAvailableCouponForOrderPrice(userEmail,price);
    }
}
