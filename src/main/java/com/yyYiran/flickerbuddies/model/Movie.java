package com.yyYiran.flickerbuddies.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
public class Movie implements Serializable {

    @Id
    private Long id;
    @Column(nullable = false)
    @JsonProperty("title")
    private String title;
    @Column(nullable = false, updatable = false)

    @JsonProperty("movie_code")
    private int movieCode;
    @JsonProperty("overview")
    @Column(length = 1000)
    private String overview;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("release_date")
    private String releaseDate;

    @OneToMany(mappedBy = "movie")
    private Set<Review> reviews;

    public Movie() {

    }

    public Movie(String title, int movieCode, String overview, String posterPath, String releaseDate) {
        this.id = (long)movieCode;
        this.title = title;
        this.movieCode = movieCode;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
    }

    public long setId(){
        this.id = (long)movieCode;
        return this.id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getMovieCode() {
        return movieCode;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", movieCode=" + movieCode +
                ", overview='" + overview + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return movieCode == movie.movieCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieCode);
    }
}
