package com.cabral.disney.payload.response;

import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListResponse {
    private Long id;
    private String name;
    private Boolean visibility;
    private Set<Long> movieIds;
}
