package com.cabral.disney.controller;

import com.cabral.disney.dto.PeliculaDTO;
import com.cabral.disney.exception.PeliculaNotFoundException;
import com.cabral.disney.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/peliculas")
public class PeliculaController {

    private PeliculaService peliculaService;

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
}
