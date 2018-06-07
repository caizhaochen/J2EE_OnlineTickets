package com.ticket.czc.tickets.dao.impl;

import com.ticket.czc.tickets.dao.UserDao;
import com.ticket.czc.tickets.model.UsersEntity;
import com.ticket.czc.tickets.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.ArrayList;

@Repository
public class UserDaoImpl extends BaseDaoImpl implements UserDao {
    private static UserDaoImpl userDao=new UserDaoImpl();

//    public UserDaoImpl(){
//
//    }

    public static UserDaoImpl getInstance(){
        return  userDao;
    }
    @Override
    public UsersEntity findUser(String email) {
        ArrayList<UsersEntity> list=new ArrayList<>();
        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();

            String hql = "from com.ticket.czc.tickets.model.UsersEntity u where u.email= :p";
            Query query = session.createQuery(hql);
            query.setString("p",email);
            list =(ArrayList<UsersEntity>) query.list();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (list==null||list.size()==0){
            return null;
        }
        else {
            return list.get(0);
        }
    }

    @Override
    public UsersEntity saveUser(UsersEntity user) {
        super.save(user);
        UsersEntity usersEntity=(new UserDaoImpl()).findUser(user.getEmail());
        return usersEntity;
    }

    @Override
    public UsersEntity updateUser(UsersEntity user) {
        super.update(user);
        UsersEntity usersEntity=(new UserDaoImpl()).findUser(user.getEmail());
        return usersEntity;
    }

    @Override
    public ArrayList<UsersEntity> getAllUsers() {
        ArrayList<UsersEntity> list=new ArrayList<>();
        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();

            String hql = "from com.ticket.czc.tickets.model.UsersEntity";
            Query query = session.createQuery(hql);
            list =(ArrayList<UsersEntity>) query.list();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<UsersEntity> getCheckedUsers() {
        ArrayList<UsersEntity> list=new ArrayList<>();
        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();

            String hql = "from com.ticket.czc.tickets.model.UsersEntity u where u.ischeck=1 and u.enableuse=1";
            Query query = session.createQuery(hql);
            list =(ArrayList<UsersEntity>) query.list();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<UsersEntity> getDeleteUsers() {
        ArrayList<UsersEntity> list=new ArrayList<>();
        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();

            String hql = "from com.ticket.czc.tickets.model.UsersEntity u where u.enableuse=0";
            Query query = session.createQuery(hql);
            list =(ArrayList<UsersEntity>) query.list();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<UsersEntity> getUncheckUsers() {
        ArrayList<UsersEntity> list=new ArrayList<>();
        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();

            String hql = "from com.ticket.czc.tickets.model.UsersEntity u where u.ischeck=0";
            Query query = session.createQuery(hql);
            list =(ArrayList<UsersEntity>) query.list();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args){
        UsersEntity usersEntity=new UsersEntity();
        usersEntity.setEmail("731744069@qq.com");
        usersEntity.setUsername("MR CAI");
        usersEntity.setUserbirth(Date.valueOf("1997-02-20"));
        usersEntity.setUserpassword("czc489622czc");
        usersEntity.setUsersex("男");
        usersEntity.setLevel(0);
        usersEntity.setIscheck(0);
        usersEntity.setToken("999999f9f9f9f9asdsda");
        UserDaoImpl userDao=new UserDaoImpl();
        userDao.updateUser(usersEntity);
    }
}
