package com.ticket.czc.tickets.factory;

import com.ticket.czc.tickets.dao.impl.ValidateDaoImpl;
import com.ticket.czc.tickets.service.*;
import com.ticket.czc.tickets.service.impl.*;
import com.ticket.czc.tickets.service.implservice.EmailCheck;
import org.hibernate.criterion.Order;

public class ServiceFactory {

    public static UsersManageService getUserManageService(){
        return UsersManageServiceImpl.getInstance();
    }

    public static VenueManageService getVenueManageService(){
        return VenueManageServiceImpl.getInstance();
    }

    public static ShowManageService getShowManageService(){
        return ShowManageServiceImpl.getInstance();
    }

    public static SeatsManageService getSeatsManageService(){
        return SeatsManageServiceImpl.getInstance();
    }

    public static OrderManageService getOrderManageService(){
        return OrderManageServiceImpl.getInstance();
    }

    public static AccountManageService getAccountManageService(){
        return AccountManageServiceImpl.getInstance();
    }

    public static CouponManageService getCouponManageService(){
        return CouponManageServiceImpl.getInstance();
    }

    public static EmailCheck getEmailCheckService(){
        return EmailCheck.getInstance();
    }

    public static ValidateServiceImpl getValidateService(){
        return ValidateServiceImpl.getInstance();
    }
}
