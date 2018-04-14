package com.ticket.czc.tickets.service;

import com.ticket.czc.tickets.model.NumStatistics;
import com.ticket.czc.tickets.model.UsersEntity;

import java.util.ArrayList;

public interface UsersManageService {
    public String validateUser(String email,String password);

    public String registerUser(UsersEntity usersEntity);

    public UsersEntity getUserInfo(String email);

    public UsersEntity updateUser(UsersEntity usersEntity);

    public ArrayList<String> addCoupon(String email,double price);

    public ArrayList<UsersEntity> getActiveUser();
    public ArrayList<UsersEntity> getUnCheckUser();
    public ArrayList<UsersEntity> getDeleteUser();

    public ArrayList<NumStatistics> getUserNum();

    public ArrayList<NumStatistics> getLevelNum();
}
