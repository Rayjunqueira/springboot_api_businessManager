package com.api.business_manager_api.Dtos;

import com.api.business_manager_api.Models.CustomerModel;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class CustomerCategoryDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private List<CustomerModel> customerList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CustomerModel> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<CustomerModel> customerList) {
        this.customerList = customerList;
    }
}
