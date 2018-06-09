package com.ticket.czc.tickets.service.impl;

import com.ticket.czc.tickets.common.Constant;
import com.ticket.czc.tickets.common.LevelDiscount;
import com.ticket.czc.tickets.dao.UserDao;
import com.ticket.czc.tickets.factory.DaoFactory;
import com.ticket.czc.tickets.factory.ServiceFactory;
import com.ticket.czc.tickets.model.AccountsEntity;
import com.ticket.czc.tickets.model.CouponsEntity;
import com.ticket.czc.tickets.model.NumStatistics;
import com.ticket.czc.tickets.model.UsersEntity;
import com.ticket.czc.tickets.service.AccountManageService;
import com.ticket.czc.tickets.service.CouponManageService;
import com.ticket.czc.tickets.service.UsersManageService;
import com.ticket.czc.tickets.service.ValidateService;
import com.ticket.czc.tickets.service.implservice.EmailCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

@Service
public class UsersManageServiceImpl implements UsersManageService {
    private static UsersManageServiceImpl usersManageService=new UsersManageServiceImpl();
    public static UsersManageService getInstance(){
        return usersManageService;
    }

    private UserDao userDao= DaoFactory.getUserDao();
//    @Autowired
//    private UserDao userDao;
    private CouponManageService couponManageService= ServiceFactory.getCouponManageService();
    private AccountManageService accountManageService=ServiceFactory.getAccountManageService();

//    @Resource
    private ValidateService validateService=ServiceFactory.getValidateService();
    @Override
    public String validateUser(String email, String password) {
        UsersEntity usersEntity=userDao.findUser(email);
        if(usersEntity==null){
            return Constant.USER_NOT_EXITS;
        }

        if(usersEntity.getIscheck()!=1){
            return Constant.USER_NOT_CHECK;
        }

        if(usersEntity.getEnableuse()!=1){
            return Constant.USER_NOT_PERMIT;
        }

        if(!usersEntity.getUserpassword().equals(password)){
            return Constant.USER_ERROR_PASSWORD;
        }



        return Constant.USER_SUCCESS_LOGIN;
    }

    @Override
    public ArrayList<String> registerUser(UsersEntity usersEntity) {
        ArrayList<String> result=new ArrayList<>();

        if(usersEntity==null){
            result.add("fail");
            result.add("网络错误！(Network Error!)");
            return result;
        }else{
            //确认是第一次注册
            UsersEntity usersEntity2=userDao.findUser(usersEntity.getEmail());
            if(usersEntity2!=null){
                result.add("fail");
                result.add("该邮箱已被注册！");
                return result;
            }else{
                //是第一次注册，然后先确认验证码是否正确或者是否过期
                ArrayList<String> checkResult=validateService.checkValidateCode(usersEntity);
                if (!checkResult.get(0).equals("success")){
                    return checkResult;
                }


                UsersEntity usersEntity1=userDao.saveUser(usersEntity);
                if(usersEntity1==null){
                    result.add("fail");
                    result.add("网络错误！(Network Error!)");
                    return result;
                }

                accountManageService.registerAccount(usersEntity.getEmail(),usersEntity.getUserpassword());
                result.add("success");
                return result;

            }

        }

    }

    @Override
    public UsersEntity getUserInfo(String email) {
        System.out.println(email);
        UsersEntity usersEntity=userDao.findUser(email);
        return usersEntity;
    }

    @Override
    public UsersEntity updateUser(UsersEntity usersEntity) {
        UsersEntity usersEntity1=userDao.updateUser(usersEntity);
        return usersEntity1;
    }

    @Override
    public ArrayList<String> addCoupon(String email, double price) {
        ArrayList<String> res=new ArrayList<>();
        UsersEntity user=userDao.findUser(email);
        int needCredit=(int)price*12;
        if(user.getCredit()<needCredit){
            res.add("fail");
            res.add("积分不足，不可以兑换！（剩余积分："+user.getCredit()+"分）");
            return res;
        }
        couponManageService.addCoupon(email,price);
        user.setCredit(user.getCredit()-needCredit);
        userDao.updateUser(user);
        res.add("success");
        return res;

    }

    @Override
    public ArrayList<UsersEntity> getActiveUser() {
        return userDao.getCheckedUsers();
    }

    @Override
    public ArrayList<UsersEntity> getDeleteUser() {
        return userDao.getDeleteUsers();
    }

    @Override
    public ArrayList<UsersEntity> getUnCheckUser() {
        return userDao.getUncheckUsers();
    }

    @Override
    public ArrayList<NumStatistics> getUserNum() {
        ArrayList<NumStatistics> numStatistics=new ArrayList<>();
        ArrayList<UsersEntity> checkedUsers=new ArrayList<>();
        ArrayList<UsersEntity> unCheckedUsers=new ArrayList<>();
        ArrayList<UsersEntity> deleteUsers=new ArrayList<>();
        checkedUsers=userDao.getCheckedUsers();
        unCheckedUsers=userDao.getUncheckUsers();
        deleteUsers=userDao.getDeleteUsers();
        int checkNum,unCheckNum,deleteNum=0;
        if(checkedUsers==null){
            checkNum=0;
        }else {
            checkNum=checkedUsers.size();
        }
        if(unCheckedUsers==null){
            unCheckNum=0;
        }else {
            unCheckNum=unCheckedUsers.size();
        }
        if(deleteUsers==null){
            deleteNum=0;
        }else {
            deleteNum=deleteUsers.size();
        }
        NumStatistics checkNS=new NumStatistics();
        NumStatistics unCheckNS=new NumStatistics();
        NumStatistics deleteNS=new NumStatistics();

        checkNS.setName("活跃用户");
        unCheckNS.setName("未激活用户");
        deleteNS.setName("注销用户");
        checkNS.setNum(checkNum);
        unCheckNS.setNum(unCheckNum);
        deleteNS.setNum(deleteNum);

        numStatistics.add(checkNS);
        numStatistics.add(unCheckNS);
        numStatistics.add(deleteNS);
        return numStatistics;
    }

    @Override
    public ArrayList<NumStatistics> getLevelNum() {
        ArrayList<NumStatistics> levelNum=new ArrayList<>();
        ArrayList<UsersEntity> users=userDao.getAllUsers();
        for(int i=0;i< LevelDiscount.discount.length;i++){
            NumStatistics num=new NumStatistics();
            String name="等级"+i;
            int number=0;
            for(int j=0;j<users.size();j++){
                if(users.get(j).getLevel()==i){
                    number++;
                }
            }
            num.setName(name);
            num.setNum(number);
            levelNum.add(num);
        }
        return levelNum;
    }

    public static void main(String[] args){
//        UsersEntity usersEntity=new UsersEntity();
//        usersEntity.setEmail("731744067@qq.com");
//        usersEntity.setUsername("MR CAI");
//        usersEntity.setUserbirth(Date.valueOf("1997-02-20"));
//        usersEntity.setUserpassword("czc489622czc");
//        usersEntity.setUsersex("男");
//        usersEntity.setLevel(0);
//        usersEntity.setIscheck(0);
        UsersManageServiceImpl usersManageService=new UsersManageServiceImpl();
//        String res=usersManageService.registerUser(usersEntity);
//        System.out.println(res);

        String res=usersManageService.validateUser("731744067@qq.com","czc489622czc");
        System.out.println(res);
    }
}
