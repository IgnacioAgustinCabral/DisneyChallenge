package com.cabral.disney.repository;

import com.cabral.disney.models.LikedMovies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikedMovieRepository extends JpaRepository<LikedMovies, Long> {
    List<LikedMovies> findByUser_Username(String username);
}