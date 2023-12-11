package com.cabral.disney.models;

import com.cabral.disney.models.composite_key.WatchlistKey;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@IdClass(WatchlistKey.class)
public class Watchlist {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Column(name = "added_at")
    private LocalDateTime addedAt;
}
