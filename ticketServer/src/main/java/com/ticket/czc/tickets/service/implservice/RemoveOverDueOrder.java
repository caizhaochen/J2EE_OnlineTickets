package com.ticket.czc.tickets.service.implservice;

import com.ticket.czc.tickets.factory.ServiceFactory;
import com.ticket.czc.tickets.model.OrdersEntity;
import com.ticket.czc.tickets.model.SeatsEntity;
import com.ticket.czc.tickets.service.OrderManageService;
import com.ticket.czc.tickets.service.SeatsManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RemoveOverDueOrder {
    private static OrderManageService orderManageService= ServiceFactory.getOrderManageService();
    private static SeatsManageService seatsManageService=ServiceFactory.getSeatsManageService();

//    @Autowired
//    private OrderManageService orderManageService;
//    @Autowired
//    private SeatsManageService seatsManageService;

    public void removeOverDueOrders(){
        ArrayList<OrdersEntity> orders=orderManageService.getOverDueOrders();
        if (orders==null||orders.size()<1){
            return;
        }
        for(int i=0;i<orders.size();i++){
            removeOneOrder(orders.get(i));
        }

    }

    public void removeOneOrder(OrdersEntity order){
        int orderId=order.getOrderid();
        ArrayList<SeatsEntity> seats=seatsManageService.getSeatsByOrder(orderId);
        for(int i=0;i<seats.size();i++){
            seats.get(i).setSeatisbooked(0);
        }
        seatsManageService.updateSeats(seats);
        order.setOrderstatus(1);
        orderManageService.updateOrder(order);
    }
}
