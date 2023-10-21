package com.cabral.disney.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieRequest {
    @NotBlank(message = "name is required.")
    @Size(min = 1, max = 50, message = "the title must be between {min} and {max} characters long.")
    private String title;

    @NotNull(message = "The creation date is required.")
    @Past(message = "The creation date must be in the past.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    @NotBlank
    @Size(min = 100, max = 400)
    private String synopsis;

    private Set<Long> characterIds;

    private Set<Long> genreIds;
}
