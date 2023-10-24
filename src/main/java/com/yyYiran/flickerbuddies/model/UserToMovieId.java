package com.yyYiran.flickerbuddies.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserToMovieId implements Serializable {

    @Column(name = "user_id")
    Long userId;

    @Column(name = "movie_id")
    Long movieId;

    public UserToMovieId(Long userId, Long movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }

    public UserToMovieId() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    @Override
    public String toString() {
        return "User:" + userId + " <-> Movie:" + movieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserToMovieId that = (UserToMovieId) o;
        return userId.equals(that.userId) && movieId.equals(that.movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, movieId);
    }
}
