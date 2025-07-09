package com.example.style_store_be.mapper;

import com.example.style_store_be.dto.request.UserCreationRequest;
import com.example.style_store_be.dto.response.UserResponse;
import com.example.style_store_be.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper (componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse (User user);
    void userUpdateRequest( @MappingTarget User user, UserCreationRequest request);
}
