package com.api.business_manager_api.Dtos;

import com.api.business_manager_api.Models.CategoryModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {

    private UUID id;

    @NotBlank
    private String product;

    private String image_url;

    @NotBlank
    private String description;

    @DecimalMin(value = "0.01", message = "Price must be greater than zero!")
    private Float price;

    @DecimalMin(value = "0.01", message = "Extra Price must be greater than zero!")
    private Float extraPrice;

    @Positive(message = "Stock must be greater than zero")
    private Integer stock;

    @NotNull
    private CategoryModel productCategory;

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
