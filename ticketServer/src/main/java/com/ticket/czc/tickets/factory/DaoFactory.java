package com.ticket.czc.tickets.factory;

import com.ticket.czc.tickets.dao.*;
import com.ticket.czc.tickets.dao.impl.*;

public class DaoFactory {

    public static UserDao getUserDao(){
        return UserDaoImpl.getInstance();
    }

    public static VenueDao getVenueDao(){
        return VenueDaoImpl.getInstance();
    }

    public static ShowDao getShowDao(){
        return ShowDaoImpl.getInstance();
    }

    public static SeatsDao getSeatsDao(){
        return SeatsDaoImpl.getInstance();
    }

    public static OrderDao getOrderDao(){
        return OrderDaoImpl.getInstance();
    }

    public static AccountDao getAccountDao(){
        return AccountDaoImpl.getInstance();
    }

    public static CouponDao getCouponDao(){
        return CouponDaoImpl.getInstance();
    }

    public static ValidateDao getVaidateDao(){
        return ValidateDaoImpl.getInstance();
    }

    public static FavoriteDao getFavoriteDao(){
        return FavoriteDaoImpl.getInstance();
    }
}
