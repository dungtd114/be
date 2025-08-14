package com.example.style_store_be.mapper;

import com.example.style_store_be.dto.request.ApDungGGRequest;
import com.example.style_store_be.dto.request.ApDungGGUpdateRequest;
import com.example.style_store_be.dto.request.GiamGiaRequest;
import com.example.style_store_be.dto.request.GiamGiaUpdateRequest;
import com.example.style_store_be.dto.response.GiamGiaResponse;
import com.example.style_store_be.dto.response.UserResponse;
import com.example.style_store_be.entity.GiamGia;
import com.example.style_store_be.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ApDungGGMapper {
    GiamGia toApDungGiamGia(ApDungGGRequest request);

    @Mapping(target = "idChiTietSanPham", ignore = true) // Bỏ qua, xử lý trong service
    @Mapping(target = "idSanPham", ignore = true)
    GiamGiaResponse toGiamGiaResponse (GiamGia giamGia);

    @Mapping(target = "ma", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "nguoiTao", ignore = true)
    @Mapping(target = "ngayTao", ignore = true)
    @Mapping(target = "trangThai", ignore = true)
    @Mapping(target = "soLuong", ignore = true)
    void apDungGGUpdateRequest(@MappingTarget GiamGia giamGia, ApDungGGUpdateRequest request);
}
