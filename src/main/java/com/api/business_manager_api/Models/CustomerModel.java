package com.api.business_manager_api.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "CUSTOMERS")
public class CustomerModel {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID customer_id;
    @Column(nullable = false, length = 80)
    private String name;
    @Column(nullable = true, length = 80)
    private String description;
    @Column(nullable = true, length = 80)
    private String cellphone;
    @Column(nullable = true, length = 80)
    private String email;

    @Column(nullable = true, length = 100 )
    private String note;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_category_id")
    private CustomerCategoryModel customerCategory;

    public UUID getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(UUID customer_id) {
        this.customer_id = customer_id;
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
