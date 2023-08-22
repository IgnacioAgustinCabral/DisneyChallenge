package com.cabral.disney.service.impl;

import com.cabral.disney.dto.PeliculaDTO;
import com.cabral.disney.entity.Pelicula;
import com.cabral.disney.exception.PeliculaNotFoundException;
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

    @Override
    public PeliculaDTO getPeliculaById(Long id) throws PeliculaNotFoundException {
        Pelicula pelicula = this.peliculaRepository.findById(id).orElseThrow(() -> new PeliculaNotFoundException("Pelicula could not be found."));

        return PeliculaMapper.mapToDTO(pelicula);
    }

    @Override
    public PeliculaDTO createPelicula(PeliculaDTO peliculaDTO) {
        Pelicula newPelicula = this.peliculaRepository.save(PeliculaMapper.mapToEntity(peliculaDTO));

        return PeliculaMapper.mapToDTO(newPelicula);
    }

    @Override
    public PeliculaDTO updatePelicula(Long id, PeliculaDTO peliculaDTO) throws PeliculaNotFoundException {
        Pelicula pelicula = this.peliculaRepository.findById(id).orElseThrow(() -> new PeliculaNotFoundException("Pelicula could not be found."));

        pelicula.setId(peliculaDTO.getId());
        pelicula.setImagen(peliculaDTO.getImagen());
        pelicula.setFecha_creacion(peliculaDTO.getFecha_creacion());
        pelicula.setCalificacion(peliculaDTO.getCalificacion());
        pelicula.setTitulo(peliculaDTO.getTitulo());

        Pelicula updatedPelicula = this.peliculaRepository.save(pelicula);

        return PeliculaMapper.mapToDTO(updatedPelicula);
    }
}
