package com.ticket.czc.tickets.controller.orders;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.ticket.czc.tickets.common.BackPercent;
import com.ticket.czc.tickets.common.Constant;
import com.ticket.czc.tickets.common.LevelDiscount;
import com.ticket.czc.tickets.factory.ServiceFactory;
import com.ticket.czc.tickets.model.*;
import com.ticket.czc.tickets.service.*;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

@Controller

@RequestMapping("/order")
public class OrderController {
    private OrderManageService orderManageService= ServiceFactory.getOrderManageService();
    private SeatsManageService seatsManageService=ServiceFactory.getSeatsManageService();
    private UsersManageService usersManageService=ServiceFactory.getUserManageService();
    private ShowManageService showManageService=ServiceFactory.getShowManageService();
    private AccountManageService accountManageService=ServiceFactory.getAccountManageService();
    private CouponManageService couponManageService=ServiceFactory.getCouponManageService();
//    @Autowired
//    private OrderManageService orderManageService;
//    @Autowired
//    private SeatsManageService seatsManageService;
//    @Autowired
//    private UsersManageService usersManageService;
//    @Autowired
//    private ShowManageService showManageService;
//    @Autowired
//    private AccountManageService accountManageService;
//    @Autowired
//    private CouponManageService couponManageService;

    public static final int TIMELIMIT = 1000*60*15;

    //用户选座购票
    @RequestMapping("/registerOrder/{orderInfo}")
    @ResponseBody
    public ArrayList<Object> registerOrderBySeats(@PathVariable("orderInfo")ArrayList<String> orderInfo, HttpServletRequest request, HttpServletResponse response) throws IOException{
        ArrayList<Object> list=new ArrayList<Object>();
        if (orderInfo.size()==0){
            list.add("fail");
            list.add("数据传输失败！");
            return list;
        }
        HttpSession session=request.getSession();
        String userId=(String)session.getAttribute("userId");
        int showId=(Integer)session.getAttribute("showId");
        int quantity=Integer.parseInt(orderInfo.get(0));
        double price=Double.parseDouble(orderInfo.get(1));
        Long nowTime=System.currentTimeMillis();
        Long orderDDL=nowTime+TIMELIMIT;
        Timestamp orderTime=new Timestamp(nowTime);
        Timestamp orderDeadline=new Timestamp(orderDDL);
        OrdersEntity order=new OrdersEntity();
        order.setShowid(showId);
        order.setUseremail(userId);
        order.setQuantity(quantity);
        order.setOriginprice(price);
        order.setTotalprice(price);
        order.setOrdertime(orderTime);
        order.setOrderdeadline(orderDeadline);
        order.setIspayed(0);
        order.setOrderstatus(0);
        order.setHascheck("否");

        ShowsEntity show=showManageService.getShowById(showId);
        order.setShowtime(show.getShowtime());
        order.setVenueid(show.getVenueid());
        order.setIscount(0);

        ArrayList<Integer> seatsNum=new ArrayList<>();
        for(int i=2;i<orderInfo.size();i++){
            seatsNum.add(Integer.parseInt(orderInfo.get(i)));
        }

        boolean isAllAvailable=seatsManageService.isAvailable(showId,seatsNum);

        if(isAllAvailable){
            int orderId=orderManageService.generateOrder(order);

            seatsManageService.lockSeats(showId,seatsNum);
            UsersEntity user=usersManageService.getUserInfo(userId);
            int userLevel=user.getLevel();
            double discount= LevelDiscount.discount[userLevel];
            ArrayList<SeatsEntity> seats=seatsManageService.getSeats(showId,seatsNum);
            double realOrderPrice=0.0;
            for(int i=0;i<seats.size();i++){
                double seatPrice=seats.get(i).getSeatprice();
                seats.get(i).setSeatrealprice(seatPrice*discount);
                realOrderPrice=realOrderPrice+seatPrice*discount;
                seats.get(i).setOrderid(orderId);
            }

            String seatsChart="";
            if(quantity==1){
                seatsChart=String.valueOf(seatsNum.get(0));
            }
            else{
                for(int i=0;i<seatsNum.size()-1;i++){
                    seatsChart=seatsChart+String.valueOf(seatsNum.get(i))+"/";
                }
                seatsChart=seatsChart+seatsNum.get(seatsNum.size()-1);

            }

            seatsManageService.updateSeats(seats);
            OrdersEntity orderInitial=orderManageService.getOrder(orderId);
            orderInitial.setTotalprice(realOrderPrice);
            orderInitial.setOrderseats(seatsChart);
            orderManageService.updateOrder(orderInitial);

            show.setRestseats(show.getRestseats()-quantity);
            showManageService.updateShow(show);

            list.add("success");
            list.add(orderId);
            return list;
        }
        else{
            list.add("fail");
            list.add("您选的座位已被别人抢先占用！");
            return list;
        }

    }

    //现场选座购票
    @RequestMapping("/venueRegisterOrder/{orderInfo}")
    @ResponseBody
    public ArrayList<Object> venueRegisterOrderBySeats(@PathVariable("orderInfo")ArrayList<String> orderInfo, HttpServletRequest request, HttpServletResponse response) throws IOException{
        ArrayList<Object> list=new ArrayList<Object>();
        if (orderInfo.size()==0){
            list.add("fail");
            list.add("数据传输失败！");
            return list;
        }
        HttpSession session=request.getSession();
        int venueId=(int)session.getAttribute("venueId");
        int showId=(Integer)session.getAttribute("showId");
        int quantity=Integer.parseInt(orderInfo.get(0));
        double price=Double.parseDouble(orderInfo.get(1));
        Long nowTime=System.currentTimeMillis();
        Long orderDDL=nowTime+TIMELIMIT;
        Timestamp orderTime=new Timestamp(nowTime);
        Timestamp orderDeadline=new Timestamp(orderDDL);
        OrdersEntity order=new OrdersEntity();
        order.setShowid(showId);
//        order.setUseremail(userId);
        order.setQuantity(quantity);
        order.setOriginprice(price);
        order.setTotalprice(price);
        order.setOrdertime(orderTime);
        order.setOrderdeadline(orderDeadline);
        order.setIspayed(0);
        order.setOrderstatus(0);
        order.setHascheck("否");

        ShowsEntity show=showManageService.getShowById(showId);
        order.setShowtime(show.getShowtime());
        order.setVenueid(show.getVenueid());
        order.setIscount(0);

        ArrayList<Integer> seatsNum=new ArrayList<>();
        for(int i=2;i<orderInfo.size();i++){
            seatsNum.add(Integer.parseInt(orderInfo.get(i)));
        }

        boolean isAllAvailable=seatsManageService.isAvailable(showId,seatsNum);

        if(isAllAvailable){
            int orderId=orderManageService.generateOrder(order);

            seatsManageService.lockSeats(showId,seatsNum);
            ArrayList<SeatsEntity> seats=seatsManageService.getSeats(showId,seatsNum);
            double realOrderPrice=0.0;
            for(int i=0;i<seats.size();i++){
                double seatPrice=seats.get(i).getSeatprice();
                realOrderPrice=realOrderPrice+seatPrice;
                seats.get(i).setOrderid(orderId);
            }

            String seatsChart="";
            if(quantity==1){
                seatsChart=String.valueOf(seatsNum.get(0));
            }
            else{
                for(int i=0;i<seatsNum.size()-1;i++){
                    seatsChart=seatsChart+String.valueOf(seatsNum.get(i))+"/";
                }
                seatsChart=seatsChart+seatsNum.get(seatsNum.size()-1);

            }

            seatsManageService.updateSeats(seats);
            OrdersEntity orderInitial=orderManageService.getOrder(orderId);
            orderInitial.setTotalprice(realOrderPrice);
            orderInitial.setOrderseats(seatsChart);
            orderManageService.updateOrder(orderInitial);

            show.setRestseats(show.getRestseats()-quantity);
            showManageService.updateShow(show);

            list.add("success");
            list.add(orderId);
            return list;
        }
        else{
            list.add("fail");
            list.add("您选的座位已被别人抢先占用！");
            return list;
        }

    }


    //用户随机配票
    @RequestMapping("/randomSeatOrder/{seatNum}")
    @ResponseBody
    public ArrayList<Object> randomSeatsOrder(@PathVariable("seatNum") int seatNum,HttpServletRequest request)throws IOException{
        ArrayList<Object> list=new ArrayList<Object>();

        HttpSession session=request.getSession();
        String userId=(String)session.getAttribute("userId");
        int showId=(Integer)session.getAttribute("showId");
        ArrayList<SeatsEntity> seats=seatsManageService.getRandomSeats(showId,seatNum);
        if(seats.size()<seatNum){
            list.add("fail");
            list.add("座位剩余数量不足！");
            return list;
        }

        /**
         * 当座位充足时，先锁住座位
         * */
        ArrayList<Integer> seatsNum=new ArrayList<>();
        for(int i=0;i<seats.size();i++){
            seats.get(i).setSeatisbooked(1);
            seatsNum.add(seats.get(i).getSeatnum());
        }
        System.out.println("座位充足，即将锁住如下座位："+seatsNum.toString());
        seatsManageService.lockSeats(showId,seatsNum);

        /**
         * 获取用户的折扣力度
         * */
        UsersEntity user=usersManageService.getUserInfo(userId);
        int userLevel=user.getLevel();
        double discount= LevelDiscount.discount[userLevel];


        /**
         *将获取的座位realprice改变，获取到订单的原价和会员价，同时生成座位列表
         **/
        double originPrice=0.0;
        double realPrice=0.0;
        String orderSeats="";
        for(int i=0;i<seats.size()-1;i++){
            double seatPrice=seats.get(i).getSeatprice();
            originPrice=originPrice+seatPrice;
            seatPrice=seatPrice*discount;
            realPrice=realPrice+seatPrice;
            seats.get(i).setSeatrealprice(seatPrice);
            orderSeats=orderSeats+seats.get(i).getSeatnum()+"/";
        }
        int j=seats.size()-1;
        double seatPrice=seats.get(j).getSeatprice();
        originPrice=originPrice+seatPrice;
        seatPrice=seatPrice*discount;
        realPrice=realPrice+seatPrice;
        seats.get(j).setSeatrealprice(seatPrice);
        orderSeats=orderSeats+seats.get(j).getSeatnum();

        OrdersEntity order=new OrdersEntity();
        Long nowTime=System.currentTimeMillis();
        Long orderDDL=nowTime+TIMELIMIT;
        Timestamp orderTime=new Timestamp(nowTime);
        Timestamp orderDeadline=new Timestamp(orderDDL);
        order.setTotalprice(realPrice);
        order.setOrderseats(orderSeats);
        order.setOriginprice(originPrice);
        order.setOrderstatus(0);
        order.setIspayed(0);
        order.setOrderdeadline(orderDeadline);
        order.setOrdertime(orderTime);
        order.setQuantity(seatNum);
        order.setUseremail(userId);
        order.setShowid(showId);
        order.setHascheck("否");

        ShowsEntity show=showManageService.getShowById(showId);
        order.setShowtime(show.getShowtime());
        order.setVenueid(show.getVenueid());
        order.setIscount(0);

        int orderId=orderManageService.generateOrder(order);

        for(int i=0;i<seats.size();i++){
            seats.get(i).setOrderid(orderId);
        }

        seatsManageService.updateSeats(seats);

        show.setRestseats(show.getRestseats()-seatNum);
        showManageService.updateShow(show);

        list.add("success");
        list.add(orderId);
        return list;


    }

    //现场随机配票
    @RequestMapping("/venueRandomSeatOrder/{seatNum}")
    @ResponseBody
    public ArrayList<Object> venueRandomSeatOrder(@PathVariable("seatNum") int seatNum,HttpServletRequest request)throws IOException{
        ArrayList<Object> list=new ArrayList<Object>();

        HttpSession session=request.getSession();
        int showId=(Integer)session.getAttribute("showId");
        ArrayList<SeatsEntity> seats=seatsManageService.getRandomSeats(showId,seatNum);
        if(seats.size()<seatNum){
            list.add("fail");
            list.add("座位剩余数量不足！");
            return list;
        }

        /**
         * 当座位充足时，先锁住座位
         * */
        ArrayList<Integer> seatsNum=new ArrayList<>();
        for(int i=0;i<seats.size();i++){
            seats.get(i).setSeatisbooked(1);
            seatsNum.add(seats.get(i).getSeatnum());
        }
        System.out.println("座位充足，即将锁住如下座位："+seatsNum.toString());
        seatsManageService.lockSeats(showId,seatsNum);


        /**
         *将获取的座位realprice改变，获取到订单的原价和会员价，同时生成座位列表
         **/
        double originPrice=0.0;
        double realPrice=0.0;
        String orderSeats="";
        for(int i=0;i<seats.size()-1;i++){
            double seatPrice=seats.get(i).getSeatprice();
            originPrice=originPrice+seatPrice;
            realPrice=realPrice+seatPrice;
            seats.get(i).setSeatrealprice(seatPrice);
            orderSeats=orderSeats+seats.get(i).getSeatnum()+"/";
        }
        int j=seats.size()-1;
        double seatPrice=seats.get(j).getSeatprice();
        originPrice=originPrice+seatPrice;
        realPrice=realPrice+seatPrice;
        seats.get(j).setSeatrealprice(seatPrice);
        orderSeats=orderSeats+seats.get(j).getSeatnum();

        OrdersEntity order=new OrdersEntity();
        Long nowTime=System.currentTimeMillis();
        Long orderDDL=nowTime+TIMELIMIT;
        Timestamp orderTime=new Timestamp(nowTime);
        Timestamp orderDeadline=new Timestamp(orderDDL);
        order.setTotalprice(realPrice);
        order.setOrderseats(orderSeats);
        order.setOriginprice(originPrice);
        order.setOrderstatus(0);
        order.setIspayed(0);
        order.setOrderdeadline(orderDeadline);
        order.setOrdertime(orderTime);
        order.setQuantity(seatNum);
        order.setShowid(showId);
        order.setHascheck("否");

        ShowsEntity show=showManageService.getShowById(showId);
        order.setShowtime(show.getShowtime());
        order.setVenueid(show.getVenueid());
        order.setIscount(0);

        int orderId=orderManageService.generateOrder(order);

        for(int i=0;i<seats.size();i++){
            seats.get(i).setOrderid(orderId);
        }

        seatsManageService.updateSeats(seats);

        show.setRestseats(show.getRestseats()-seatNum);
        showManageService.updateShow(show);

        list.add("success");
        list.add(orderId);
        return list;


    }

    //用户付款的界面
    @RequestMapping("/payOrder/{orderId}")
    public String payOrder(@PathVariable("orderId") int orderId,HttpServletRequest request)throws IOException{
        HttpSession session=request.getSession();
        session.setAttribute("orderId",orderId);
        return "/user/payOrder";
    }
    //现场付款的界面
    @RequestMapping("/venuePayOrder/{orderId}")
    public String venuePayOrder(@PathVariable("orderId") int orderId,HttpServletRequest request)throws IOException{
        HttpSession session=request.getSession();
        session.setAttribute("orderId",orderId);
        return "/venue/venuePayOrder";
    }

    @RequestMapping("/getPayInfo")
    @ResponseBody
    public OrdersEntity getPayInfo(HttpServletRequest request)throws IOException{
        HttpSession session=request.getSession();
        int orderId=(int)session.getAttribute("orderId");
//        double orderPrice=(double)session.getAttribute("orderPrice");
        OrdersEntity order=orderManageService.getOrder(orderId);
        return order;
    }

    //用户购票付款
    @RequestMapping("/pay/{accountInfo}")
    @ResponseBody
    public String payMoney(@PathVariable("accountInfo") ArrayList<String > accountInfo,HttpServletRequest request){
        HttpSession session=request.getSession();
        int orderId=(int)session.getAttribute("orderId");
        OrdersEntity order=orderManageService.getOrder(orderId);
        if(order.getOrderstatus()==1){
            return "overDue";
        }
        if(accountInfo.size()<2){
            return "noInfo";
        }
        String accountId=accountInfo.get(0);
        String password=accountInfo.get(1);
        double coupon=Double.parseDouble(accountInfo.get(2));

        double orderPrice=order.getTotalprice();
        orderPrice=orderPrice-coupon;
        String payRes=accountManageService.consumeAccount(accountId,password,orderPrice);
        if(!Constant.USER_SUCCESS_LOGIN.equals(payRes)){
            return payRes;
        }

        order.setTotalprice(orderPrice);
        order.setIspayed(1);
        order.setOrderstatus(2);
        orderManageService.updateOrder(order);

        accountManageService.addMoney(Constant.ADMIN_ACCOUNT,orderPrice);

        /**
         * 付款成功后
         * 用户的消费金额增加
         * 查询判断并更新用户等级
         * 用户积分增加相应的整数个
         * */
        UsersEntity user=usersManageService.getUserInfo(order.getUseremail());
        double userConsume=user.getUserconsume();
        userConsume=userConsume+orderPrice;
        user.setUserconsume(userConsume);
        int level=LevelDiscount.getLevel(userConsume);
        user.setLevel(level);
        int credit=(int)orderPrice;
        user.setCredit(user.getCredit()+credit);
        usersManageService.updateUser(user);

        /**
         * 如果用了优惠券就要改变优惠券的数据库
         * */
        if (coupon!=0.0){
            couponManageService.reduceCoupon(order.getUseremail(),coupon);
        }

        return "success";
    }

    //现场购票付款
    @RequestMapping("/venuePay/{vipId}")
    @ResponseBody
    public String venuePay(HttpServletRequest request,@PathVariable("vipId") String vipId){
        if (vipId.equals("--")){
            System.out.println("这个现场购票的会员号为空");
        }
        double discount=1.0;
        if(!vipId.equals("--")){
            UsersEntity user=usersManageService.getUserInfo(vipId);
            if(user==null){
                return "noUser";
            }
            else {
                discount=LevelDiscount.discount[user.getLevel()];
            }
        }


        HttpSession session=request.getSession();
        int orderId=(int)session.getAttribute("orderId");
        OrdersEntity order=orderManageService.getOrder(orderId);
        if (order.getOrderstatus()==1){
            return "overDue";
        }

        order.setIspayed(1);
        order.setOrderstatus(4);
        order.setUseremail(vipId);
        order.setOriginprice(order.getTotalprice());
        order.setTotalprice(order.getTotalprice()*discount);
        orderManageService.updateOrder(order);

        ShowsEntity show=showManageService.getShowById(order.getShowid());
        show.setVenueget(show.getVenueget()+order.getTotalprice());
        showManageService.updateShow(show);

//        accountManageService.addMoney("admin",order.getTotalprice());


        return "success";
    }

    @RequestMapping("/getMyOrders")
    @ResponseBody
    public ArrayList<OrdersEntity> getMyOrders(HttpServletRequest request)throws IOException{
        HttpSession session=request.getSession();
        String userEmail=(String) session.getAttribute("userId");
        return orderManageService.getOrdersByUser(userEmail);
    }

    //用户退票
    @RequestMapping("/backOrder/{orderId}")
    public String backOrderView(@PathVariable("orderId") int orderId,HttpServletRequest request)throws IOException{
        OrdersEntity order=orderManageService.getOrder(orderId);
        long orderTime=order.getShowtime().getTime();
        long nowTime=System.currentTimeMillis();
        long min=orderTime-nowTime;
        if(min<=1000*60*60){
            return "/order/backTimeNotPermit";
        }
        HttpSession session=request.getSession();
        session.setAttribute("backOrderId",orderId);
        return "/user/backOrder";
    }

    @RequestMapping("/getBackOrderInfo")
    @ResponseBody
    public ArrayList<Object> getBackPercent(HttpServletRequest request)throws IOException{
        ArrayList<Object> list=new ArrayList<>();
        HttpSession session=request.getSession();
        int orderId=(int)session.getAttribute("backOrderId");
        OrdersEntity order=orderManageService.getOrder(orderId);
        long orderTime=order.getShowtime().getTime();
        long nowTime=System.currentTimeMillis();
        long min=orderTime-nowTime;
        double percent= BackPercent.getReducePercent(min);
        list.add(order);
        list.add(percent);
        return list;
    }

    @RequestMapping("/back/{orderId}")
    @ResponseBody
    public String backOrder(@PathVariable("orderId") int orderId){
        String res=orderManageService.backOrder(orderId);
        return res;
    }

    @RequestMapping("/checkOrder/{orderId}")
    @ResponseBody
    public ArrayList<String> checkOrder(@PathVariable("orderId") int orderId,HttpServletRequest request)throws IOException{
        HttpSession session=request.getSession();
        int venueId=(int)session.getAttribute("venueId");
        return orderManageService.checkOrder(orderId,venueId);
    }

    @RequestMapping("/getUnCountInfo")
    @ResponseBody
    public ArrayList<CountInfo> getUnCountInfo(){
        return orderManageService.getCountInfo();
    }

    @RequestMapping("/countForVenue/{countInfo}")
    @ResponseBody
    public ArrayList<String> countForVenue(@PathVariable("countInfo") ArrayList<String> countInfo){
        int showId=Integer.valueOf(countInfo.get(0));
        double venueGet=Double.parseDouble(countInfo.get(1));
        double adminGet=Double.parseDouble(countInfo.get(2));
        return orderManageService.payForVenue(showId,venueGet,adminGet);
    }

    @RequestMapping("/getVenueShowInfo")
    @ResponseBody
    public ArrayList<VenueShowInfo> getVenueShowInfo(HttpServletRequest request)throws IOException{
        HttpSession session=request.getSession();
        int venueId=(int) session.getAttribute("venueId");
        return orderManageService.getVenueShowByVenue(venueId);
    }

    @RequestMapping("/getVipInfo/{email}")
    @ResponseBody
    public ArrayList<Object> getVipInfo(@PathVariable("email")String email){
        ArrayList<Object> result=new ArrayList<>();
        UsersEntity user=usersManageService.getUserInfo(email);
        if (user==null){
            result.add("fail");
            result.add("不存在该会员账户！");
            return result;
        }
        else {
            double percent=LevelDiscount.discount[user.getLevel()];
            result.add("success");
            result.add(percent);
            return result;
        }
    }

    @RequestMapping("/getOrderShow/{orderId}")
    @ResponseBody
    public ArrayList<Object> getOrderShow(@PathVariable("orderId") int orderId){
        ArrayList<Object> result=new ArrayList<>();
        OrdersEntity order=orderManageService.getOrder(orderId);
        if (order==null){
            result.add("fail");
            result.add("该订单号不存在!");
            return result;
        }
        int showId=order.getShowid();
        ShowsEntity show=showManageService.getShowById(showId);
        result.add("success");
        result.add(order);
        result.add(show);
        return result;
    }
}
