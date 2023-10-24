package com.yyYiran.flickerbuddies.repo;

import com.yyYiran.flickerbuddies.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token, Long> {
    @Query("SELECT t FROM Token t WHERE t.token =:token")
    Token findByToken(String token);

    @Query("Select t FROM Token t WHERE t.user.id=:userId")
    Token findTokenByUser(@Param("userId") long userId);
}
