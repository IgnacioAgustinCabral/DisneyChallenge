package com.cabral.disney.payload.response;

import com.cabral.disney.models.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WatchedMovieResponse {
    private Movie movie;
    private Boolean watchedStatus;
}
