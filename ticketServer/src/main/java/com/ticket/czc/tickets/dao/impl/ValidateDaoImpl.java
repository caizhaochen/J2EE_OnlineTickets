package com.ticket.czc.tickets.dao.impl;


import com.ticket.czc.tickets.dao.ValidateDao;
import com.ticket.czc.tickets.model.ValidatesEntity;
import com.ticket.czc.tickets.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

//@Repository
@Component
public class ValidateDaoImpl extends BaseDaoImpl implements ValidateDao {
    private static ValidateDaoImpl validateDao=new ValidateDaoImpl();

    public static ValidateDaoImpl getInstance(){
        return  validateDao;
    }
    @Override
    public void addValidate(ValidatesEntity validatesEntity) {
        super.save(validatesEntity);
    }

    @Override
    public ValidatesEntity getValidate(String userEmail) {
        ArrayList<ValidatesEntity> list=new ArrayList<>();
        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();

            String hql = "from com.ticket.czc.tickets.model.ValidatesEntity v where v.userEmail= :u";
            Query query = session.createQuery(hql);
            query.setString("u",userEmail);
            list =(ArrayList<ValidatesEntity>) query.list();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

        if (list==null||list.size()==0){
            return null;
        }

        return list.get(0);
    }

    @Override
    public void updateValidate(ValidatesEntity validatesEntity) {
        super.update(validatesEntity);
    }
}
