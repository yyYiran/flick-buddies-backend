package com.yyYiran.flickerbuddies;

import com.yyYiran.flickerbuddies.model.Movie;
import com.yyYiran.flickerbuddies.model.Review;
import com.yyYiran.flickerbuddies.model.User;
import com.yyYiran.flickerbuddies.repo.MovieRepo;
import com.yyYiran.flickerbuddies.repo.ReviewRepo;
import com.yyYiran.flickerbuddies.repo.UserRepo;
import com.yyYiran.flickerbuddies.service.FlickService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FlickerbuddiesApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlickerbuddiesApplication.class, args);
		System.out.println("Running FlickerbuddiesApplication");
//		ApplicationContext context = SpringApplication.run(FlickerbuddiesApplication.class, args);
//		UserRepo userRepo = context.getBean(UserRepo.class);
//		MovieRepo movieRepo = context.getBean(MovieRepo.class);
//		ReviewRepo reviewRepo = context.getBean(ReviewRepo.class);
//
//		User u1 = new User("u1", "u111");
//		User ud = new User("u1", "u111");
//		User u2 = new User("u2", "u222");
//		User u3 = new User("u3newelephant", "333 new");
//		userRepo.saveAll(Arrays.asList(u1, u2));
////		userRepo.save(ud);
//
//		FlickService fs = new FlickService(movieRepo, userRepo, reviewRepo);
//		Movie m1 = new Movie("1984", 123, "A movie about big brother", "/1212.jpg", "2018/3/4");
//		Movie m2 = new Movie("animal farm", 1283, "Animals in a farm rebels", "/1212.jpg", "2008/12/25");
//		Movie m3 = new Movie("sunshine valley", 553, "sunshine valley shine your face", "/1212.jpg", "2008/12/25");
//		System.err.println("m1 raw " + m1);
//		movieRepo.saveAll(Arrays.asList(m1, m2));
////		System.out.println("movieRepo.findMovieByCode: " + movieRepo.findMovieByCode(md2.getMovieCode()));
//		Movie m = fs.addMovie(m1);
//		System.err.println("addMovie duplicate: " + m);
//		m = fs.addMovie(m3);
//		System.err.println("addMovie new: " + m);
//
//		User u = fs.addUser(ud);
//		System.err.println("addUser duplicate: " + u);
//		u =  fs.addUser(u3);
//		System.err.println("addUser new: " + u);
//
//
//		Review r1 = new Review(u1, m1, 8, "user1 love 1984! Very thought-provoking");
//		Review r2 = new Review(u1, m2, 3, "user1 doesn't like animal farm");
//		Review r3 = new Review(u3, m2, 2, "user3 hate animal farm");
//		Review r4 = new Review(u3, m3);
//		Review r5 = new Review(u2, m3, 9, "user3 likes movie 3 because it is sunny");
//		reviewRepo.saveAll(Arrays.asList(r1, r2, r3, r4, r5));
//
////		System.err.println("findWatchedMoviesByUser: " + reviewRepo.findWatchedMoviesByUser(u1.getId()));
////		System.err.println("findWatchedMoviesByUser: " + reviewRepo.findWatchedMoviesByUser(u2.getId()));
////		System.err.println("findWatchedMoviesByUser: " + reviewRepo.findWatchedMoviesByUser(u3.getId()));
//
//		System.err.println("findReviewsByUser: " + reviewRepo.findReviewsByUser(u1.getId()));
//
//		List<Movie> results = fs.getMovies("1984");
//		System.err.println("search movies "+ results);
//

//		System.err.println("findWatchlistedMoviesByUser: " + reviewRepo.findWatchlistedMoviesByUser(u1.getId()))

//		System.err.println("findWatchlistedMoviesByUser: " + reviewRepo.findWatchlistedMoviesByUser(u2.getId()));
//		System.err.println("findWatchlistedMoviesByUser: " + reviewRepo.findWatchlistedMoviesByUser(u3.getId()));
//
//		System.err.println("findUsersByMovie: " + reviewRepo.findUsersByMovie(m1.getId()));
//		System.err.println("findUsersByMovie: " + reviewRepo.findUsersByMovie(m2.getId()));
//		System.err.println("findUsersByMovie: " + reviewRepo.findUsersByMovie(m3.getId()));

//		Review r4 = new Review(u2, m3, 1, "u2 to u3");
//		Review r = reviewRepo.findReviewByIds(r1.getId().getUserId(), r1.getId().getMovieId());
//		System.err.println("find exist review: " + r);
//		r = reviewRepo.findReviewByIds(r4.getId().getUserId(), r4.getId().getMovieId());
//		System.err.println("find nonexist review: " + r);
//
//		Review r1Change = new Review(u1, m1, 0, "user1 now think 1-1984 not that good");
//		System.err.println("update watched review's rating & review: " + fs.updateReview(r1Change));
//		System.err.println("update not exist review: " + fs.updateReview(r4));
//
//		Review r2Change = new Review(u1, m2, 5, "user1 add review to 2-animal farm");
//		System.err.println("update watchlisted review to watched: " + fs.updateReview(r2Change));
//
//		r1Change = new Review(u1, m1);
//		System.err.println(":: update watched to watchlisted: " + fs.moveToWatchlist(r1Change));



//		Review r5 = new Review(u2, m1);
//		System.err.println("add new review: " + fs.addReview(r5));
//		System.err.println("add new review: " + fs.addReview(r4));

	}

}
