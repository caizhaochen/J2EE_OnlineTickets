package com.ticket.czc.tickets.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "users", schema = "tickets", catalog = "")
public class UsersEntity {
    private String email;
    private String username;
    private Date userbirth;
    private String usersex;
    private String userpassword;
    private Integer level;
    private Integer ischeck;
    private String token;
    private Timestamp activetime;
    private Integer enableuse;
    private Double userconsume;
    private Integer credit;

    @Id
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "userbirth")
    public Date getUserbirth() {
        return userbirth;
    }

    public void setUserbirth(Date userbirth) {
        this.userbirth = userbirth;
    }

    @Basic
    @Column(name = "usersex")
    public String getUsersex() {
        return usersex;
    }

    public void setUsersex(String usersex) {
        this.usersex = usersex;
    }

    @Basic
    @Column(name = "userpassword")
    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    @Basic
    @Column(name = "level")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Basic
    @Column(name = "ischeck")
    public Integer getIscheck() {
        return ischeck;
    }

    public void setIscheck(Integer ischeck) {
        this.ischeck = ischeck;
    }

    @Basic
    @Column(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "activetime")
    public Timestamp getActivetime() {
        return activetime;
    }

    public void setActivetime(Timestamp activetime) {
        this.activetime = activetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (userbirth != null ? !userbirth.equals(that.userbirth) : that.userbirth != null) return false;
        if (usersex != null ? !usersex.equals(that.usersex) : that.usersex != null) return false;
        if (userpassword != null ? !userpassword.equals(that.userpassword) : that.userpassword != null) return false;
        if (level != null ? !level.equals(that.level) : that.level != null) return false;
        if (ischeck != null ? !ischeck.equals(that.ischeck) : that.ischeck != null) return false;
        if (token != null ? !token.equals(that.token) : that.token != null) return false;
        if (activetime != null ? !activetime.equals(that.activetime) : that.activetime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (userbirth != null ? userbirth.hashCode() : 0);
        result = 31 * result + (usersex != null ? usersex.hashCode() : 0);
        result = 31 * result + (userpassword != null ? userpassword.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (ischeck != null ? ischeck.hashCode() : 0);
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (activetime != null ? activetime.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "enableuse")
    public Integer getEnableuse() {
        return enableuse;
    }

    public void setEnableuse(Integer enableuse) {
        this.enableuse = enableuse;
    }

    @Basic
    @Column(name = "userconsume")
    public Double getUserconsume() {
        return userconsume;
    }

    public void setUserconsume(Double userconsume) {
        this.userconsume = userconsume;
    }

    @Basic
    @Column(name = "credit")
    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }
}
