package com.api.business_manager_api.Models;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "CUSTOMERS_CATEGORIES")
public class CustomerCategoryModel {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID customer_category_id;
    @Column(nullable = false, length = 80)
    private String name;
    @Column(nullable = false, length = 80)
    private String description;

    @OneToMany(mappedBy = "customerCategory")
    private List<CustomerModel> customerList;

    public UUID getCustomer_category_id() {
        return customer_category_id;
    }

    public void setCustomer_category_id(UUID customer_category_id) {
        this.customer_category_id = customer_category_id;
    }

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
