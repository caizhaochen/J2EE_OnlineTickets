package com.ticket.czc.tickets.service.implservice;


import com.ticket.czc.tickets.common.Common;
import com.ticket.czc.tickets.dao.UserDao;
import com.ticket.czc.tickets.dao.ValidateDao;
import com.ticket.czc.tickets.factory.DaoFactory;
import com.ticket.czc.tickets.factory.ServiceFactory;
import com.ticket.czc.tickets.model.UsersEntity;
import com.ticket.czc.tickets.model.ValidatesEntity;
import com.ticket.czc.tickets.service.UsersManageService;
import com.ticket.czc.tickets.service.ValidateService;
import com.ticket.czc.tickets.service.impl.UsersManageServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

@Component
public class EmailCheck {
    private static EmailCheck emailCheck=new EmailCheck();
    public static EmailCheck getInstance(){
        return emailCheck;
    }
//    public EmailCheck(){}


    private UserDao usersDao=DaoFactory.getUserDao();
    //    @Resource
    private ValidateDao validateDao=DaoFactory.getVaidateDao();
    //    @Resource
    private ValidateService validateService= ServiceFactory.getValidateService();
    public static final String FROM = "czc18252896388@163.com";//发件人的email
    public static final String PWD = "czc489622czc";//发件人密码--邮箱密码
    public static final int TIMELIMIT = 1000*60*30; //激活邮件过期时间30min
    public static final String TITLE = "REDBUD共享账簿验证码";
    public static final String HOST = "smtp.163.com";
    public static final String SMTP = "smtp";


    //生成激活链接并发送
    public UsersEntity activateMail(UsersEntity u) throws MessagingException, NoSuchAlgorithmException {
        //注册邮箱
        String to  = u.getEmail();
        //当前时间戳
        Long curTime = System.currentTimeMillis();
        //激活的有效时间
        Long activateTime = curTime+TIMELIMIT;
        //激活码--用于激活邮箱账号
        String token = to+curTime;
//        String token=generateSix();
        u.setToken(md5(token));
        token = u.getToken();
        //过期时间
        u.setActivetime(new Timestamp(activateTime));
        //发送的邮箱内容
        usersDao.updateUser(u);
//        String content = "<p>您好 O(∩_∩)O~~<br><br>欢迎加入REDBUD记账本!<br><br>请在2小时内输入以下验证码注册：<br><br>"
//                +"<br><h1>"+token+"</h1></p>";
        String content = "<p>您好 O(∩_∩)O~~<br><br>欢迎加入REDBUD共享账簿!<br><br>帐户需要激活才能使用，赶紧激活成为我们的正式的一员吧:)<br><br>请在2小时内点击下面的链接立即激活帐户："
                +"<br><a href='"+ Common.URL+"user/activatemail/?token="+token+"&email="+to+"'>"
                +Common.URL+"user/activatemail/?token="+token+"&email="+to+"</a></p>";
        //调用发送邮箱服务
        sendMail(to, TITLE, content);
        return u;
    }

    /**生成激活码并发送，但是此时该账号并未注册，所以用户信息需要存在validates表里，
     * 假设在规定时间内并没有注册，那么在下次重新注册的时候将validates表里该用户的激活码更新
     * 假如在规定时间内激活，这个记录保留，用以留作以后的发送验证码
     **/
    public void sendValidateCode(String userEmail) throws MessagingException,NoSuchAlgorithmException{
        System.out.println("开始产生验证码--"+new Timestamp(System.currentTimeMillis()));
        //当前时间戳
        Long curTime = System.currentTimeMillis();
        //激活的有效时间
        Long activateTime = curTime+TIMELIMIT;

        String code=generateSix();

        ValidatesEntity validate=new ValidatesEntity();
        validate.setUserEmail(userEmail);
        validate.setValidateCode(code);
        validate.setDeadline(new Timestamp(activateTime));
        validateService.addValidateInfo(validate);
        String content = "<p>您好 O(∩_∩)O~~<br><br>欢迎加入REDBUD共享账簿!<br><br>请在30分钟内输入以下验证码注册：<br><br>"
                +"<br><h1>"+code+"</h1></p>";

        System.out.println("开始发送邮件--"+new Timestamp(System.currentTimeMillis()));
        sendMail(userEmail,TITLE,content);
        System.out.println("邮件发送完毕--"+new Timestamp(System.currentTimeMillis()));
    }

    //---------------发送邮件-------------------
    public static void sendMail(String to,String title,String content) throws AddressException,MessagingException {

        Properties props = new Properties(); //可以加载一个配置文件
        // 使用smtp：简单邮件传输协议
        props.put("mail.smtp.host", HOST);//存储发送邮件服务器的信息
        props.put("mail.smtp.auth", "true");//同时通过验证
        Session session = Session.getInstance(props);//根据属性新建一个邮件会话
        //session.setDebug(true); //有他会打印一些调试信息。
        MimeMessage message = new MimeMessage(session);//由邮件会话新建一个消息对象

        //设置自定义发件人昵称
        String sendName="";
        try {
            sendName=javax.mail.internet.MimeUtility.encodeText("REDBUD账本");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        message.setFrom(new InternetAddress(sendName+"<"+FROM+">"));//设置发件人的地址
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));//设置收件人,并设置其接收类型为TO
        message.setSubject(title);//设置标题
        //设置信件内容
        //message.setText(mailContent); //发送 纯文本 邮件 todo
        message.setContent(content, "text/html;charset=gbk"); //发送HTML邮件，内容样式比较丰富
        message.setSentDate(new Date());//设置发信时间
        message.saveChanges();//存储邮件信息
        //发送邮件
        Transport transport = session.getTransport(SMTP);
        //Transport transport = session.getTransport();
        transport.connect(FROM, PWD);
        transport.sendMessage(message, message.getAllRecipients());//发送邮件,其中第二个参数是所有已设好的收件人地址
        transport.close();
    }

    private static String generateSix(){
        String res="";
        Random random0=new Random();
        Random random1=new Random();
        Random random2=new Random();
        Random random3=new Random();
        Random random4=new Random();
        Random random5=new Random();

        String res0=random0.nextInt(10)+"";
        String res1=random1.nextInt(10)+"";
        String res2=random2.nextInt(10)+"";
        String res3=random3.nextInt(10)+"";
        String res4=random4.nextInt(10)+"";
        String res5=random5.nextInt(10)+"";

        res=res0+res1+res2+res3+res4+res5;

        return res;
    }


    public static String md5(String string) {
        if (string.isEmpty()) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes("UTF-8"));
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

}
