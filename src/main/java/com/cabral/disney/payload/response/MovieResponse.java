package com.cabral.disney.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieResponse {
    private Long id;
    private String title;
    private LocalDate creationDate;
    private String synopsis;
    private byte[] image;
    private Set<Long> characterIds;
    private Set<Long> genreIds;
}
