package com.ticket.czc.tickets.controller.users;

import com.ticket.czc.tickets.common.Constant;
import com.ticket.czc.tickets.factory.ServiceFactory;
import com.ticket.czc.tickets.model.CouponsEntity;
import com.ticket.czc.tickets.model.UsersEntity;
import com.ticket.czc.tickets.service.AccountManageService;
import com.ticket.czc.tickets.service.CouponManageService;
import com.ticket.czc.tickets.service.UsersManageService;
import com.ticket.czc.tickets.service.implservice.EmailCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

@RestController
@RequestMapping("/user")
public class UsersController {

    private EmailCheck emailCheck=ServiceFactory.getEmailCheckService();
    private UsersManageService usersManageService = ServiceFactory.getUserManageService();
    private AccountManageService accountManageService=ServiceFactory.getAccountManageService();
    private CouponManageService couponManageService=ServiceFactory.getCouponManageService();


//    @Autowired
//    private UsersManageService usersManageService;
//    @Autowired
//    private AccountManageService accountManageService;
    /**
     * 获取验证码（click the button to get verify code）
     * @param userEmail --just need the userEmail
     */
    @RequestMapping("/getCheckCode/{userEmail}")
    public String getCheckCode(@PathVariable("userEmail")String userEmail){
        try {
            emailCheck.sendValidateCode(userEmail);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    /***
     *用户注册 （Register user）
     * @param userInfo--
     *                0-userEmail    ,
     *                1-userName     ,
     *                2-userToken    ,
     *                3-userPass     ,
     *
     * @return <state:result></state:result>
     */
    @RequestMapping("/register/{userInfo}")
    public ArrayList<String> registerUser(@PathVariable("userInfo")ArrayList<String> userInfo){
        ArrayList<String> resArray=new ArrayList<>();
        if (userInfo==null){
            resArray.add("fail");
            resArray.add("网络错误！请重试。");
            return resArray;
        }

        UsersEntity user=new UsersEntity();
        user.setEmail(userInfo.get(0));
        user.setUsername(userInfo.get(1));
        user.setToken(userInfo.get(2));
        user.setUserpassword(userInfo.get(3));
        user.setUserbirth(new Date(System.currentTimeMillis()));
        user.setUsersex("男");
        user.setIscheck(1);
        user.setEnableuse(1);
        user.setLevel(0);
        ArrayList<String> res=usersManageService.registerUser(user);
        return res;
    }

    @RequestMapping("/login/{loginInfo}")
    public String login(@PathVariable("loginInfo") ArrayList<String> loginInfo, HttpServletRequest request) throws IOException {
        String email = loginInfo.get(0);
        String password = loginInfo.get(1);

        String loginRes = usersManageService.validateUser(email, password);
        if (loginRes .equals( Constant.USER_SUCCESS_LOGIN)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("userId", email);
        }

        return loginRes;
    }

    @RequestMapping("/modifyInfo/{userInfo}")
    public String modifyInfo(@PathVariable("userInfo") ArrayList<String> userInfo) {
        UsersEntity user = new UsersEntity();
        user = usersManageService.getUserInfo(userInfo.get(0));
        user.setUsername(userInfo.get(1));
        user.setUserbirth(Date.valueOf(userInfo.get(2)));
        user.setUsersex(userInfo.get(3));
        UsersEntity usersEntity = usersManageService.updateUser(user);
        if (usersEntity == null) {
            return "fail";
        } else {
            return "success";
        }
    }

    @RequestMapping("/modifyPass/{userInfo}")
    public String modifyPass(@PathVariable("userInfo") ArrayList<String> userInfo) {
        UsersEntity user = new UsersEntity();
        user = usersManageService.getUserInfo(userInfo.get(0));
        user.setUserpassword(userInfo.get(1));
        UsersEntity usersEntity = usersManageService.updateUser(user);
        if (usersEntity == null) {
            return "fail";
        } else {
            return "success";
        }
    }

    @RequestMapping("deleteUser")
    public String deleteUser(HttpServletRequest request)throws IOException{
        HttpSession session=request.getSession(false);
        String email=(String)session.getAttribute("userId");
        UsersEntity user=usersManageService.getUserInfo(email);
        user.setEnableuse(0);
        UsersEntity usersEntity=usersManageService.updateUser(user);
        if(usersEntity.getEnableuse()==0){
            return "success";
        }
        else{
            return "fail";
        }
    }

    @RequestMapping("/getCoupon/{price}")
//    @ResponseBody
    public ArrayList<String> getCoupon(@PathVariable("price") double price,HttpServletRequest request)throws IOException{
        HttpSession session=request.getSession();
        String email=(String)session.getAttribute("userId");
        return usersManageService.addCoupon(email,price);
    }

    @RequestMapping("/getMyCoupon")
    public ArrayList<CouponsEntity> getMyCoupons(HttpServletRequest request)throws IOException{
        HttpSession session=request.getSession();
        String email=(String)session.getAttribute("userId");
        return couponManageService.getAvailableCoupons(email);
    }




    public static void main(String[] args) {
        System.out.println(Date.valueOf("1997-02-10"));
    }

}
