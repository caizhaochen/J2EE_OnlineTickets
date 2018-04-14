package com.ticket.czc.tickets.service;

import com.ticket.czc.tickets.model.CountInfo;
import com.ticket.czc.tickets.model.OrdersEntity;
import com.ticket.czc.tickets.model.TicketsOrderInfo;
import com.ticket.czc.tickets.model.VenueShowInfo;

import java.util.ArrayList;

public interface OrderManageService {

    public Integer generateOrder(OrdersEntity ordersEntity);

    public void updateOrder(OrdersEntity ordersEntity);

    public void deleteOrder(OrdersEntity ordersEntity);

    public OrdersEntity getOrder(int orderId);

    public ArrayList<OrdersEntity> getOverDueOrders();

    public ArrayList<OrdersEntity> getOrdersByUser(String userEmail);

    public String backOrder(int orderId);

    public ArrayList<String> checkOrder(int orderId,int venueId);

    public ArrayList<CountInfo> getCountInfo();

    public ArrayList<String> payForVenue(int showId,double venueM,double adminM);

    //根据每个场馆的id获取每个表演的在线预定/线下预定/退订
    public ArrayList<VenueShowInfo> getVenueShowByVenue(int venueId);

    //用于admin获取财务统计
    public TicketsOrderInfo getTicketsOrderInfo();
}
