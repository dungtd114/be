package com.example.style_store_be.mapper;

import com.example.style_store_be.dto.request.DonHangRequest;
import com.example.style_store_be.entity.HoaDon;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DonHangMapper {
    HoaDon toHoaDon(DonHangRequest request);
}
