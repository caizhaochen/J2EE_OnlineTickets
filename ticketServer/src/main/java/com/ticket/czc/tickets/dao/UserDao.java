package com.ticket.czc.tickets.dao;

import com.ticket.czc.tickets.model.UsersEntity;

import java.util.ArrayList;

public interface UserDao {

    public UsersEntity findUser(String email);

    public UsersEntity updateUser(UsersEntity user);

    public UsersEntity saveUser(UsersEntity user);
    public ArrayList<UsersEntity> getAllUsers();
    public ArrayList<UsersEntity> getCheckedUsers();
    public ArrayList<UsersEntity> getUncheckUsers();
    public ArrayList<UsersEntity> getDeleteUsers();
}
