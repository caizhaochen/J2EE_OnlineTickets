package com.ticket.czc.tickets.utils;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        try {
            Configuration config;
//				ServiceRegistry serviceRegistry;
//				config = new Configuration().configure();
//				config.addAnnotatedClass(UsersEntity.class);
//				config.addAnnotatedClass(OrdersEntity.class);
////				config.addAnnotatedClass(SeatspriceEntity.class);
//				config.addAnnotatedClass(VenuesEntity.class);
//				config.addAnnotatedClass(ShowsEntity.class);
//				serviceRegistry =new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
//				sessionFactory=config.buildSessionFactory(serviceRegistry);
            config = new Configuration();

            sessionFactory = config.configure().buildSessionFactory();
            return sessionFactory;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * gerCurrentSession ���Զ��ر�session��ʹ�õ��ǵ�ǰ��session���� * * @return
     */
    public static Session getSession() {
//		 return getSessionFactory().openSession();
        return getSessionFactory().getCurrentSession();
    }
}
