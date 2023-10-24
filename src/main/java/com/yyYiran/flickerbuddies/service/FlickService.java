package com.yyYiran.flickerbuddies.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yyYiran.flickerbuddies.model.Movie;
import com.yyYiran.flickerbuddies.model.Review;
import com.yyYiran.flickerbuddies.model.Status;
import com.yyYiran.flickerbuddies.model.User;
import com.yyYiran.flickerbuddies.repo.MovieRepo;
import com.yyYiran.flickerbuddies.repo.UserRepo;
import com.yyYiran.flickerbuddies.repo.ReviewRepo;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Handle all CRUD operations related to
 */
@Service
@Transactional
public class FlickService {
    @Value("${api.token}")
    private String apiToken;
    //
    @Value("${api.url}")
    private String apiUrl;
    private final MovieRepo movieRepo;
    private final UserRepo userRepo;
    private final ReviewRepo reviewRepo;



    @Autowired
    public FlickService(MovieRepo movieRepo, UserRepo userRepo, ReviewRepo reviewRepo) {
        this.movieRepo = movieRepo;
        this.userRepo = userRepo;
        this.reviewRepo = reviewRepo;
    }

    // Movie
    // Main: Add a movie to pool if this movie not exist
    public Movie addMovie(Movie m){
        if (movieRepo.findMovieByCode(m.getMovieCode()) == null){
            m.setId();
            return movieRepo.save(m);
        }
        return null;
    }

    public Movie findMovieById(long id){
        return movieRepo.findMovieById(id);
    }

    public Movie findMovieByCode(int code){
        return movieRepo.findMovieByCode(code);
    }

    public List<Movie> getMovies(String query) {
        HttpHeaders headers = new HttpHeaders();
//        String apiToken = environment.getProperty("api.token");
//        String apiUrl = environment.getProperty("api.url");
        headers.set("Authorization", "Bearer " + apiToken);
        headers.set("accept", "application/json");
        HttpEntity<JsonNode> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        System.err.println("api url:" + apiUrl);
        System.err.println("token:" + apiToken);
        String url = String.format(apiUrl, query);

        ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class);

        ObjectMapper objectMapper = new ObjectMapper();

        if (response.getStatusCode() == HttpStatus.OK) {

            try{
                JsonNode responseNodes = objectMapper
                        .readerForListOf(Movie.class)
                        .readTree(response.getBody()
                                .get("results").toString());
                List<Movie> movies = new ArrayList<>();
                for (JsonNode node : responseNodes){
                    if (node.get("poster_path").asText() != "null"){
                        Movie m = new Movie(node.get("title").asText(),
                                node.get("id").asInt(),
                                node.get("overview").asText(),
                                node.get("poster_path").asText(),
                                node.get("release_date").asText());
                        movies.add(m);
                    }
                }
                System.err.println(movies);
                return movies;
            } catch (IOException e){
                System.err.println("IO Expection");
                return Collections.emptyList();
            }

        } else {
            // Handle error here
            return Collections.emptyList();
        }
    }

    // User
    // Main: Sign up new user
    // Find if this user already exists in pool -> sign in rather than sign up
    public User addUser(User u){
        String username = u.getUsername();
        if (userRepo.findUserByName(username) == null){
            return userRepo.save(u);
        }
        return null;
    }

    public User findUserByName(String username){
        User u =  userRepo.findUserByName(username);
        return u;
    }

    // Review / User-Movie relation
    // find a review
    public Review findReview(Review r) {
        return reviewRepo.findReviewByIds(r.getId().getUserId(), r.getId().getMovieId());
    }

    // find all reviews of a book
    public List<Review> findReviewsOfMovie(long movieId){
        return reviewRepo.findReviewsOfMovie(movieId);
    }

    // add a review, assume this relation not found
    public Review addReview(Review r){
        if (reviewRepo.findReviewByIds(r.getId().getUserId(), r.getId().getMovieId()) == null){
            return reviewRepo.save(r);
        }
        return null;
    }

    // update a review, assume this review exists
    public boolean updateReview(Review r){
        Review reviewToUpdate = reviewRepo.findReviewByIds(r.getId().getUserId(), r.getId().getMovieId());
        if (reviewToUpdate == null){
            return false;
        }
        if (r.getStatus() == Status.WATCHED){
            reviewToUpdate.setReview(r.getReview());
            reviewToUpdate.setRating(r.getRating());
        }
        reviewToUpdate.setStatus(r.getStatus());
        reviewRepo.save(reviewToUpdate);
        return true;
    }

    public boolean moveToWatchlist(Review r){
        Review reviewToUpdate = reviewRepo.findReviewByIds(r.getId().getUserId(), r.getId().getMovieId());
        if (reviewToUpdate == null){
            return false;
        }
        reviewToUpdate.setStatus(Status.WATCHLISTED);
        reviewRepo.save(reviewToUpdate);
        return true;
    }

    // TODO: delete a review

    // Find the list of movies this user reviewed
    public List<Movie> findWatchedMoviesByUser(long userId){
        return reviewRepo.findWatchedMoviesByUser(userId);
    }

    private List<Review> findReviewsByUser(long userId){
        return reviewRepo.findReviewsByUser(userId);
    }

    public Long usernameToId(String username){
        return userRepo.findUserByName(username).getId();
    }

    public List<Review> findReviewsByUser(String username){
        return reviewRepo.findReviewsByUser(usernameToId(username));
    }


    // Find the list of movies this user watchlisted
    public List<Movie> findWatchlistedMoviesByUser(String username){
        return reviewRepo.findWatchlistedMoviesByUser(usernameToId(username));
    }

    // Find the list of users that is interested in this movie
    public List<User> findUsersByMovie(long movieId){
        return reviewRepo.findUsersByMovie(movieId);
    }

}
