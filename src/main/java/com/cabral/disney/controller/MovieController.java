package com.cabral.disney.controller;

import com.cabral.disney.exception.MovieNotFoundException;
import com.cabral.disney.exception.MovieSearchEmptyResultException;
import com.cabral.disney.payload.request.MovieRequest;
import com.cabral.disney.payload.response.MovieResponse;
import com.cabral.disney.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movie/all")
    public ResponseEntity<List<MovieResponse>> getMovies() {
        return ResponseEntity.ok(this.movieService.getAllMovies());
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<?> getMovie(@PathVariable Long id) throws MovieNotFoundException {
        MovieResponse movieResponse = this.movieService.getMovieById(id);
        return ResponseEntity.ok(movieResponse);
    }

    @GetMapping("/movie")
    public ResponseEntity<?> searchMovie(@RequestParam String name) throws MovieSearchEmptyResultException {
        List<MovieResponse> movieResponses = this.movieService.searchMovie(name);
        return ResponseEntity.ok(movieResponses);
    }

    @PostMapping("/movie")
    public ResponseEntity<MovieResponse> createMovie(
            @RequestPart("movieRequest") @Valid MovieRequest request,
            @RequestPart(value = "image", required = false) MultipartFile file
    ) {
        MovieResponse createdMovie = this.movieService.createMovie(request, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
    }

    @PutMapping("/movie/{id}")
    public ResponseEntity<?> updateMovie(
            @PathVariable Long id,
            @RequestPart("movieRequest") @Valid MovieRequest request,
            @RequestPart(value = "image", required = false) MultipartFile file
    ) throws MovieNotFoundException {
        MovieResponse movieUpdated = this.movieService.updateMovie(id, request, file);
        return ResponseEntity.ok(movieUpdated);
    }

    @DeleteMapping("/movie/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) throws MovieNotFoundException {
        this.movieService.deleteMovie(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Movie deleted successfully.");

    }

    @GetMapping("/movie/{id}/image")
    public ResponseEntity<byte[]> getCharacterImage(@PathVariable Long id) throws MovieNotFoundException {
        MovieResponse movieResponse = this.movieService.getMovieById(id);
        if (movieResponse != null && movieResponse.getImage() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Adjust content type as needed
                    .body(movieResponse.getImage());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
