package com.ticket.czc.tickets.dao;

import com.ticket.czc.tickets.model.OrdersEntity;

import java.util.ArrayList;

public interface OrderDao {

    public Integer saveOrder(OrdersEntity ordersEntity);

    public OrdersEntity getOrder(int orderId);

    public void updateOrder(OrdersEntity ordersEntity);

    public ArrayList<OrdersEntity> getOverDueOrders();

    public ArrayList<OrdersEntity> getOrdersByUser(String userEmail);

    public ArrayList<OrdersEntity> getUnCountOrderByShow(int showId);

    public ArrayList<Integer> getUncountShowId();

    public void updateOrders(ArrayList<OrdersEntity> orders);

    public ArrayList<OrdersEntity> getPayOnlineOrdersByShow(int showId);
    public ArrayList<OrdersEntity> getPayRealOrdersByShow(int showId);

    public ArrayList<OrdersEntity> getBackOrderByShow(int showId);

    public ArrayList<OrdersEntity> getAllOnlineOrders();
    public ArrayList<OrdersEntity> getAllRealOrders();
    public ArrayList<OrdersEntity> getAllBackOrders();
}
