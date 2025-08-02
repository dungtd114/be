package com.example.style_store_be.mapper;

import com.example.style_store_be.dto.request.DiaChiNhanRequest;
import com.example.style_store_be.dto.request.DiaChiNhanUpdateRequest;
import com.example.style_store_be.dto.request.UserCreationRequest;
import com.example.style_store_be.dto.request.UserUpdateRequest;
import com.example.style_store_be.dto.response.DiaChiNhanResponse;
import com.example.style_store_be.dto.response.UserResponse;
import com.example.style_store_be.entity.DiaChiNhan;
import com.example.style_store_be.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper (componentModel = "spring")
public interface DiaChiNhanMapper {
    DiaChiNhan toDiaChiNhan(DiaChiNhanRequest request);
    DiaChiNhanResponse toDiaChiNhanResponse (DiaChiNhan diaChiNhan);

    @Mapping(target = "ngayTao", ignore = true)
    @Mapping(target = "user", ignore = true)
    void diaChiNhanUpdateRequest( @MappingTarget DiaChiNhan diaChiNhan, DiaChiNhanUpdateRequest request);
}
