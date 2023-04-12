package com.api.business_manager_api.Mappers;

import com.api.business_manager_api.Dtos.ProductDto;
import com.api.business_manager_api.Models.ProductModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "product_id", ignore = true)
    ProductModel toProductModel (ProductDto productDto);
}
