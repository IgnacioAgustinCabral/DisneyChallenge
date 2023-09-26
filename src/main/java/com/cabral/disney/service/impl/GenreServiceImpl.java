package com.cabral.disney.service.impl;

import com.cabral.disney.exception.GenreNotFoundException;
import com.cabral.disney.mapper.GenreMapper;
import com.cabral.disney.models.Genre;
import com.cabral.disney.payload.request.GenreRequest;
import com.cabral.disney.payload.response.GenreResponse;
import com.cabral.disney.repository.GenreRepository;
import com.cabral.disney.service.GenreService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {
    private GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<GenreResponse> getAllGenres() {
        List<Genre> genres = this.genreRepository.findAll();
        List<GenreResponse> genreResponses = genres.stream()
                .map(genre -> GenreMapper.mapToDTO(genre))
                .collect(Collectors.toList());

        return genreResponses;
    }

    @Override
    public GenreResponse getGenreById(Long id) throws GenreNotFoundException {
        Genre genre = this.genreRepository.findById(id).orElseThrow(() -> new GenreNotFoundException("Genre not found with id: " + id));

        return GenreMapper.mapToDTO(genre);
    }

    @Override
    public GenreResponse createGenre(GenreRequest request) {
        Genre genre = GenreMapper.mapToEntity(request);
        Genre savedGenre = this.genreRepository.save(genre);

        return GenreMapper.mapToDTO(savedGenre);
    }

    @Override
    public GenreResponse updateGenre(Long id, GenreRequest request) throws GenreNotFoundException {
        Genre genre = this.genreRepository.findById(id).orElseThrow(() -> new GenreNotFoundException("Genre not found with id: " + id));

        genre.setName(request.getName());

        Genre updatedGenre = this.genreRepository.save(genre);

        return GenreMapper.mapToDTO(updatedGenre);
    }
}
