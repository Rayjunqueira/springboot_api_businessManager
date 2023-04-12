package com.api.business_manager_api.Controllers;

import com.api.business_manager_api.Dtos.CustomerDto;
import com.api.business_manager_api.Mappers.CustomerMapper;
import com.api.business_manager_api.Models.CustomerCategoryModel;
import com.api.business_manager_api.Models.CustomerModel;
import com.api.business_manager_api.Services.CustomerCategoryService;
import com.api.business_manager_api.Services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/customer")
public class CustomerController {

    final CustomerService customerService;
    final CustomerCategoryService customerCategoryService;
    final CustomerMapper customerMapper;

    public CustomerController(CustomerService customerService, CustomerCategoryService customerCategoryService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerCategoryService = customerCategoryService;
        this.customerMapper = customerMapper;
    }
    @PostMapping
    public ResponseEntity<Object> saveCustomer (@RequestBody @Valid CustomerDto customerDto) {
        try {
            if (customerService.existsByName(customerDto.getName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Customer already exists!");
            }
            CustomerModel customerModel = customerMapper.toCustomerModel(customerDto);
            CustomerCategoryModel customerCategoryModel = customerCategoryService.findById(customerDto.getCustomerCategory().getCustomer_category_id())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            customerModel.setCustomerCategory(customerCategoryModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(customerModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot create customer. Check if the fields sent in your request are correct.");
        }
    }
    @GetMapping
    public ResponseEntity<List<CustomerModel>> getAllCustomers() {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneCustomer(@PathVariable(value = "id")UUID id) {
        Optional<CustomerModel> customerModelOptional = customerService.findById(id);
        if (!customerModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(customerModelOptional.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCustomer (@PathVariable(value = "id") UUID id,
                                                @RequestBody @Valid CustomerDto customerDto) {
        Optional<CustomerModel> customerModelOptional = customerService.findById(id);
        if (!customerModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found!");
        }
        CustomerModel customerModel = customerModelOptional.get();
        customerModel.setName(customerDto.getName());
        customerModel.setDescription(customerDto.getDescription());
        customerModel.setCellphone(customerDto.getCellphone());
        customerModel.setEmail(customerDto.getEmail());
        customerModel.setNote(customerDto.getNote());
        customerModel.setCustomerCategory(customerDto.getCustomerCategory());

        return ResponseEntity.status(HttpStatus.OK).body(customerService.save(customerModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable(value = "id") UUID id) {
        Optional<CustomerModel> customerModelOptional = customerService.findById(id);
        if (!customerModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
        }
        customerService.delete(customerModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Customer deleted succesfully!");
    }


}
