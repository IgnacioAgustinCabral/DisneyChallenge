package com.cabral.disney.repository;

import com.cabral.disney.models.WatchedMovie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchedMoviesRepository extends JpaRepository<WatchedMovie, Long> {
}
