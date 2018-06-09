package com.ticket.czc.tickets.model;

import javax.persistence.*;

@Entity
@Table(name = "seats", schema = "tickets", catalog = "")
public class SeatsEntity {
    private int seatsid;
    private Integer venueid;
    private Integer showid;
    private Integer orderid;
    private Double seatprice;
    private Integer seatisbooked;
    private Double seatrealprice;
    private Integer seatnum;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seatsid")
    public int getSeatsid() {
        return seatsid;
    }

    public void setSeatsid(int seatsid) {
        this.seatsid = seatsid;
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
    @Column(name = "showid")
    public Integer getShowid() {
        return showid;
    }

    public void setShowid(Integer showid) {
        this.showid = showid;
    }

    @Basic
    @Column(name = "orderid")
    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    @Basic
    @Column(name = "seatprice")
    public Double getSeatprice() {
        return seatprice;
    }

    public void setSeatprice(Double seatprice) {
        this.seatprice = seatprice;
    }

    @Basic
    @Column(name = "seatisbooked")
    public Integer getSeatisbooked() {
        return seatisbooked;
    }

    public void setSeatisbooked(Integer seatisbooked) {
        this.seatisbooked = seatisbooked;
    }

    @Basic
    @Column(name = "seatrealprice")
    public Double getSeatrealprice() {
        return seatrealprice;
    }

    public void setSeatrealprice(Double seatrealprice) {
        this.seatrealprice = seatrealprice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SeatsEntity that = (SeatsEntity) o;

        if (seatsid != that.seatsid) return false;
        if (venueid != null ? !venueid.equals(that.venueid) : that.venueid != null) return false;
        if (showid != null ? !showid.equals(that.showid) : that.showid != null) return false;
        if (orderid != null ? !orderid.equals(that.orderid) : that.orderid != null) return false;
        if (seatprice != null ? !seatprice.equals(that.seatprice) : that.seatprice != null) return false;
        if (seatisbooked != null ? !seatisbooked.equals(that.seatisbooked) : that.seatisbooked != null) return false;
        if (seatrealprice != null ? !seatrealprice.equals(that.seatrealprice) : that.seatrealprice != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = seatsid;
        result = 31 * result + (venueid != null ? venueid.hashCode() : 0);
        result = 31 * result + (showid != null ? showid.hashCode() : 0);
        result = 31 * result + (orderid != null ? orderid.hashCode() : 0);
        result = 31 * result + (seatprice != null ? seatprice.hashCode() : 0);
        result = 31 * result + (seatisbooked != null ? seatisbooked.hashCode() : 0);
        result = 31 * result + (seatrealprice != null ? seatrealprice.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "seatnum")
    public Integer getSeatnum() {
        return seatnum;
    }

    public void setSeatnum(Integer seatnum) {
        this.seatnum = seatnum;
    }
}
