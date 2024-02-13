package com.cabral.disney.service.impl;

import com.cabral.disney.exception.EmptyWatchlistException;
import com.cabral.disney.exception.MovieNotFoundException;
import com.cabral.disney.mapper.WatchlistMapper;
import com.cabral.disney.models.Movie;
import com.cabral.disney.models.User;
import com.cabral.disney.models.Watchlist;
import com.cabral.disney.payload.response.WatchlistResponse;
import com.cabral.disney.repository.MovieRepository;
import com.cabral.disney.repository.UserRepository;
import com.cabral.disney.repository.WatchlistRepository;
import com.cabral.disney.service.WatchlistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WatchlistServiceImpl implements WatchlistService {

    private WatchlistRepository watchlistRepository;
    private MovieRepository movieRepository;
    private UserRepository userRepository;

    @Autowired
    public WatchlistServiceImpl(WatchlistRepository watchlistRepository, MovieRepository movieRepository, UserRepository userRepository) {
        this.watchlistRepository = watchlistRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<WatchlistResponse> getAllMoviesInWatchlist(Long userId) throws EmptyWatchlistException {
        List<Watchlist> watchlists = this.watchlistRepository.findAllByUser_Id(userId);

        if (!watchlists.isEmpty()) {

            List<WatchlistResponse> watchlistResponses = watchlists.stream()
                    .map(watchlist -> WatchlistMapper.mapToDTO(watchlist))
                    .collect(Collectors.toList());

            return watchlistResponses;

        } else {
            throw new EmptyWatchlistException("No films in watchlist yet");
        }
    }

    @Override
    public String removeMovieFromWatchlist(Long movieId, String username) throws MovieNotFoundException {
        User user = this.userRepository.findByUsername(username).get();
        Optional<Watchlist> watchlistOptional = this.watchlistRepository.findByMovie_IdAndUser_Id(movieId, user.getId());

        if (watchlistOptional.isPresent()) {
            this.watchlistRepository.delete(watchlistOptional.get());
            return watchlistOptional.get().getMovie().getTitle();
        } else {
            throw new MovieNotFoundException("Movie not found in the watchlist");
        }
    }

    @Override
    public String addMovieToWatchlist(Long movieId, String username) throws MovieNotFoundException {
        Movie movie = this.movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found"));

        User user = this.userRepository.findByUsername(username).get();

        Watchlist savedMovieToWatchlist = this.watchlistRepository.save(WatchlistMapper.mapToEntity(movie, user));

        return savedMovieToWatchlist.getMovie().getTitle();
    }

}
