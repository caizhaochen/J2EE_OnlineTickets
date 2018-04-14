package com.ticket.czc.tickets.model;

import javax.persistence.*;

@Entity
@Table(name = "accounts", schema = "tickets", catalog = "")
public class AccountsEntity {
    private String accountid;
    private String accountpass;
    private Double accountmoney;

    @Id
    @Column(name = "accountid")
    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    @Basic
    @Column(name = "accountpass")
    public String getAccountpass() {
        return accountpass;
    }

    public void setAccountpass(String accountpass) {
        this.accountpass = accountpass;
    }

    @Basic
    @Column(name = "accountmoney")
    public Double getAccountmoney() {
        return accountmoney;
    }

    public void setAccountmoney(Double accountmoney) {
        this.accountmoney = accountmoney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountsEntity that = (AccountsEntity) o;

        if (accountid != null ? !accountid.equals(that.accountid) : that.accountid != null) return false;
        if (accountpass != null ? !accountpass.equals(that.accountpass) : that.accountpass != null) return false;
        if (accountmoney != null ? !accountmoney.equals(that.accountmoney) : that.accountmoney != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accountid != null ? accountid.hashCode() : 0;
        result = 31 * result + (accountpass != null ? accountpass.hashCode() : 0);
        result = 31 * result + (accountmoney != null ? accountmoney.hashCode() : 0);
        return result;
    }
}
