package com.cabral.disney.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterRequest {

    @NotBlank(message = "name is required.")
    @Size(min = 1, max = 300, message = "the name must be between {min} and {max} characters long.")
    private String name;

    @NotNull(message = "age is required.")
    @Min(value = 1, message = "age must be greater or equal than {value}")
    private Integer age;

    @NotNull(message = "weight is required.")
    @DecimalMin(value = "0.1", message = "weight must be greater or equal than {value}")
    private Double weight;

    @NotBlank(message = "history is required.")
    @Size(min = 1, max = 2000, message = "the history must be between {min} and {max} characters long.")
    private String history;

    private String image;

    private Set<Long> movieIds;
}
