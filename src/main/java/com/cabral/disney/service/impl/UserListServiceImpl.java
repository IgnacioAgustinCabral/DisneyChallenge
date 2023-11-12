package com.cabral.disney.service.impl;

import com.cabral.disney.exception.ListCreationValidationException;
import com.cabral.disney.exception.ListNotFoundException;
import com.cabral.disney.exception.MovieNotFoundException;
import com.cabral.disney.exception.UsernameNotFoundException;
import com.cabral.disney.mapper.UserListMapper;
import com.cabral.disney.models.Movie;
import com.cabral.disney.models.User;
import com.cabral.disney.models.UserList;
import com.cabral.disney.payload.request.ListRequest;
import com.cabral.disney.payload.response.ListResponse;
import com.cabral.disney.repository.MovieRepository;
import com.cabral.disney.repository.UserListRepository;
import com.cabral.disney.repository.UserRepository;
import com.cabral.disney.service.UserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserListServiceImpl implements UserListService {
    private UserListRepository userListRepository;
    private MovieRepository movieRepository;
    private UserRepository userRepository;

    @Autowired
    public UserListServiceImpl(UserListRepository userListRepository, MovieRepository movieRepository, UserRepository userRepository) {
        this.userListRepository = userListRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
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

        UserList list = UserListMapper.mapToEntity(listRequest, user, movies);

        UserList savedList = this.userListRepository.save(list);

        return UserListMapper.mapToDTO(savedList);
    }

    @Override
    public List<ListResponse> getListForUser(User user) {
        List<UserList> userLists = this.userListRepository.findUserListByUser_Id(user.getId());

        List<ListResponse> lists = userLists.stream()
                .map(list -> UserListMapper.mapToDTO(list))
                .collect(Collectors.toList());

        return lists;
    }

    @Override
    public List<ListResponse> getPublicListsByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user " + username + " not found"));

        List<UserList> publicLists = this.userListRepository.findUserListByUser_IdAndIsPublicIsTrue(user.getId());

        List<ListResponse> lists = publicLists.stream()
                .map(list -> UserListMapper.mapToDTO(list))
                .collect(Collectors.toList());

        return lists;
    }

    @Override
    public String deleteList(String username, String listName) throws ListNotFoundException {
        User user = this.userRepository.findByUsername(username).get();

        UserList list = this.userListRepository.findUserListByNameAndUser_Id(listName, user.getId()).orElseThrow(() -> new ListNotFoundException("This list doesn't exist"));

        this.userListRepository.delete(list);

        return list.getName();
    }
}
