package com.cabral.disney.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenreRequest {

    @NotBlank
    @Size(min = 3, max = 15, message = "The name of the genre must be between {min} and {max}")
    private String name;
}
