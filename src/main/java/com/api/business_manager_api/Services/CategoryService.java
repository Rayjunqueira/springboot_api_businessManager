package com.api.business_manager_api.Services;

import com.api.business_manager_api.Models.CategoryModel;
import com.api.business_manager_api.Models.ProductModel;
import com.api.business_manager_api.Repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public CategoryModel save(CategoryModel categoryModel) {
        return categoryRepository.save(categoryModel);
    }

    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    public Optional<CategoryModel> findById(UUID id) {
        return categoryRepository.findById(id);
    }
    public List<CategoryModel> findAll() {
        return categoryRepository.findAll();
    }

    public void delete(CategoryModel categoryModel) {
        categoryRepository.delete(categoryModel);
    }
}
