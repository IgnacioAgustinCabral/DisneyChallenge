package com.cabral.disney.service.impl;

import com.cabral.disney.exception.PersonajeNotFoundException;
import com.cabral.disney.models.Movie;
import com.cabral.disney.exception.MovieNotFoundException;
import com.cabral.disney.exception.MovieSearchEmptyResultException;
import com.cabral.disney.mapper.MovieMapper;
import com.cabral.disney.models.Personaje;
import com.cabral.disney.payload.request.MovieRequest;
import com.cabral.disney.payload.response.MovieResponse;
import com.cabral.disney.repository.MovieRepository;
import com.cabral.disney.repository.PersonajeRepository;
import com.cabral.disney.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;
    private PersonajeRepository personajeRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, PersonajeRepository personajeRepository) {
        this.movieRepository = movieRepository;
        this.personajeRepository = personajeRepository;
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
    public MovieResponse createMovie(MovieRequest request){
        Set<Personaje> personajes = new HashSet<>();
        Set<Long> personajesIds = request.getPersonajes();

        if (!personajesIds.isEmpty()) {

            personajesIds.stream().map(id -> {
                        try {
                            return this.personajeRepository.findById(id).orElseThrow(() -> new PersonajeNotFoundException("Personaje not found with id: " + id));
                        } catch (PersonajeNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .forEach(personajes::add);
        }
//        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
//        personajes.stream().forEach(personaje -> System.out.println(personaje));
        Movie newMovie = this.movieRepository.save(MovieMapper.mapToEntity(request, personajes));
//        System.out.println(newMovie);

        return MovieMapper.mapToDTO(newMovie);
    }

    @Override
    public MovieResponse updateMovie(Long id, MovieRequest request) throws MovieNotFoundException {
        Movie movie = this.movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("Movie could not be found."));

        movie.setImagen(request.getImagen());
        movie.setFecha_creacion(request.getFecha_creacion());
        movie.setCalificacion(request.getCalificacion());
        movie.setTitulo(request.getTitulo());

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
