package com.api.business_manager_api.Repositories;

import com.api.business_manager_api.Models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {
    boolean existsByProduct (String product);
}
