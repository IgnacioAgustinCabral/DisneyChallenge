package com.cabral.disney.mapper;

import com.cabral.disney.models.Movie;
import com.cabral.disney.models.User;
import com.cabral.disney.models.UserList;
import com.cabral.disney.payload.request.ListRequest;
import com.cabral.disney.payload.response.ListResponse;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserListMapper {
    public static UserList mapToEntity(ListRequest listRequest, User user, Set<Movie> movies) {
        return UserList.builder()
                .name(listRequest.getName())
                .isPublic(listRequest.getIsPublic())
                .user(user)
                .moviesInList(movies)
                .build();
    }

    public static ListResponse mapToDTO(UserList savedList) {
        return ListResponse.builder()
                .id(savedList.getId())
                .name(savedList.getName())
                .visibility(savedList.getIsPublic())
                .movieIds(savedList.getMoviesInList().stream()
                        .map(movie -> movie.getId())
                        .collect(Collectors.toSet()))
                .build();
    }
}
