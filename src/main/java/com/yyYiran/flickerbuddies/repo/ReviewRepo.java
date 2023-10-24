package com.yyYiran.flickerbuddies.repo;

import com.yyYiran.flickerbuddies.model.Movie;
import com.yyYiran.flickerbuddies.model.Review;
import com.yyYiran.flickerbuddies.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// CRUD operation and customized query operations
public interface ReviewRepo extends JpaRepository<Review, Long> {
    @Query("SELECT r from Review r WHERE r.id.movieId=:movieId AND r.id.userId=:userId")
    Review findReviewByIds(@Param("userId") long userId, @Param("movieId")long movieId);

    @Query("SELECT m from Movie m, Review r where r.status=1 AND r.id.userId=:userId AND r.id.movieId=m.id")
    List<Movie> findWatchedMoviesByUser(@Param("userId")long userId);

    @Query("SELECT r from Movie m, Review r where r.status=1 AND r.id.userId=:userId AND r.id.movieId=m.id")
    List<Review> findReviewsByUser(@Param("userId")long userId);



    @Query("SELECT r from Review r WHERE r.id.movieId=:movieId AND r.status=1 ")
    List<Review> findReviewsOfMovie(@Param("movieId") long movieId);

    @Query("SELECT m from Movie m, Review r where r.status=2 AND r.id.userId=:userId AND r.id.movieId=m.id")
    List<Movie> findWatchlistedMoviesByUser(@Param("userId")long userId);

    @Query("SELECT u from User u, Review r where r.id.movieId=:movieId AND r.id.userId=u.id")
    List<User> findUsersByMovie(@Param("movieId")long movieId);
}
