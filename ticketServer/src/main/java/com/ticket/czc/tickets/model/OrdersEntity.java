package com.ticket.czc.tickets.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity

@Table(name = "orders", schema = "tickets", catalog = "")
public class OrdersEntity {
    private int orderid;
    private String useremail;
    private int showid;
    private Integer quantity;
    private Double totalprice;
    private Timestamp ordertime;
    private Integer ispayed;
    private Timestamp orderdeadline;
    private Integer orderstatus;
    private Double originprice;
    private String orderseats;
    private Double beforebackprice;
    private String hascheck;
    private Timestamp showtime;
    private Integer venueid;
    private Integer iscount;

    /**
     * orderstutus:0：未付款，未超时
     *             1：未付款，已超时
     *             2：已付款
     *             3：已退订
     **/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "orderid")
    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    @Basic
    @Column(name = "useremail")
    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    @Basic
    @Column(name = "showid")
    public int getShowid() {
        return showid;
    }

    public void setShowid(int showid) {
        this.showid = showid;
    }

    @Basic
    @Column(name = "quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "totalprice")
    public Double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Double totalprice) {
        this.totalprice = totalprice;
    }

    @Basic
    @Column(name = "ordertime")
    public Timestamp getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Timestamp ordertime) {
        this.ordertime = ordertime;
    }

    @Basic
    @Column(name = "ispayed")
    public Integer getIspayed() {
        return ispayed;
    }

    public void setIspayed(Integer ispayed) {
        this.ispayed = ispayed;
    }

    @Basic
    @Column(name = "orderdeadline")
    public Timestamp getOrderdeadline() {
        return orderdeadline;
    }

    public void setOrderdeadline(Timestamp orderdeadline) {
        this.orderdeadline = orderdeadline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrdersEntity that = (OrdersEntity) o;

        if (orderid != that.orderid) return false;
        if (showid != that.showid) return false;
        if (useremail != null ? !useremail.equals(that.useremail) : that.useremail != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (totalprice != null ? !totalprice.equals(that.totalprice) : that.totalprice != null) return false;
        if (ordertime != null ? !ordertime.equals(that.ordertime) : that.ordertime != null) return false;
        if (ispayed != null ? !ispayed.equals(that.ispayed) : that.ispayed != null) return false;
        if (orderdeadline != null ? !orderdeadline.equals(that.orderdeadline) : that.orderdeadline != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderid;
        result = 31 * result + (useremail != null ? useremail.hashCode() : 0);
        result = 31 * result + showid;
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (totalprice != null ? totalprice.hashCode() : 0);
        result = 31 * result + (ordertime != null ? ordertime.hashCode() : 0);
        result = 31 * result + (ispayed != null ? ispayed.hashCode() : 0);
        result = 31 * result + (orderdeadline != null ? orderdeadline.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "orderstatus")
    public Integer getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(Integer orderstatus) {
        this.orderstatus = orderstatus;
    }

    @Basic
    @Column(name = "originprice")
    public Double getOriginprice() {
        return originprice;
    }

    public void setOriginprice(Double originprice) {
        this.originprice = originprice;
    }

    @Basic
    @Column(name = "orderseats")
    public String getOrderseats() {
        return orderseats;
    }

    public void setOrderseats(String orderseats) {
        this.orderseats = orderseats;
    }

    @Basic
    @Column(name = "beforebackprice")
    public Double getBeforebackprice() {
        return beforebackprice;
    }

    public void setBeforebackprice(Double beforebackprice) {
        this.beforebackprice = beforebackprice;
    }

    @Basic
    @Column(name = "hascheck")
    public String getHascheck() {
        return hascheck;
    }

    public void setHascheck(String hascheck) {
        this.hascheck = hascheck;
    }

    @Basic
    @Column(name = "showtime")
    public Timestamp getShowtime() {
        return showtime;
    }

    public void setShowtime(Timestamp showtime) {
        this.showtime = showtime;
    }

    @Basic
    @Column(name = "venueid")
    public Integer getVenueid() {
        return venueid;
    }

    public void setVenueid(Integer venueid) {
        this.venueid = venueid;
    }

    @Basic
    @Column(name = "iscount")
    public Integer getIscount() {
        return iscount;
    }

    public void setIscount(Integer iscount) {
        this.iscount = iscount;
    }

}
