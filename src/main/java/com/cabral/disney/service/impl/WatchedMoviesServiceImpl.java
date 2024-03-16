package com.cabral.disney.service.impl;

import com.cabral.disney.exception.MovieNotFoundException;
import com.cabral.disney.mapper.WatchedMovieMapper;
import com.cabral.disney.models.Movie;
import com.cabral.disney.models.User;
import com.cabral.disney.models.WatchedMovie;
import com.cabral.disney.payload.response.WatchedMovieResponse;
import com.cabral.disney.repository.MovieRepository;
import com.cabral.disney.repository.UserRepository;
import com.cabral.disney.repository.WatchedMoviesRepository;
import com.cabral.disney.service.WatchedMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WatchedMoviesServiceImpl implements WatchedMovieService {
    private UserRepository userRepository;
    private MovieRepository movieRepository;
    private WatchedMoviesRepository watchedMoviesRepository;

    @Autowired
    public WatchedMoviesServiceImpl(UserRepository userRepository, MovieRepository movieRepository, WatchedMoviesRepository watchedMoviesRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.watchedMoviesRepository = watchedMoviesRepository;
    }

    @Override
    public WatchedMovieResponse addToWatchedMovies(String username, Long movieId) throws MovieNotFoundException {

        Movie movie = this.movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found"));
        User user = this.userRepository.findByUsername(username).get();
        WatchedMovie savedWatchedMovie = this.watchedMoviesRepository.save(WatchedMovieMapper.mapToEntity(user, movie));
        return WatchedMovieMapper.mapToDTO(savedWatchedMovie.getMovie(), Boolean.TRUE);
    }

    @Override
    public WatchedMovieResponse removeFromWatchedMovies(String username, Long movieId) throws MovieNotFoundException {
        Movie movie = this.movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found"));
        User user = this.userRepository.findByUsername(username).get();

        Optional<WatchedMovie> watchedMovieOptional = this.watchedMoviesRepository.findByMovie_IdAndUser_Id(movie.getId(), user.getId());

        if (watchedMovieOptional.isPresent()) {
            this.watchedMoviesRepository.delete(watchedMovieOptional.get());
            return WatchedMovieMapper.mapToDTO(watchedMovieOptional.get().getMovie(), Boolean.FALSE);
        } else {
            throw new MovieNotFoundException("You haven't seen this movie yet");
        }
    }
}
