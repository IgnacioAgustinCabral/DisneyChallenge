package com.cabral.disney.repository;

import com.cabral.disney.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT m FROM Movie m WHERE m.title LIKE %:name%")
    List<Movie> searchMovie(@Param("name") String name);
}
