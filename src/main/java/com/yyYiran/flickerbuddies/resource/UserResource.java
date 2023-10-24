package com.yyYiran.flickerbuddies.resource;


import com.yyYiran.flickerbuddies.model.Movie;
import com.yyYiran.flickerbuddies.model.Review;
import com.yyYiran.flickerbuddies.model.User;
import com.yyYiran.flickerbuddies.service.FlickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "http://localhost:4200/")
public class UserResource {
    private final FlickService fs;

    @Autowired
    public UserResource(FlickService fs) {
        this.fs = fs;
    }

    // TODO: Preauthorize userId
    @PreAuthorize("#username == principal.username")
    @GetMapping(value="/{username}/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Review>> getReviewsOfUser(@PathVariable String username){
//        System.err.println("calling getreviewsofuser");
//        System.err.println(username);
//        System.err.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        List<Review> reviews = fs.findReviewsByUser(username);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    // TODO: Preauthorize userId
    @PreAuthorize("#username == principal.username")
    @GetMapping(value="/{username}/watchlist", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Movie>> getWatchlistOfUser(@PathVariable String username){
//        System.err.println("calling getreviewsofuser");
        List<Movie> watchlist = fs.findWatchlistedMoviesByUser(username);
        return new ResponseEntity<>(watchlist, HttpStatus.OK);
    }

//    private boolean isPasswordValid(String password){
//        if (password.length() < 8){
//            return false;
//        }
//        String uppercaseRegex = ".*[A-Z].*";
//        String lowercaseRegex = ".*[a-z].*";
//        String digitRegex = ".*[0-9].*";
//        String specialCharRegex = ".*[@#$%^&*+=!()].*";
//
//        return password.matches(uppercaseRegex)
//                && password.matches(lowercaseRegex)
//                && password.matches(digitRegex)
//                && password.matches(specialCharRegex);
//    }

    // add a new user to database
//    @PostMapping("/signup")
//    public ResponseEntity<String> signupUser(@RequestBody User u){
//        if (u == null || u.getUsername() == null || u.getPassword() == null){
//            return ResponseEntity.badRequest()
//                    .body("Empty username or password.");
//        }
//
//        if (u.getUsername().length() < 4){
//            return ResponseEntity.badRequest()
//                    .body("Invalid username. Username too short.");
//        }
//
//        if (!isPasswordValid(u.getPassword())){
//            return ResponseEntity.badRequest()
//                    .body("Invalid password.");
//        }
//
//        User newUser = fs.addUser(u);
//        if (newUser == null){
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists. Please try a different username.");
//        }
//        return ResponseEntity.status(HttpStatus.CREATED).body(newUser.getUsername() + "'s account is created!");
//    }


}
