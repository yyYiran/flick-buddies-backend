package com.yyYiran.flickerbuddies.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Review {
    @EmbeddedId
    private UserToMovieId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(name="movie_id")
    @JsonIgnore
    private Movie movie;

    private int rating = -1;
    private String review = null;

    @Enumerated(EnumType.ORDINAL)
    private Status status = Status.NOT_WATCHED;

    // Construct a review for watched movie
    public Review(long userId, long movieId, int rating, String review){
        this.id = new UserToMovieId(userId, movieId);
        this.rating = rating;
        this.review = review;
        this.status = Status.WATCHED;
    }

    public Review(User user, Movie movie, int rating, String review) {
        this.id = new UserToMovieId(user.getId(), movie.getId());
        this.user = user;
        this.movie = movie;
        this.rating = rating;
        this.review = review;
        this.status = Status.WATCHED;
    }

    // Construct a relation for watchlisted movie
    public Review(User user, Movie movie) {
        this.id = new UserToMovieId(user.getId(), movie.getId());
        this.user = user;
        this.movie = movie;
        this.status = Status.WATCHLISTED;
        this.review = null;
        this.rating = -1;
    }

    public int getRating() {
        return rating;
    }

    public UserToMovieId getId() {
        return id;
    }

    public String getReview() {
        return review;
    }

    public User getUser() {
        return user;
    }

    public Movie getMovie() {
        return movie;
    }

    public Status getStatus() {
        return status;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setStatus(Status status) {
        if (status == Status.WATCHLISTED){
            this.review = null;
            this.rating = -1;
        }
        this.status = status;
    }

    public Review() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return id.equals(review.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", user=" + user +
                ", movie=" + movie +
                ", rating=" + rating +
                ", review='" + review + '\'' +
                ", status=" + status +
                '}';
    }
}
