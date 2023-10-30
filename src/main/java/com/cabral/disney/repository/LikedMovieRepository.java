package com.cabral.disney.repository;

import com.cabral.disney.models.LikedMovies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedMovieRepository extends JpaRepository<LikedMovies,Long> {
}
