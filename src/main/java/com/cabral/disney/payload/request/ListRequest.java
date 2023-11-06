package com.cabral.disney.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListRequest {

    @NotBlank
    @Size(min = 5, max = 100, message = "The name of the list must be between {min} and {max} characters")
    private String name;

    @NotNull
    private Boolean isPublic;

    @NotNull
    private Set<Long> movieIds;
}
