package com.cabral.disney.repository;

import com.cabral.disney.models.WatchedMovie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WatchedMoviesRepository extends JpaRepository<WatchedMovie, Long> {
    Optional<WatchedMovie> findByMovie_IdAndUser_Id(Long movieId, Long userId);
}
