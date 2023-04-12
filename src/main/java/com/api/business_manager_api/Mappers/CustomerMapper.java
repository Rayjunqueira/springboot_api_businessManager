package com.api.business_manager_api.Mappers;

import com.api.business_manager_api.Dtos.CustomerDto;
import com.api.business_manager_api.Models.CustomerModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "customer_id", ignore = true)
    CustomerModel toCustomerModel (CustomerDto customerDto);
}
