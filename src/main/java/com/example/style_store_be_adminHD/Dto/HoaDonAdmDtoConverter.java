package com.example.style_store_be_adminHD.Dto;

import com.example.style_store_be_adminHD.Entity.HoaDonAdm;

import org.springframework.stereotype.Component;

@Component
public class HoaDonAdmDtoConverter {
    public HoaDonAdmDto toDto(HoaDonAdm hoaDon) {
        if (hoaDon == null) return null;

        HoaDonAdmDto dto = new HoaDonAdmDto();
        dto.setId(hoaDon.getId());
        dto.setMa(hoaDon.getMa());
        dto.setNguoiDatHang(hoaDon.getNguoiDatHang());
        dto.setNguoiNhanHang(hoaDon.getNguoiNhanHang());
        dto.setDiaChiNhanHang(hoaDon.getDiaChiNhanHang());
        dto.setTongSoLuongSp(hoaDon.getTongSoLuongSp());
        dto.setTongTien(hoaDon.getTongTien());
        dto.setTienThue(hoaDon.getTienThue());
        dto.setNgayDat(hoaDon.getNgayDat());
        dto.setNgayNhan(hoaDon.getNgayNhan());
        dto.setNgayTao(hoaDon.getNgayTao());
        dto.setNgaySua(hoaDon.getNgaySua());
        dto.setNgayXoa(hoaDon.getNgayXoa());
        dto.setTrangThai(hoaDon.getTrangThai());
        dto.setMoTa(hoaDon.getMoTa());

        if (hoaDon.getIdNguoiTao() != null)
            dto.setTenNguoiTao(hoaDon.getIdNguoiTao().getHoTen());
        if (hoaDon.getIdKhachHang() != null)
            dto.setTenKhachHang(hoaDon.getIdKhachHang().getHoTen());
        if (hoaDon.getIdThanhToan() != null)
            dto.setTenThanhToan(hoaDon.getIdThanhToan().getTen());

        return dto;
    }
}
