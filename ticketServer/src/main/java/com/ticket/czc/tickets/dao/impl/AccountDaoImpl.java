package com.ticket.czc.tickets.dao.impl;

import com.ticket.czc.tickets.dao.AccountDao;
import com.ticket.czc.tickets.model.AccountsEntity;
import org.springframework.stereotype.Repository;

//@Repository
public class AccountDaoImpl extends BaseDaoImpl implements AccountDao {
    private static AccountDaoImpl accountDao=new AccountDaoImpl();
    public static AccountDaoImpl getInstance(){
        return accountDao;
    }

    @Override
    public void save(AccountsEntity accountsEntity) {
        super.save(accountsEntity);
    }

    @Override
    public AccountsEntity getAccount(String id) {
        AccountsEntity account=(AccountsEntity)super.load(AccountsEntity.class,id);
        return account;
    }

    @Override
    public void updateAccount(AccountsEntity accountsEntity) {
        super.update(accountsEntity);
    }

    @Override
    public void addMoeny(String id, double money) {
        AccountsEntity account=(AccountsEntity)super.load(AccountsEntity.class,id);
        account.setAccountmoney(account.getAccountmoney()+money);
        super.update(account);
    }
}
