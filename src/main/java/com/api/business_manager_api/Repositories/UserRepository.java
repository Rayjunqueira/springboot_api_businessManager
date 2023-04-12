package com.api.business_manager_api.Repositories;

import com.api.business_manager_api.Models.UserModel;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {

    boolean existsByEmail (String email);

    Optional <UserModel> findByUsername(String username);
}
