package com.ticket.czc.tickets.service;

import com.ticket.czc.tickets.model.AccountsEntity;

public interface AccountManageService {

    public String validataAccount(String id,String password);

    public void registerAccount(String id,String password);

    public String consumeAccount(String id,String password,Double money);

    public AccountsEntity getAccount(String id);

    public void addMoney(String id,double money);
}
