package com.cabral.disney.service.impl;

import com.cabral.disney.exception.CharacterNotFoundException;
import com.cabral.disney.exception.MovieNotFoundException;
import com.cabral.disney.exception.MovieSearchEmptyResultException;
import com.cabral.disney.mapper.MovieMapper;
import com.cabral.disney.models.Character;
import com.cabral.disney.models.Movie;
import com.cabral.disney.payload.request.MovieRequest;
import com.cabral.disney.payload.response.MovieResponse;
import com.cabral.disney.repository.CharacterRepository;
import com.cabral.disney.repository.MovieRepository;
import com.cabral.disney.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;
    private CharacterRepository characterRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, CharacterRepository characterRepository) {
        this.movieRepository = movieRepository;
        this.characterRepository = characterRepository;
    }

    @Override
    public List<MovieResponse> getAllMovies() {
        List<Movie> movies = this.movieRepository.findAll();
        List<MovieResponse> moviesResponses = movies.stream().map(MovieMapper::mapToDTO).collect(Collectors.toList());
        return moviesResponses;
    }

    @Override
    public MovieResponse getMovieById(Long id) throws MovieNotFoundException {
        Movie movie = this.movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("Movie could not be found."));

        return MovieMapper.mapToDTO(movie);
    }

    @Override
    @Transactional
    public MovieResponse createMovie(MovieRequest request) {
        Set<Character> characters = null;
        Set<Long> characterIds = request.getCharacterIds();

        if (characterIds != null) {
            characters = characterIds.stream().map(id -> {
                        try {
                            return this.characterRepository.findById(id).orElseThrow(() -> new CharacterNotFoundException("Character not found with id: " + id));
                        } catch (CharacterNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toSet());
        }

        Movie newMovie = MovieMapper.mapToEntity(request, characters);
        Movie savedMovie = movieRepository.save(newMovie);

        return MovieMapper.mapToDTO(savedMovie);
    }

    @Override
    public MovieResponse updateMovie(Long id, MovieRequest request) throws MovieNotFoundException {
        Movie movie = this.movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("Movie could not be found."));

        movie.setImage(request.getImage());
        movie.setCreationDate(request.getCreationDate());
        movie.setQualification(request.getQualification());
        movie.setTitle(request.getTitle());

        Movie updatedMovie = this.movieRepository.save(movie);

        return MovieMapper.mapToDTO(updatedMovie);
    }

    @Override
    public void deleteMovie(Long id) throws MovieNotFoundException {
        Movie movie = this.movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("Movie could not be found."));

        this.movieRepository.delete(movie);
    }

    @Override
    public List<MovieResponse> searchMovie(String name) throws MovieSearchEmptyResultException {
        List<Movie> movies = this.movieRepository.searchMovie(name);

        if (movies.isEmpty()) {
            throw new MovieSearchEmptyResultException("No movies were found under that criteria.");
        } else {
            List<MovieResponse> movieResponses = movies.stream().map(MovieMapper::mapToDTO).collect(Collectors.toList());
            return movieResponses;
        }
    }
}
