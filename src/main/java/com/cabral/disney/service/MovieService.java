package com.cabral.disney.service;

import com.cabral.disney.exception.MovieNotFoundException;
import com.cabral.disney.exception.MovieSearchEmptyResultException;
import com.cabral.disney.payload.request.MovieRequest;
import com.cabral.disney.payload.response.MovieResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MovieService {
    List<MovieResponse> getAllMovies();

    MovieResponse getMovieById(Long id) throws MovieNotFoundException;

    List<MovieResponse> searchMovie(String name) throws MovieSearchEmptyResultException;

    MovieResponse createMovie(MovieRequest request, MultipartFile file);

    MovieResponse updateMovie(Long id, MovieRequest request, MultipartFile file) throws MovieNotFoundException;

    void deleteMovie(Long id) throws MovieNotFoundException;
}
