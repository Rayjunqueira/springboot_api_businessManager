package com.api.business_manager_api.Mappers;

import com.api.business_manager_api.Dtos.OrderDto;
import com.api.business_manager_api.Models.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "order_id", ignore = true)
    OrderModel toOrderModel (OrderDto orderDto);
}
