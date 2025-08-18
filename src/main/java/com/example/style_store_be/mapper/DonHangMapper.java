package com.example.style_store_be.mapper;

import com.example.style_store_be.dto.request.DonHangRequest;
import com.example.style_store_be.dto.request.HoaDonUpdateRequest;
import com.example.style_store_be.dto.request.UserUpdateRequest;
import com.example.style_store_be.dto.response.HoaDonUDResponse;
import com.example.style_store_be.dto.response.UserResponse;
import com.example.style_store_be.entity.HoaDon;
import com.example.style_store_be.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DonHangMapper {
    HoaDon toHoaDon(DonHangRequest request);

    HoaDonUDResponse toHoaDonUDResponse (HoaDon hoaDon);


    @Mapping(target = "nguoiTao", ignore = true)
    @Mapping(target = "nguoiXuat", ignore = true)
    @Mapping(target = "thanhToan", ignore = true)
    @Mapping(target = "ma", ignore = true)
    @Mapping(target = "nguoiDatHang", ignore = true)
    @Mapping(target = "tongSoLuongSp", ignore = true)
    @Mapping(target = "tongTien", ignore = true)
    @Mapping(target = "ngayDat", ignore = true)
    @Mapping(target = "ngayNhan", ignore = true)
    @Mapping(target = "ngayTao", ignore = true)
    @Mapping(target = "ngayXoa", ignore = true)
    @Mapping(target = "trangThai", ignore = true)
    @Mapping(target = "moTa", ignore = true)
    @Mapping(target = "khachHang", ignore = true)
    @Mapping(target = "trangThaiThanhToan", ignore = true)
    @Mapping(target = "hoaDonCts", ignore = true)
    void hoaDonUdateRequest(@MappingTarget HoaDon hoaDon, HoaDonUpdateRequest request);
}
