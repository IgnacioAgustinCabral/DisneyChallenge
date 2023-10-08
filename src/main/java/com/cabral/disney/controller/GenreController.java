package com.cabral.disney.controller;

import com.cabral.disney.exception.GenreNotFoundException;
import com.cabral.disney.payload.request.GenreRequest;
import com.cabral.disney.payload.response.GenreResponse;
import com.cabral.disney.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genre/all")
    public ResponseEntity<List<GenreResponse>> getAllGenres() {
        return ResponseEntity.ok(this.genreService.getAllGenres());
    }

    @GetMapping("/genre/{id}")
    public ResponseEntity<?> getGenreById(@PathVariable Long id) throws GenreNotFoundException {
        GenreResponse genre = this.genreService.getGenreById(id);

        return ResponseEntity.ok(genre);
    }

    @PostMapping("/genre")
    public ResponseEntity<?> createGenre(@Valid @RequestBody GenreRequest request) {
        GenreResponse genreResponse = this.genreService.createGenre(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(genreResponse);
    }

    @PutMapping("/genre/{id}")
    public ResponseEntity<?> updateGenre(@PathVariable Long id, @Valid @RequestBody GenreRequest request) throws GenreNotFoundException {
        GenreResponse updatedGenreResponse = this.genreService.updateGenre(id, request);

        return ResponseEntity.ok(updatedGenreResponse);
    }

    @DeleteMapping("/genre/{id}")
    public ResponseEntity<?> deleteGenre(@PathVariable Long id) throws GenreNotFoundException {
        this.genreService.deleteGenre(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Genre deleted successfully");

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
