package com.api.business_manager_api.Controllers;

import com.api.business_manager_api.Dtos.CategoryDto;
import com.api.business_manager_api.Dtos.ProductDto;
import com.api.business_manager_api.Mappers.CategoryMapper;
import com.api.business_manager_api.Models.CategoryModel;
import com.api.business_manager_api.Models.ProductModel;
import com.api.business_manager_api.Services.CategoryService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/category")
public class CategoryController {

    final CategoryService categoryService;
    final CategoryMapper categoryMapper;


    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @PostMapping
    public ResponseEntity<Object> saveCategory(@RequestBody @Valid CategoryDto categoryDto) {
        try {
            if (categoryService.existsByName(categoryDto.getName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Category already exists!");
            }
            CategoryModel categoryModel = categoryMapper.toCategoryModel(categoryDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(categoryModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot create category. Check if the fields sent in your request are correct.");
        }
    }

    @GetMapping
    public ResponseEntity<List<CategoryModel>> getAllCategories() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneCategory(@PathVariable(value = "id")UUID id) {
        try {
            Optional<CategoryModel> categoryModelOptional = categoryService.findById(id);
            if (!categoryModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
            }
            return ResponseEntity.status(HttpStatus.OK).body(categoryModelOptional.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The category could not be found. Please check if the sent ID is correct.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable(value = "id") UUID id,
                                                @RequestBody @Valid CategoryDto categoryDto) {
        try {
            Optional<CategoryModel> categoryModelOptional = categoryService.findById(id);
            if (!categoryModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found!");
            }
            CategoryModel categoryModel = categoryModelOptional.get();
            categoryModel.setName(categoryDto.getName());
            categoryModel.setDescription(categoryDto.getDescription());
            categoryModel.setProductList(categoryDto.getProductList());

            return ResponseEntity.status(HttpStatus.OK).body(categoryService.save(categoryModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The category could not be found. Please check if the sent ID is correct.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable(value = "id") UUID id) {
        try {
            Optional<CategoryModel> categoryModelOptional = categoryService.findById(id);
            if (!categoryModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found!");
            }
            categoryService.delete(categoryModelOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("Category deleted succesfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The category could not be found. Please check if the sent ID is correct.");
        }
    }

}


