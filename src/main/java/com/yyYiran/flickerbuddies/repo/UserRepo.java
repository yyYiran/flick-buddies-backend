package com.yyYiran.flickerbuddies.repo;

import com.yyYiran.flickerbuddies.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

// CRUD operation and customized query operations
public interface UserRepo extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username =:username")
    User findUserByName(@Param("username") String username);



}
