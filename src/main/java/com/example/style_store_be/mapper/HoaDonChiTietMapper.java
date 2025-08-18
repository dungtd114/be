package com.example.style_store_be.mapper;

import com.example.style_store_be.dto.request.DonHangRequest;
import com.example.style_store_be.dto.request.HoaDonChiTietRequest;
import com.example.style_store_be.entity.ChiTietSanPham;
import com.example.style_store_be.entity.HoaDon;
import com.example.style_store_be.entity.HoaDonCt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HoaDonChiTietMapper {
    @Mapping(target = "hoaDon", expression = "java(mapHoaDon(request.getHoaDonId()))")
    @Mapping(target = "sanPhamCt", expression = "java(mapChiTietSanPham(request.getSanPhamCtId()))")
    HoaDonCt toHoaDonCt(HoaDonChiTietRequest request);

    default HoaDon mapHoaDon(Long hoaDonId) {
        if (hoaDonId == null) return null;
        HoaDon hoaDon = new HoaDon();
        hoaDon.setId(hoaDonId);
        return hoaDon;
    }


    default ChiTietSanPham mapChiTietSanPham(Long sanPhamCtId) {
        if (sanPhamCtId == null) return null;
        ChiTietSanPham ctsp = new ChiTietSanPham();
        ctsp.setId(sanPhamCtId);
        return ctsp;
    }}
