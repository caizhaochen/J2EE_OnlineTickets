package com.ticket.czc.tickets.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "shows", schema = "tickets", catalog = "")
public class ShowsEntity {
    private int showid;
    private Integer venueid;
    private String showname;
    private Timestamp showtime;
    private String showtype;
    private String showdescribe;
    private Integer showline;
    private Integer showrow;
    private Integer restseats;
    private Timestamp posttime;
    private Double venueget;
    private Double adminget;

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "showid")
    public int getShowid() {
        return showid;
    }

    public void setShowid(int showid) {
        this.showid = showid;
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
    @Column(name = "showname")
    public String getShowname() {
        return showname;
    }

    public void setShowname(String showname) {
        this.showname = showname;
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
    @Column(name = "showtype")
    public String getShowtype() {
        return showtype;
    }

    public void setShowtype(String showtype) {
        this.showtype = showtype;
    }

    @Basic
    @Column(name = "showdescribe")
    public String getShowdescribe() {
        return showdescribe;
    }

    public void setShowdescribe(String showdescribe) {
        this.showdescribe = showdescribe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShowsEntity that = (ShowsEntity) o;

        if (showid != that.showid) return false;
        if (venueid != null ? !venueid.equals(that.venueid) : that.venueid != null) return false;
        if (showname != null ? !showname.equals(that.showname) : that.showname != null) return false;
        if (showtime != null ? !showtime.equals(that.showtime) : that.showtime != null) return false;
        if (showtype != null ? !showtype.equals(that.showtype) : that.showtype != null) return false;
        if (showdescribe != null ? !showdescribe.equals(that.showdescribe) : that.showdescribe != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = showid;
        result = 31 * result + (venueid != null ? venueid.hashCode() : 0);
        result = 31 * result + (showname != null ? showname.hashCode() : 0);
        result = 31 * result + (showtime != null ? showtime.hashCode() : 0);
        result = 31 * result + (showtype != null ? showtype.hashCode() : 0);
        result = 31 * result + (showdescribe != null ? showdescribe.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "showline")
    public Integer getShowline() {
        return showline;
    }

    public void setShowline(Integer showline) {
        this.showline = showline;
    }

    @Basic
    @Column(name = "showrow")
    public Integer getShowrow() {
        return showrow;
    }

    public void setShowrow(Integer showrow) {
        this.showrow = showrow;
    }

    @Basic
    @Column(name = "restseats")
    public Integer getRestseats() {
        return restseats;
    }

    public void setRestseats(Integer restseats) {
        this.restseats = restseats;
    }

    @Basic
    @Column(name = "posttime")
    public Timestamp getPosttime() {
        return posttime;
    }

    public void setPosttime(Timestamp posttime) {
        this.posttime = posttime;
    }

    @Basic
    @Column(name = "venueget")
    public Double getVenueget() {
        return venueget;
    }

    public void setVenueget(Double venueget) {
        this.venueget = venueget;
    }

    @Basic
    @Column(name = "adminget")
    public Double getAdminget() {
        return adminget;
    }

    public void setAdminget(Double adminget) {
        this.adminget = adminget;
    }
}
