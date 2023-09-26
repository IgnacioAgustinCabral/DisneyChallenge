package com.cabral.disney.controller;

import com.cabral.disney.payload.response.GenreResponse;
import com.cabral.disney.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genre/all")
    public ResponseEntity<List<GenreResponse>> getAllGenres(){
        return ResponseEntity.ok(this.genreService.getAllGenres());
    }
}
