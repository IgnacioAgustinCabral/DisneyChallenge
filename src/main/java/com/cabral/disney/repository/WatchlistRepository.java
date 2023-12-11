package com.cabral.disney.repository;

import com.cabral.disney.models.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    List<Watchlist> findAllByUser_Id(Long userId);

    Optional<Watchlist> findByMovie_IdAndUser_Id(Long movieId, Long userId);
}
