package com.cabral.disney.controller;

import com.cabral.disney.dto.PeliculaDTO;
import com.cabral.disney.exception.PeliculaNotFoundException;
import com.cabral.disney.exception.PeliculaSearchEmptyResultException;
import com.cabral.disney.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/peliculas")
public class PeliculaController {

    private final PeliculaService peliculaService;

    @Autowired
    public PeliculaController(PeliculaService peliculaService) {
        this.peliculaService = peliculaService;
    }

    @GetMapping("/pelicula/all")
    public ResponseEntity<List<PeliculaDTO>> getPeliculas() {
        return ResponseEntity.ok(this.peliculaService.getAllPeliculas());
    }

    @GetMapping("/pelicula/{id}")
    public ResponseEntity<?> getPelicula(@PathVariable Long id) {
        try {
            PeliculaDTO peliculaDTO = this.peliculaService.getPeliculaById(id);
            return ResponseEntity.ok(peliculaDTO);
        } catch (PeliculaNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/pelicula")
    public ResponseEntity<?> searchPersonajes(@RequestParam String name) {
        try {
            List<PeliculaDTO> peliculas = this.peliculaService.searchPelicula(name);
            return ResponseEntity.ok(peliculas);
        } catch (PeliculaSearchEmptyResultException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PostMapping(value = "/pelicula")
    public ResponseEntity<PeliculaDTO> createPelicula(@RequestBody PeliculaDTO peliculaDTO) {
        PeliculaDTO createdPelicula = this.peliculaService.createPelicula(peliculaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPelicula);
    }

    @PutMapping("/pelicula/{id}")
    public ResponseEntity<?> updatePelicula(@PathVariable Long id, @RequestBody PeliculaDTO peliculaDTO) {
        try {
            PeliculaDTO peliculaDTOUpdated = this.peliculaService.updatePelicula(id, peliculaDTO);
            return ResponseEntity.ok(peliculaDTOUpdated);
        } catch (PeliculaNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @DeleteMapping("/pelicula/{id}")
    public ResponseEntity<?> deletePelicula(@PathVariable Long id) {
        try {
            this.peliculaService.deletePelicula(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Pelicula deleted successfully.");
        } catch (PeliculaNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
}
