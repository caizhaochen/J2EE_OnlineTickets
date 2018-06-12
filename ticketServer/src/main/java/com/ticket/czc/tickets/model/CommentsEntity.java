package com.ticket.czc.tickets.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "comments", schema = "tickets", catalog = "")
public class CommentsEntity {
    private int commentId;
    private Integer showId;
    private String userEmail;
    private String commentContent;
    private Timestamp commentTime;

    @Id
    @Column(name = "commentId")
    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    @Basic
    @Column(name = "showId")
    public Integer getShowId() {
        return showId;
    }

    public void setShowId(Integer showId) {
        this.showId = showId;
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
    @Column(name = "commentContent")
    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    @Basic
    @Column(name = "commentTime")
    public Timestamp getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Timestamp commentTime) {
        this.commentTime = commentTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentsEntity that = (CommentsEntity) o;

        if (commentId != that.commentId) return false;
        if (showId != null ? !showId.equals(that.showId) : that.showId != null) return false;
        if (userEmail != null ? !userEmail.equals(that.userEmail) : that.userEmail != null) return false;
        if (commentContent != null ? !commentContent.equals(that.commentContent) : that.commentContent != null)
            return false;
        if (commentTime != null ? !commentTime.equals(that.commentTime) : that.commentTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = commentId;
        result = 31 * result + (showId != null ? showId.hashCode() : 0);
        result = 31 * result + (userEmail != null ? userEmail.hashCode() : 0);
        result = 31 * result + (commentContent != null ? commentContent.hashCode() : 0);
        result = 31 * result + (commentTime != null ? commentTime.hashCode() : 0);
        return result;
    }
}
