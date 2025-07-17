package com.example.style_store_be.mapper;

import com.example.style_store_be.dto.request.ApDungGGRequest;
import com.example.style_store_be.dto.request.ApDungGGUpdateRequest;
import com.example.style_store_be.dto.request.GiamGiaRequest;
import com.example.style_store_be.dto.request.GiamGiaUpdateRequest;
import com.example.style_store_be.entity.GiamGia;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ApDungGGMapper {
    GiamGia toApDungGiamGia(ApDungGGRequest request);
    void apDungGGUpdateRequest(@MappingTarget GiamGia giamGia, ApDungGGUpdateRequest request);
}
