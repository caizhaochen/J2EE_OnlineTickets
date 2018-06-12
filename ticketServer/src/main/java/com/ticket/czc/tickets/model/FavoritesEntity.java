package com.ticket.czc.tickets.model;

import javax.persistence.*;

@Entity
@Table(name = "favorites", schema = "tickets", catalog = "")
public class FavoritesEntity {
    private int favoriteId;
    private Integer showId;
    private String userEmail;

    @Id
    @Column(name = "favoriteId")
    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FavoritesEntity that = (FavoritesEntity) o;

        if (favoriteId != that.favoriteId) return false;
        if (showId != null ? !showId.equals(that.showId) : that.showId != null) return false;
        if (userEmail != null ? !userEmail.equals(that.userEmail) : that.userEmail != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = favoriteId;
        result = 31 * result + (showId != null ? showId.hashCode() : 0);
        result = 31 * result + (userEmail != null ? userEmail.hashCode() : 0);
        return result;
    }
}
