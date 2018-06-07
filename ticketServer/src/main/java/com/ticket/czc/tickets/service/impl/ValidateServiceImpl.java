package com.ticket.czc.tickets.service.impl;


import com.ticket.czc.tickets.dao.ValidateDao;
import com.ticket.czc.tickets.factory.DaoFactory;
import com.ticket.czc.tickets.model.UsersEntity;
import com.ticket.czc.tickets.model.ValidatesEntity;
import com.ticket.czc.tickets.service.UsersManageService;
import com.ticket.czc.tickets.service.ValidateService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;

@Component
public class ValidateServiceImpl implements ValidateService {
    private static ValidateServiceImpl validateService=new ValidateServiceImpl();
    public static ValidateServiceImpl getInstance(){
        return validateService;
    }
//    @Resource
    private ValidateDao validateDao= DaoFactory.getVaidateDao();

    @Override
    public ValidatesEntity getValidate(String userEmail) {
        return validateDao.getValidate(userEmail);
    }

    @Override
    public void addValidateInfo(ValidatesEntity validate) {
        String email=validate.getUserEmail();
        ValidatesEntity validateTemp=validateDao.getValidate(email);
        if (validateTemp==null){
            validateDao.addValidate(validate);
            return;
        }

        validateTemp.setValidateCode(validate.getValidateCode());
        validateTemp.setDeadline(validate.getDeadline());
        validateDao.updateValidate(validateTemp);
    }

    @Override
    public ArrayList<String> checkValidateCode(UsersEntity user) {
        ArrayList<String> result=new ArrayList<>();

        String userEmail=user.getEmail();
        ValidatesEntity validate=validateDao.getValidate(userEmail);
        if (validate==null){
            result.add("fail");
            result.add("验证码错误，请重新获取验证码！(Please get code again!)");
            return result;
        }

        if (validate.getDeadline().before(new Timestamp(System.currentTimeMillis()))){
            result.add("fail");
            result.add("超出时间，请重新发送验证码！(Time is over limited time,please get code again)");
            return result;
        }

        if (!validate.getValidateCode().equals(user.getToken())){
            result.add("fail");
            result.add("验证码错误！(ERROR Code!)");
            return result;
        }

        result.add("success");
        return result;
    }
}
