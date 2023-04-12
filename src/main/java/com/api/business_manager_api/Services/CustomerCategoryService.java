package com.api.business_manager_api.Services;

import com.api.business_manager_api.Models.CategoryModel;
import com.api.business_manager_api.Models.CustomerCategoryModel;
import com.api.business_manager_api.Models.ProductModel;
import com.api.business_manager_api.Models.UserModel;
import com.api.business_manager_api.Repositories.CustomerCategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerCategoryService {
    final CustomerCategoryRepository customerCategoryRepository;

    public CustomerCategoryService(CustomerCategoryRepository customerCategoryRepository) {
        this.customerCategoryRepository = customerCategoryRepository;
    }

    @Transactional
    public CustomerCategoryModel save(CustomerCategoryModel customerCategoryModel) {
        return customerCategoryRepository.save(customerCategoryModel);
    }

    public boolean existsByName(String name) {
        return customerCategoryRepository.existsByName(name);
    }

    public List<CustomerCategoryModel> findAll() {
        return customerCategoryRepository.findAll();
    }

    public Optional<CustomerCategoryModel> findById(UUID id) {
        return customerCategoryRepository.findById(id);
    }

    public void delete(CustomerCategoryModel customerCategoryModel) {
        customerCategoryRepository.delete(customerCategoryModel);
    }

}
