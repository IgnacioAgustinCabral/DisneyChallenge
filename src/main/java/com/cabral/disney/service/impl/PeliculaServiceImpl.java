package com.cabral.disney.service.impl;

import com.cabral.disney.dto.PeliculaDTO;
import com.cabral.disney.entity.Pelicula;
import com.cabral.disney.mapper.PeliculaMapper;
import com.cabral.disney.repository.PeliculaRepository;
import com.cabral.disney.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class PeliculaServiceImpl implements PeliculaService {

    private PeliculaRepository peliculaRepository;

    @Autowired
    public PeliculaServiceImpl(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    @Override
    public List<PeliculaDTO> getAllPeliculas() {
        List<Pelicula> peliculas = this.peliculaRepository.findAll();
        List<PeliculaDTO> peliculaDTOS = peliculas.stream().map(PeliculaMapper::mapToDTO).collect(Collectors.toList());
        return peliculaDTOS;
    }
}
