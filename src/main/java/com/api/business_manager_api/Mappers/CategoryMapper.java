package com.api.business_manager_api.Mappers;

import com.api.business_manager_api.Dtos.CategoryDto;
import com.api.business_manager_api.Models.CategoryModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "category_id", ignore = true)
    CategoryModel toCategoryModel(CategoryDto categoryDto);
}
