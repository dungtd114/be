package com.example.style_store_be_adminHD.Dto;

import com.example.style_store_be_adminHD.Entity.HoaDonAdm;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;
@Component
public class HoaDonAdmDtoConverter {
//    public HoaDonAdmDto toDto(HoaDonAdm hoaDon) {
//        if (hoaDon == null) return null;
//
//        HoaDonAdmDto dto = new HoaDonAdmDto();
//        dto.setId(hoaDon.getId());
//        dto.setMa(hoaDon.getMa());
//        dto.setNguoiDatHang(hoaDon.getNguoiDatHang());
//        dto.setNguoiNhanHang(hoaDon.getNguoiNhanHang());
//        dto.setDiaChiNhanHang(hoaDon.getDiaChiNhanHang());
//        dto.setTongSoLuongSp(hoaDon.getTongSoLuongSp());
//        dto.setTongTien(hoaDon.getTongTien());
//        dto.setTienThue(hoaDon.getTienThue());
//        dto.setNgayDat(hoaDon.getNgayDat());
//        dto.setNgayNhan(hoaDon.getNgayNhan());
//        dto.setNgayTao(hoaDon.getNgayTao());
//        dto.setNgaySua(hoaDon.getNgaySua());
//        dto.setNgayXoa(hoaDon.getNgayXoa());
//        dto.setTrangThai(hoaDon.getTrangThai());
//        dto.setMoTa(hoaDon.getMoTa());
//
//        if (hoaDon.getIdNguoiTao() != null)
//            dto.setTenNguoiTao(hoaDon.getIdNguoiTao().getHoTen());
//        if (hoaDon.getIdKhachHang() != null)
//            dto.setTenKhachHang(hoaDon.getIdKhachHang().getHoTen());
//        if (hoaDon.getIdThanhToan() != null)
//            dto.setTenThanhToan(hoaDon.getIdThanhToan().getTen());
//
//        return dto;
//    }
    private final HoaDonCtAdmDtoConverter hoaDonCtAdmDtoConverter; // Inject Converter cho chi tiết hóa đơn

    // Constructor để Spring tự động inject HoaDonCtAdmDtoConverter
    public HoaDonAdmDtoConverter(HoaDonCtAdmDtoConverter hoaDonCtAdmDtoConverter) {
        this.hoaDonCtAdmDtoConverter = hoaDonCtAdmDtoConverter;
    }

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

        /**
         * Ánh xạ danh sách chi tiết hóa đơn từ Entity sang DTO.
         * Kiểm tra `hoaDon.getChiTietHoaDon()` khác null và không rỗng trước khi ánh xạ
         * để tránh lỗi NullPointerException và chỉ ánh xạ khi có dữ liệu.
         * Sử dụng stream().map().collect() để chuyển đổi từng HoaDonCtAdm thành HoaDonCtAdmDto.
         */
        if (hoaDon.getChiTietHoaDon() != null && !hoaDon.getChiTietHoaDon().isEmpty()) {
            dto.setChiTietHoaDon(
                    hoaDon.getChiTietHoaDon().stream()
                            .map(hoaDonCtAdmDtoConverter::toDto) // Sử dụng converter của chi tiết hóa đơn
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }
}
