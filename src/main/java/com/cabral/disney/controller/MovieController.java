package com.cabral.disney.controller;

import com.cabral.disney.exception.MovieNotFoundException;
import com.cabral.disney.exception.MovieSearchEmptyResultException;
import com.cabral.disney.payload.request.MovieRequest;
import com.cabral.disney.payload.response.MovieResponse;
import com.cabral.disney.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movie/all")
    public ResponseEntity<List<MovieResponse>> getMovies() {
        return ResponseEntity.ok(this.movieService.getAllMovies());
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<?> getMovie(@PathVariable Long id) {
        try {
            MovieResponse movieResponse = this.movieService.getMovieById(id);
            return ResponseEntity.ok(movieResponse);
        } catch (MovieNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/movie")
    public ResponseEntity<?> searchMovie(@RequestParam String name) {
        try {
            List<MovieResponse> movieResponses = this.movieService.searchMovie(name);
            return ResponseEntity.ok(movieResponses);
        } catch (MovieSearchEmptyResultException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PostMapping(value = "/movie")
    public ResponseEntity<MovieResponse> createMovie(@Valid @RequestBody MovieRequest request) {
        MovieResponse createdMovie = this.movieService.createMovie(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
    }

    @PutMapping("/movie/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable Long id, @RequestBody MovieRequest request) {
        try {
            MovieResponse movieUpdated = this.movieService.updateMovie(id, request);
            return ResponseEntity.ok(movieUpdated);
        } catch (MovieNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @DeleteMapping("/movie/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        try {
            this.movieService.deleteMovie(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Movie deleted successfully.");
        } catch (MovieNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
}
