package com.cabral.disney.service.impl;

import com.cabral.disney.exception.ListCreationValidationException;
import com.cabral.disney.exception.MovieNotFoundException;
import com.cabral.disney.mapper.UserListMapper;
import com.cabral.disney.models.Movie;
import com.cabral.disney.models.User;
import com.cabral.disney.models.UserList;
import com.cabral.disney.payload.request.ListRequest;
import com.cabral.disney.payload.response.ListResponse;
import com.cabral.disney.repository.MovieRepository;
import com.cabral.disney.repository.UserListRepository;
import com.cabral.disney.service.UserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserListServiceImpl implements UserListService {
    private UserListRepository userListRepository;
    private MovieRepository movieRepository;

    @Autowired
    public UserListServiceImpl(UserListRepository userListRepository, MovieRepository movieRepository) {
        this.userListRepository = userListRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public ListResponse createList(ListRequest listRequest, User user) throws ListCreationValidationException {
        Set<Movie> movies;
        Set<Long> movieIdsInRequest = listRequest.getMovieIds();

        if (movieIdsInRequest.size() < 1) {
            throw new ListCreationValidationException("List must have at least one movie");
        }

        movies = movieIdsInRequest.stream().map(id -> {
                    try {
                        return this.movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("Movie not found"));
                    } catch (MovieNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());

        UserList list = UserListMapper.mapToEntity(listRequest, user , movies);

        UserList savedList = this.userListRepository.save(list);

        return UserListMapper.mapToDTO(savedList);
    }
}
