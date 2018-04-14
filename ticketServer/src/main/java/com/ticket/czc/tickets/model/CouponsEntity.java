package com.ticket.czc.tickets.model;

import javax.persistence.*;

@Entity
@Table(name = "coupons", schema = "tickets", catalog = "")
public class CouponsEntity {
    private int couponid;
    private String userid;
    private Double price;
    private Integer couponquantity;

    @Id
    @Column(name = "couponid")
    public int getCouponid() {
        return couponid;
    }

    public void setCouponid(int couponid) {
        this.couponid = couponid;
    }

    @Basic
    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "couponquantity")
    public Integer getCouponquantity() {
        return couponquantity;
    }

    public void setCouponquantity(Integer couponquantity) {
        this.couponquantity = couponquantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CouponsEntity that = (CouponsEntity) o;

        if (couponid != that.couponid) return false;
        if (userid != null ? !userid.equals(that.userid) : that.userid != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (couponquantity != null ? !couponquantity.equals(that.couponquantity) : that.couponquantity != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = couponid;
        result = 31 * result + (userid != null ? userid.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (couponquantity != null ? couponquantity.hashCode() : 0);
        return result;
    }
}
