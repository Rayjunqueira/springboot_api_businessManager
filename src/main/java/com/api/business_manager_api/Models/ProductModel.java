package com.api.business_manager_api.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "PRODUCTS")
public class ProductModel {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID product_id;

    @Column(nullable = false, length = 80)
    private String product;
    @Column(nullable = true, length = 200)
    private String image_url;
    @Column(nullable = true, length = 80)
    private String brand;
    @Column(nullable = true, length = 80)
    private String description;
    @Column(nullable = true, length = 80)
    private Float price;

    @Column(nullable = true, length = 80)
    private Float extraPrice;

    @Column(nullable = false, length = 80)
    private Integer stock;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryModel productCategory;

    public UUID getProduct_id() {
        return product_id;
    }

    public void setProduct_id(UUID product_id) {
        this.product_id = product_id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public CategoryModel getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(CategoryModel productCategory) {
        this.productCategory = productCategory;
    }

    public Float getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(Float extraPrice) {
        this.extraPrice = extraPrice;
    }
}
