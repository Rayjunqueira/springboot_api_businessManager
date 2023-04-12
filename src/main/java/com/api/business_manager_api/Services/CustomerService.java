package com.api.business_manager_api.Services;

import com.api.business_manager_api.Models.CustomerModel;
import com.api.business_manager_api.Models.ProductModel;
import com.api.business_manager_api.Repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public boolean existsByName(String name) {
        return customerRepository.existsByName(name);
    }

    public CustomerModel save(CustomerModel customerModel) {
        return customerRepository.save(customerModel);
    }

    public List<CustomerModel> findAll() {
        return customerRepository.findAll();
    }

    public Optional<CustomerModel> findById(UUID id) {
        return customerRepository.findById(id);
    }

    public void delete(CustomerModel customerModel) {
        customerRepository.delete(customerModel);
    }
}
