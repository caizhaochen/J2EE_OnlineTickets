package com.ticket.czc.tickets.dao;


import com.ticket.czc.tickets.model.ValidatesEntity;

public interface ValidateDao {

    public void addValidate(ValidatesEntity validatesEntity);

    public ValidatesEntity getValidate(String userEmail);

    public void updateValidate(ValidatesEntity validatesEntity);
}
