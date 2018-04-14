package com.ticket.czc.tickets.service.impl;

import com.ticket.czc.tickets.common.BackPercent;
import com.ticket.czc.tickets.common.Constant;
import com.ticket.czc.tickets.dao.OrderDao;
import com.ticket.czc.tickets.dao.impl.BaseDaoImpl;
import com.ticket.czc.tickets.factory.DaoFactory;
import com.ticket.czc.tickets.factory.ServiceFactory;
import com.ticket.czc.tickets.model.*;
import com.ticket.czc.tickets.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrderManageServiceImpl implements OrderManageService {
    private static OrderManageServiceImpl orderManageService=new OrderManageServiceImpl();
    public static OrderManageServiceImpl getInstance(){
        return orderManageService;
    }

    private SeatsManageService seatsManageService= ServiceFactory.getSeatsManageService();
    private AccountManageService accountManageService=ServiceFactory.getAccountManageService();
    private UsersManageService usersManageService=ServiceFactory.getUserManageService();
    private ShowManageService showManageService=ServiceFactory.getShowManageService();
    private VenueManageService venueManageService=ServiceFactory.getVenueManageService();
    private OrderDao orderDao= DaoFactory.getOrderDao();
//    @Autowired
//    private SeatsManageService seatsManageService;
//    @Autowired
//    private AccountManageService accountManageService;
//    @Autowired
//    private UsersManageService usersManageService;
//    @Autowired
//    private ShowManageService showManageService;
//    @Autowired
//    private VenueManageService venueManageService;

//    @Autowired
//    private  OrderDao orderDao;

    private ArrayList<OrdersEntity> unCountOrders=new ArrayList<>();

    @Override
    public Integer generateOrder(OrdersEntity ordersEntity) {
        int orderid=orderDao.saveOrder(ordersEntity);
        return orderid;
    }

    @Override
    public OrdersEntity getOrder(int orderId) {
        OrdersEntity order=orderDao.getOrder(orderId);
        return order;
    }

    @Override
    public void updateOrder(OrdersEntity ordersEntity) {
        orderDao.updateOrder(ordersEntity);
    }

    @Override
    public void deleteOrder(OrdersEntity ordersEntity) {

    }

    @Override
    public ArrayList<OrdersEntity> getOverDueOrders() {
        return orderDao.getOverDueOrders();
    }

    @Override
    public ArrayList<OrdersEntity> getOrdersByUser(String userEmail) {
        return orderDao.getOrdersByUser(userEmail);
    }

    @Override
    public String backOrder(int orderId) {
        OrdersEntity order=orderDao.getOrder(orderId);
        long orderTime=order.getShowtime().getTime();
        long nowTime=System.currentTimeMillis();
        long min=orderTime-nowTime;
        double percent= BackPercent.getReducePercent(min);
        order.setBeforebackprice(order.getTotalprice());

        //恢复这笔订单的座位为可以预定的状态
        ArrayList<SeatsEntity> seats=seatsManageService.getSeatsByOrder(orderId);
        for(int i=0;i<seats.size();i++){
            seats.get(i).setSeatisbooked(0);
        }
        seatsManageService.updateSeats(seats);

        ShowsEntity showsEntity=showManageService.getShowById(order.getShowid());
        showsEntity.setRestseats(showsEntity.getRestseats()+order.getQuantity());
        showManageService.updateShow(showsEntity);

        order.setOrderstatus(3);
        double oldPrice=order.getTotalprice();
        double backMoney=oldPrice*percent;
        double nowPrice=oldPrice-backMoney;
        order.setTotalprice(nowPrice);
        orderDao.updateOrder(order);

        String userEmail=order.getUseremail();
        String accountId=userEmail;
        UsersEntity user=usersManageService.getUserInfo(userEmail);
        user.setCredit(user.getCredit()-((int)backMoney+1));
        user.setUserconsume(user.getUserconsume()-backMoney);
        usersManageService.updateUser(user);

        accountManageService.addMoney(accountId,backMoney);
        accountManageService.consumeAccount(Constant.ADMIN_ACCOUNT,Constant.ADMIN_PASSWORD,backMoney);

        return "success";
    }

    @Override
    public ArrayList<String> checkOrder(int orderId,int venueId) {
        ArrayList<String> res=new ArrayList<>();
        OrdersEntity order=orderDao.getOrder(orderId);
        if (order==null){
            res.add("fail");
            res.add("没有此订单！");
            return res;
        }
        int showId=order.getShowid();
        ShowsEntity show=showManageService.getShowById(showId);
        int venue=show.getVenueid();
        if(venue!=venueId){
            res.add("fail");
            res.add("该订单不是本场馆发布的！");
            return res;
        }
        if (order.getOrderstatus()==0){
            res.add("fail");
            res.add("该订单尚未付款！");
            return res;
        }
        if (order.getOrderstatus()==1){
            res.add("fail");
            res.add("该订单因未在规定时间内付款已被取消！");
            return res;
        }
        if (order.getOrderstatus()==3){
            res.add("fail");
            res.add("该订单已被撤回销毁！");
            return res;
        }
        if (order.getHascheck().equals("是")){
            res.add("fail");
            res.add("该订单已被检票过！");
            return res;
        }

        order.setHascheck("是");
        orderDao.updateOrder(order);
        res.add("success");
        return res;

    }

    @Override
    public ArrayList<CountInfo> getCountInfo() {
        ArrayList<CountInfo> countInfos=new ArrayList<>();
        ArrayList<Integer> showIds=orderDao.getUncountShowId();
        if(showIds==null||showIds.size()==0){
            return null;
        }
        for(int i=0;i<showIds.size();i++){
            System.out.println("uncountShowId"+showIds.get(i));
            ArrayList<OrdersEntity> orders=orderDao.getUnCountOrderByShow(showIds.get(i));
            System.out.println(showIds.get(i));
            System.out.println(orders.size());
            unCountOrders.addAll(orders);
            CountInfo countInfo=new CountInfo();
            int showId=orders.get(0).getShowid();
            ShowsEntity show=showManageService.getShowById(showId);
            countInfo.setShowId(showId);
            countInfo.setShowName(show.getShowname());
            countInfo.setShowDescribe(show.getShowdescribe());
            countInfo.setShowPostTime(show.getPosttime());
            countInfo.setShowTime(show.getShowtime());
            countInfo.setShowType(show.getShowtype());
            int venueId=show.getVenueid();
            VenuesEntity venue=venueManageService.getVenueInfo(venueId);
            countInfo.setVenueName(venue.getVenuename());
            double origin=0.0;
            double venueM=0.0;
            double adminM=0.0;
            for(int j=0;j<orders.size();j++){
                double price=orders.get(j).getTotalprice();
                origin=origin+price;

            }
            venueM=venueM+origin*Constant.BENIFIT;
            adminM=adminM+origin-venueM;
            countInfo.setOriginMoney(origin);
            countInfo.setVenueGet(venueM);
            countInfo.setAdminGet(adminM);
            countInfos.add(countInfo);

        }
        return countInfos;

    }

    @Override
    public ArrayList<String> payForVenue(int showId,double venueM,double adminM) {
        ArrayList<String> res=new ArrayList<>();
        if (unCountOrders==null||unCountOrders.size()==0){
            res.add("fail");
            res.add("操作失败，请刷新界面！");
            return res;
        }
        double percent=Constant.BENIFIT;
        ArrayList<OrdersEntity> orders=new ArrayList<>();
        for(int i=0;i<unCountOrders.size();i++){
            if(unCountOrders.get(i).getShowid()==showId && unCountOrders.get(i).getIscount()==0){
                System.out.println("即将结算的orderId"+unCountOrders.get(i).getOrderid());
                unCountOrders.get(i).setIscount(1);
//                unCountOrders.get(i).setVenueget(unCountOrders.get(i).getTotalprice()* percent);
                orders.add(unCountOrders.get(i));
            }
        }
        if(orders==null||orders.size()==0){
            res.add("success");
            return res;
        }
        ShowsEntity show=showManageService.getShowById(showId);
        show.setVenueget(show.getVenueget()+venueM);
        show.setAdminget(show.getAdminget()+adminM);
        showManageService.updateShow(show);
        orderDao.updateOrders(orders);
        accountManageService.consumeAccount(Constant.ADMIN_ACCOUNT,Constant.ADMIN_PASSWORD,venueM);
        res.add("success");
        return res;
    }

    @Override
    public ArrayList<VenueShowInfo> getVenueShowByVenue(int venueId) {
        ArrayList<VenueShowInfo> venueShowInfos=new ArrayList<>();
        ArrayList<ShowsEntity> shows=showManageService.getAllShowByVenue(venueId);
        if(shows==null){
            return null;
        }
        for(int i=0;i<shows.size();i++){
            VenueShowInfo venueShowInfo=new VenueShowInfo();
            venueShowInfo.setShow(shows.get(i));
            int showId=shows.get(i).getShowid();
            int checkOrders=0;
            int checkSeats=0;
            ArrayList<OrdersEntity> onlineOrders=orderDao.getPayOnlineOrdersByShow(showId);
            if(onlineOrders==null){
                venueShowInfo.setOnlinePayNum(0);
                venueShowInfo.setOnlinePayIncome(0.0);
            }else{
                venueShowInfo.setOnlinePayNum(onlineOrders.size());
                double onlinePay=0.0;
                for(int j=0;j<onlineOrders.size();j++){
                    onlinePay=onlinePay+onlineOrders.get(j).getTotalprice()*Constant.BENIFIT;
                    if(onlineOrders.get(j).getHascheck().equals("是")){
                        checkOrders++;
                        checkSeats=checkSeats+onlineOrders.get(j).getQuantity();
                    }
                }
                venueShowInfo.setOnlinePayIncome(onlinePay);
            }

            ArrayList<OrdersEntity> realOrders=orderDao.getPayRealOrdersByShow(showId);
            if(realOrders==null){
                venueShowInfo.setRealPayNum(0);
                venueShowInfo.setRealPayIncome(0.0);
            }
            else {
                venueShowInfo.setRealPayNum(realOrders.size());
                double realPay=0.0;
                for(int j=0;j<realOrders.size();j++){
                    realPay=realPay+realOrders.get(j).getTotalprice();
                    if(realOrders.get(j).getHascheck().equals("是")){
                        checkOrders++;
                        checkSeats=checkSeats+realOrders.get(j).getQuantity();
                    }
                }
                venueShowInfo.setRealPayIncome(realPay);
            }

            venueShowInfo.setCheckOrders(checkOrders);
            venueShowInfo.setCheckSeats(checkSeats);

            ArrayList<OrdersEntity> backOrders=orderDao.getBackOrderByShow(showId);
            if (backOrders==null){
                venueShowInfo.setBackNum(0);
                venueShowInfo.setBackIncome(0.0);
            }else {
                venueShowInfo.setBackNum(backOrders.size());
                double backPay=0.0;
                for(int j=0;j<backOrders.size();j++){
                    backPay=backPay+backOrders.get(j).getTotalprice()*Constant.BENIFIT;
                }
                venueShowInfo.setBackIncome(backPay);
            }
            venueShowInfos.add(venueShowInfo);
        }
        return venueShowInfos;
    }

    @Override
    public TicketsOrderInfo getTicketsOrderInfo() {
        ArrayList<OrdersEntity> onlineOrders=orderDao.getAllOnlineOrders();
        ArrayList<OrdersEntity> realOrders=orderDao.getAllRealOrders();
        ArrayList<OrdersEntity> backOrders=orderDao.getAllBackOrders();
        double onlineOrderPrice=0.0;
        double realOrderPrice=0.0;
        double backOrderPrice=0.0;
        int onlineOrderNum=0;
        int realorderNum=0;
        int backOrderNum=0;

        if(onlineOrders!=null){
            onlineOrderNum=onlineOrders.size();
            for(int i=0;i<onlineOrderNum;i++){
                onlineOrderPrice=onlineOrderPrice+onlineOrders.get(i).getTotalprice();
            }
        }
        if(realOrders!=null){
            realorderNum=realOrders.size();
            for(int i=0;i<realorderNum;i++){
                realOrderPrice=realOrderPrice+realOrders.get(i).getTotalprice();
            }
        }
        if(backOrders!=null){
            backOrderNum=backOrders.size();
            for (int i=0;i<backOrderNum;i++){
                backOrderPrice=backOrderPrice+backOrders.get(i).getTotalprice();
            }
        }

        TicketsOrderInfo ticketsOrderInfo=new TicketsOrderInfo();
        ticketsOrderInfo.setOnlineOrderNum(onlineOrderNum);
        ticketsOrderInfo.setOnlineOrderPrice(onlineOrderPrice);
        ticketsOrderInfo.setOnlineOrderBenifit(onlineOrderPrice-onlineOrderPrice*Constant.BENIFIT);
        ticketsOrderInfo.setRealOrderNum(realorderNum);
        ticketsOrderInfo.setRealOrderPrice(realOrderPrice);
        ticketsOrderInfo.setRealOrderBenifit(0.0);
        ticketsOrderInfo.setBackOrderNum(backOrderNum);
        ticketsOrderInfo.setBackOrderPrice(backOrderPrice);
        ticketsOrderInfo.setBackOrderBenifit(backOrderPrice-backOrderPrice*Constant.BENIFIT);

        return ticketsOrderInfo;
    }

    public static void main(String[] args){

    }
}
