package com.example.style_store_be_adminHD.Dto;

import com.example.style_store_be_adminHD.Entity.HoaDonCtAdm;
import com.example.style_store_be_adminHD.Entity.ChiTietSanPhamAdm;
import org.springframework.stereotype.Component;

@Component
public class HoaDonCtAdmDtoConverter {
    public HoaDonCtAdmDto toDto(HoaDonCtAdm hoaDonCt) {
        if (hoaDonCt == null) {
            return null;
        }

        HoaDonCtAdmDto dto = new HoaDonCtAdmDto();
        dto.setId(hoaDonCt.getId());
        dto.setTenSanPham(hoaDonCt.getTenSanPham());
        dto.setGiaTien(hoaDonCt.getGiaTien());
        dto.setSoLuong(hoaDonCt.getSoLuong());
        dto.setThanhTien(hoaDonCt.getThanhTien());
        dto.setIdSanPhamCt(hoaDonCt.getIdSanPhamCt() != null ? hoaDonCt.getIdSanPhamCt().getId() : null);

        // Logic để lấy URL ảnh sản phẩm (nếu có)
        // Bạn cần đảm bảo Entity ChiTietSanPhamAdm có phương thức getAnhChinh()
        // hoặc bất kỳ phương thức nào trả về URL ảnh của sản phẩm.
//        if (hoaDonCt.getIdSanPhamCt() != null && hoaDonCt.getIdSanPhamCt().getAnhChinh() != null) {
//            dto.setAnhSanPham(hoaDonCt.getIdSanPhamCt().getAnhChinh());
//        } else {
//            dto.setAnhSanPham(null); // Hoặc một URL ảnh mặc định nếu không có ảnh
//        }

        return dto;
    }
}

