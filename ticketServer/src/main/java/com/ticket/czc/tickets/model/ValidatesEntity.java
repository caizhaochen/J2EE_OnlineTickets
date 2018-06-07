package com.ticket.czc.tickets.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "validates", schema = "tickets", catalog = "")
public class ValidatesEntity {
    private int validateId;
    private String userEmail;
    private String validateCode;
    private Timestamp deadline;

    @Id
    @Column(name = "validateId")
    public int getValidateId() {
        return validateId;
    }

    public void setValidateId(int validateId) {
        this.validateId = validateId;
    }

    @Basic
    @Column(name = "userEmail")
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Basic
    @Column(name = "validateCode")
    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    @Basic
    @Column(name = "deadline")
    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValidatesEntity that = (ValidatesEntity) o;

        if (validateId != that.validateId) return false;
        if (userEmail != null ? !userEmail.equals(that.userEmail) : that.userEmail != null) return false;
        if (validateCode != null ? !validateCode.equals(that.validateCode) : that.validateCode != null) return false;
        if (deadline != null ? !deadline.equals(that.deadline) : that.deadline != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = validateId;
        result = 31 * result + (userEmail != null ? userEmail.hashCode() : 0);
        result = 31 * result + (validateCode != null ? validateCode.hashCode() : 0);
        result = 31 * result + (deadline != null ? deadline.hashCode() : 0);
        return result;
    }
}
