package com.ticket.czc.tickets.dao;

import com.ticket.czc.tickets.model.AccountsEntity;

public interface AccountDao {

    public void save(AccountsEntity accountsEntity);

    public AccountsEntity getAccount(String id);

    public void updateAccount(AccountsEntity accountsEntity);

    public void addMoeny(String id,double money);
}
