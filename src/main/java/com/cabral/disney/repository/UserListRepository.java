package com.cabral.disney.repository;

import com.cabral.disney.models.UserList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserListRepository extends JpaRepository<UserList,Long> {
}
