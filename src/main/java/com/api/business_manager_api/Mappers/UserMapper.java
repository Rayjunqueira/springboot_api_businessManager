package com.api.business_manager_api.Mappers;

import com.api.business_manager_api.Dtos.UserDto;
import com.api.business_manager_api.Models.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "user_id", ignore = true)
    UserModel toUserModel (UserDto userDto);
}
