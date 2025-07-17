package com.example.style_store_be_adminThongKe.DTO;

import lombok.Data;

@Data
public class SanPhamBanChayDTO {
    private String maSanPham;
    private String tenSanPham;
    private String mauSac;
    private String thuongHieu;
    private String kichThuoc;
    private Integer tongSoLuongBan;
    private Double tongDoanhThu;
}
