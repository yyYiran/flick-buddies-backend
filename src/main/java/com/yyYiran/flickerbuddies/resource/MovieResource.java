package com.yyYiran.flickerbuddies.resource;


import com.yyYiran.flickerbuddies.model.Movie;
import com.yyYiran.flickerbuddies.model.Review;
import com.yyYiran.flickerbuddies.model.ReviewRequest;
import com.yyYiran.flickerbuddies.model.User;
import com.yyYiran.flickerbuddies.service.FlickService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/movie")
public class MovieResource {
    private final FlickService fs;


    @Autowired
    public MovieResource(FlickService fs) {
        this.fs = fs;
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie m){
        Movie createdMovie = fs.addMovie(m);
        System.err.println("Created movie = "+ createdMovie);
        return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
    }

    @GetMapping(value="/{movieId}/watchers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getWatchersOf(@PathVariable long movieId){
//        System.err.println("calling getreviewsofuser");
        List<User> watchers = fs.findUsersByMovie(movieId);
        return new ResponseEntity<>(watchers, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Movie>> searchForMovie(@RequestParam("q") String q){
        List<Movie> results = fs.getMovies(q);
        HttpHeaders headers = new HttpHeaders();
//        headers.setAccessControlAllowOrigin("*");
        return ResponseEntity.ok().headers(headers).body(results);
    }

    @GetMapping(value="/{movieId}/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Review>> getReviewsOf(@PathVariable long movieId){
        List<Review> reviews = fs.findReviewsOfMovie(movieId);
        return ResponseEntity.ok().body(reviews);
    }

    @PreAuthorize("#r.username == principal.username")
    @PostMapping(value="/{movieId}/review", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Review> addReview(@RequestBody ReviewRequest r){
        System.err.println("review request = " + r);
        User u = fs.findUserByName(r.getUsername());
        Movie m = fs.findMovieByCode((int)r.getMovieId());
        System.err.println("username: " + u);
        System.err.println("movieid: " + m);
        if (u == null || m == null){
            return ResponseEntity.badRequest().build();
        }
        Review newReview = new Review (u, m, r.getRating(), r.getReview());
        Review addedReview = fs.addReview(newReview);
        if (addedReview == null)
            return new ResponseEntity<>(addedReview, HttpStatus.CONFLICT);
        return new ResponseEntity<>(addedReview, HttpStatus.CREATED);
    }

//    @PostMapping(value="/{movieId}/review")
//    public ResponseEntity<Review> addReview(@RequestBody Review r){
//        Review addedReview = fs.addReview(r);
//        return new ResponseEntity<>(addedReview, HttpStatus.CREATED);
//    }

    @PutMapping(value="/{movieId}/review")
    public ResponseEntity<String> updateReview(@RequestBody Review r){
        boolean success = fs.updateReview(r);
        if (!success){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review doesn't exist");
        }
        return ResponseEntity.ok("Success");
    }
}
