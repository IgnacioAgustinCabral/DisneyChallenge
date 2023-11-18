package com.cabral.disney.repository;

import com.cabral.disney.models.UserList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserListRepository extends JpaRepository<UserList, Long> {
    List<UserList> findUserListByUser_Id(Long id);

    List<UserList> findUserListByUser_IdAndIsPublicIsTrue(Long id);

    Optional<UserList> findUserListByNameAndUser_Id(String listName, Long id);

    Boolean existsByName(String name);
}
