package com.api.business_manager_api.Controllers;

import com.api.business_manager_api.Dtos.CustomerCategoryDto;
import com.api.business_manager_api.Mappers.CustomerCategoryMapper;
import com.api.business_manager_api.Models.CustomerCategoryModel;
import com.api.business_manager_api.Services.CustomerCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/customercategory")
public class CustomerCategoryController {
    final CustomerCategoryService customerCategoryService;

    final CustomerCategoryMapper customerCategoryMapper;

    public CustomerCategoryController(CustomerCategoryService customerCategoryService, CustomerCategoryMapper customerCategoryMapper) {
        this.customerCategoryService = customerCategoryService;
        this.customerCategoryMapper = customerCategoryMapper;
    }

    @PostMapping
    public ResponseEntity<Object> saveCustomerCategory(@RequestBody @Valid CustomerCategoryDto customerCategoryDto) {
        try {
            if (customerCategoryService.existsByName(customerCategoryDto.getName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Customer category already exists!");
            }
            CustomerCategoryModel customerCategoryModel = customerCategoryMapper.toCustomerCategoryModel(customerCategoryDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(customerCategoryService.save(customerCategoryModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot create customer category. Check if the fields sent in your request are correct.");
        }
    }
    @GetMapping
    public ResponseEntity<List<CustomerCategoryModel>> getAllCustomerCategories() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(customerCategoryService.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneCustomerCategory(@PathVariable(value = "id") UUID id) {
        try {
            Optional<CustomerCategoryModel> customerCategoryModelOptional  = customerCategoryService.findById(id);
            if (!customerCategoryModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer category not found");
            }
            return ResponseEntity.status(HttpStatus.OK).body(customerCategoryModelOptional.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The customer category could not be found. Please check if the sent ID is correct.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCustomerCategory(@PathVariable(value = "id") UUID id,
                                                 @RequestBody @Valid CustomerCategoryDto customerCategoryDto) {
        try {
            Optional<CustomerCategoryModel> customerCategoryModelOptional = customerCategoryService.findById(id);
            if (!customerCategoryModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer category not found!");
            }
            CustomerCategoryModel customerCategoryModel = customerCategoryModelOptional.get();
            customerCategoryModel.setName(customerCategoryDto.getName());
            customerCategoryModel.setDescription(customerCategoryDto.getDescription());
            customerCategoryModel.setCustomerList(customerCategoryDto.getCustomerList());


            return ResponseEntity.status(HttpStatus.OK).body(customerCategoryService.save(customerCategoryModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The customer category could not be found. Please check if the sent ID is correct.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomerCategory(@PathVariable(value = "id") UUID id) {
        try {
            Optional<CustomerCategoryModel> customerCategoryModelOptional = customerCategoryService.findById(id);
            if (!customerCategoryModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer category not found!");
            }
            customerCategoryService.delete(customerCategoryModelOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("Customer category deleted succesfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Customer category could not be found. Please check if the sent ID is correct.");
        }
    }

}

