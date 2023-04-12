package com.api.business_manager_api.Repositories;

import com.api.business_manager_api.Models.CustomerCategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerCategoryRepository  extends JpaRepository<CustomerCategoryModel, UUID> {
    boolean existsByName (String name);
}
