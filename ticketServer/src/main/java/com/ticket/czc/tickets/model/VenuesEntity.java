package com.ticket.czc.tickets.model;

import javax.persistence.*;

@Entity
@Table(name = "venues", schema = "tickets", catalog = "")
public class VenuesEntity {
    private int venueid;
    private String venuepassword;
    private String venuephone;
    private String location;
    private String information;
    private Integer ischecked;
    private Integer venueline;
    private Integer venuerow;
    private String venuename;
    private String checkInfo;
    private Double income;

    @Id
    @Column(name = "venueid")
    public int getVenueid() {
        return venueid;
    }

    public void setVenueid(int venueid) {
        this.venueid = venueid;
    }

    @Basic
    @Column(name = "venuepassword")
    public String getVenuepassword() {
        return venuepassword;
    }

    public void setVenuepassword(String venuepassword) {
        this.venuepassword = venuepassword;
    }

    @Basic
    @Column(name = "venuephone")
    public String getVenuephone() {
        return venuephone;
    }

    public void setVenuephone(String venuephone) {
        this.venuephone = venuephone;
    }

    @Basic
    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "information")
    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Basic
    @Column(name = "ischecked")
    public Integer getIschecked() {
        return ischecked;
    }

    public void setIschecked(Integer ischecked) {
        this.ischecked = ischecked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VenuesEntity that = (VenuesEntity) o;

        if (venueid != that.venueid) return false;
        if (venuepassword != null ? !venuepassword.equals(that.venuepassword) : that.venuepassword != null)
            return false;
        if (venuephone != null ? !venuephone.equals(that.venuephone) : that.venuephone != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (information != null ? !information.equals(that.information) : that.information != null) return false;
        if (ischecked != null ? !ischecked.equals(that.ischecked) : that.ischecked != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = venueid;
        result = 31 * result + (venuepassword != null ? venuepassword.hashCode() : 0);
        result = 31 * result + (venuephone != null ? venuephone.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (information != null ? information.hashCode() : 0);
        result = 31 * result + (ischecked != null ? ischecked.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "venueline")
    public Integer getVenueline() {
        return venueline;
    }

    public void setVenueline(Integer venueline) {
        this.venueline = venueline;
    }

    @Basic
    @Column(name = "venuerow")
    public Integer getVenuerow() {
        return venuerow;
    }

    public void setVenuerow(Integer venuerow) {
        this.venuerow = venuerow;
    }

    @Basic
    @Column(name = "venuename")
    public String getVenuename() {
        return venuename;
    }

    public void setVenuename(String venuename) {
        this.venuename = venuename;
    }

    @Basic
    @Column(name = "checkInfo")
    public String getCheckInfo() {
        return checkInfo;
    }

    public void setCheckInfo(String checkInfo) {
        this.checkInfo = checkInfo;
    }

    @Basic
    @Column(name = "income")
    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }
}
