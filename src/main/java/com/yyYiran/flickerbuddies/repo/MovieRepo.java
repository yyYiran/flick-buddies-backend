package com.yyYiran.flickerbuddies.repo;

import com.yyYiran.flickerbuddies.model.Movie;
import com.yyYiran.flickerbuddies.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

// CRUD operation and customized query operations
public interface MovieRepo extends JpaRepository<Movie, Long> {

    @Query("SELECT m FROM Movie m WHERE m.movieCode = :movieCode")
    Movie findMovieByCode(@Param("movieCode") int movieCode);

    @Query("SELECT m FROM Movie m WHERE m.id = :id")
    Movie findMovieById(@Param("id") long id);

}
