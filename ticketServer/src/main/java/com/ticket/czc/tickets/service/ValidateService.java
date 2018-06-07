package com.ticket.czc.tickets.service;



import com.ticket.czc.tickets.model.UsersEntity;
import com.ticket.czc.tickets.model.ValidatesEntity;

import java.util.ArrayList;

public interface ValidateService {

    /**
     * 生成验证码（Generate ValidateCode）
     * @param validate
     */
    public void addValidateInfo(ValidatesEntity validate);

    /**
     * 获取验证码的相关信息（Get information  about the VALIDATE）
     * @param userEmail ---get information via userEmail
     * @return ---return the user's validateInfo
     */
    public ValidatesEntity getValidate(String userEmail);

    /**
     * 检查验证码是否在正确和过时 (Check the code is correct or not and the time is whether beyond de deadline time)
     * @param user --this UsersEntity include user's email and user's code
     * @return <result,description></result,description> result--success/fail description--code error/over time
     */
    public ArrayList<String> checkValidateCode(UsersEntity user);
}
