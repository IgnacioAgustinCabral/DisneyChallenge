package com.cabral.disney.service.impl;

import com.cabral.disney.exception.EmptyWatchlistException;
import com.cabral.disney.mapper.WatchlistMapper;
import com.cabral.disney.models.Watchlist;
import com.cabral.disney.payload.response.WatchlistResponse;
import com.cabral.disney.repository.WatchlistRepository;
import com.cabral.disney.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WatchlistServiceImpl implements WatchlistService {

    private WatchlistRepository watchlistRepository;

    @Autowired
    public WatchlistServiceImpl(WatchlistRepository watchlistRepository) {
        this.watchlistRepository = watchlistRepository;
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

}
