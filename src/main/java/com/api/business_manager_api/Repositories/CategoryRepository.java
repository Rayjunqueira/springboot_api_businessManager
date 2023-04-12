package com.api.business_manager_api.Repositories;

import com.api.business_manager_api.Models.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, UUID> {

    boolean existsByName (String name);
}
