package com.ticket.czc.tickets.service.impl;

import com.ticket.czc.tickets.common.Constant;
import com.ticket.czc.tickets.dao.AccountDao;
import com.ticket.czc.tickets.factory.DaoFactory;
import com.ticket.czc.tickets.model.AccountsEntity;
import com.ticket.czc.tickets.service.AccountManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountManageServiceImpl implements AccountManageService {
    private static AccountManageServiceImpl accountManageService=new AccountManageServiceImpl();

    public static AccountManageServiceImpl getInstance() {
        return accountManageService;
    }

    private AccountDao accountDao= DaoFactory.getAccountDao();

//    @Autowired
//    private AccountDao accountDao;
    @Override
    public String validataAccount(String id, String password) {
//        AccountsEntity account=accountDao.getAccount(id);
//        if(account==null){
//            return Constant.USER_NOT_EXITS;
//        }
//
//        if(!password.equals(account.getAccountpass())){
//            return Constant.USER_ERROR_PASSWORD;
//        }
//
//        return Constant.USER_SUCCESS_LOGIN;
        return null;
    }

    @Override
    public void registerAccount(String id, String password) {
        AccountsEntity accountsEntity=new AccountsEntity();
        accountsEntity.setAccountid(id);
        accountsEntity.setAccountpass(password);
        accountsEntity.setAccountmoney(100000.0);
        accountDao.save(accountsEntity);
    }

    @Override
    public String consumeAccount(String id, String password, Double money) {
        AccountsEntity account=accountDao.getAccount(id);
        if(account==null){
            return Constant.USER_NOT_EXITS;
        }

        if(!password.equals(account.getAccountpass())){
            return Constant.USER_ERROR_PASSWORD;
        }

        double nowMoney=account.getAccountmoney();
        nowMoney=nowMoney-money;
        if(nowMoney<0.0){
            return "notEnough";
        }

        account.setAccountmoney(nowMoney);
        accountDao.updateAccount(account);

        return Constant.USER_SUCCESS_LOGIN;
    }

    @Override
    public AccountsEntity getAccount(String id) {
        AccountsEntity account=accountDao.getAccount(id);
        return account;
    }

    @Override
    public void addMoney(String id, double money) {
        accountDao.addMoeny(id,money);
    }
}
