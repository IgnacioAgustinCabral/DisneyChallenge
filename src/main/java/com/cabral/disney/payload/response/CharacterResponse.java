package com.cabral.disney.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterResponse {
    private Long id;

    private String name;

    private Integer age;

    private Double weight;

    private String history;

    private String image;

    private Set<Long> movieIds;
}
