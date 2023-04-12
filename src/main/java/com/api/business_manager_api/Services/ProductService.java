package com.api.business_manager_api.Services;

import com.api.business_manager_api.Models.CategoryModel;
import com.api.business_manager_api.Models.ProductModel;
import com.api.business_manager_api.Repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductModel save(ProductModel productModel) {
        return productRepository.save(productModel);
    }

    public boolean existsByProduct(String product) {
        return productRepository.existsByProduct(product);
    }
    public List<ProductModel> findAll() {
        return productRepository.findAll();
    }

    public Optional<ProductModel> findById(UUID id) {
        return productRepository.findById(id);
    }

    public void delete(ProductModel productModel) {
        productRepository.delete(productModel);
    }
}
