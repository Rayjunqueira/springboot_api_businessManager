package com.api.business_manager_api.Dtos;

import com.api.business_manager_api.Models.CustomerModel;
import com.api.business_manager_api.Models.ProductModel;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class OrderDto {
    @NotNull
    private CustomerModel customer;
    @NotNull
    private List<ProductModel> products;
    @NotNull

    private Float totalAmount;

    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }
}
