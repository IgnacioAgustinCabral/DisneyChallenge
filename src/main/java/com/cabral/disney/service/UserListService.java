package com.cabral.disney.service;

import com.cabral.disney.exception.ListCreationValidationException;
import com.cabral.disney.exception.UsernameNotFoundException;
import com.cabral.disney.models.User;
import com.cabral.disney.payload.request.ListRequest;
import com.cabral.disney.payload.response.ListResponse;

import java.util.List;

public interface UserListService {
    ListResponse createList(ListRequest listRequest, User user) throws ListCreationValidationException;

    List<ListResponse> getListForUser(User user);

    List<ListResponse> getPublicListsByUsername(String username) throws UsernameNotFoundException;
}
