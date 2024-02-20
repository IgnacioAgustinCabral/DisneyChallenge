package com.cabral.disney.models;

import com.cabral.disney.models.composite_key.WatchedMovieKey;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(exclude = {"user", "movie"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "watched_movies")
@IdClass(WatchedMovieKey.class)
public class WatchedMovie {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Column(name = "seen_at", nullable = false, updatable = false)
    private LocalDateTime seenAt;
}
