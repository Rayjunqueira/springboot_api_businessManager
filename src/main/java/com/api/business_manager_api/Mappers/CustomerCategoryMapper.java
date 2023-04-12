package com.api.business_manager_api.Mappers;

import com.api.business_manager_api.Dtos.CustomerCategoryDto;
import com.api.business_manager_api.Models.CustomerCategoryModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerCategoryMapper {

    @Mapping(target = "customer_category_id", ignore = true)
    CustomerCategoryModel toCustomerCategoryModel(CustomerCategoryDto customerCategoryDto);
}
