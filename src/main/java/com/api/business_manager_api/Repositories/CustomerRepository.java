package com.api.business_manager_api.Repositories;

import com.api.business_manager_api.Models.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerModel, UUID> {
    boolean existsByName(String customer);
}
