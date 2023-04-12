package com.api.business_manager_api.Dtos;

import com.api.business_manager_api.Models.CategoryModel;
import com.api.business_manager_api.Models.CustomerCategoryModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CustomerDto {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String cellphone;

    @NotBlank
    private String email;

    @NotBlank
    private String note;

    @NotNull
    private CustomerCategoryModel customerCategory;

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

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public CustomerCategoryModel getCustomerCategory() {
        return customerCategory;
    }

    public void setCustomerCategory(CustomerCategoryModel customerCategory) {
        this.customerCategory = customerCategory;
    }
}
