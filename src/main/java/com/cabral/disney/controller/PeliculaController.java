package com.cabral.disney.controller;

import com.cabral.disney.dto.PeliculaDTO;
import com.cabral.disney.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}
