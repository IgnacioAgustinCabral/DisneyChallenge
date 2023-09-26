package com.cabral.disney.service;

import com.cabral.disney.exception.GenreNotFoundException;
import com.cabral.disney.payload.request.GenreRequest;
import com.cabral.disney.payload.response.GenreResponse;

import java.util.List;

public interface GenreService {
    List<GenreResponse> getAllGenres();

    GenreResponse getGenreById(Long id) throws GenreNotFoundException;

    GenreResponse createGenre(GenreRequest request);

    GenreResponse updateGenre(Long id, GenreRequest request) throws GenreNotFoundException;

    void deleteGenre(Long id) throws GenreNotFoundException;
}
