package com.example.style_store_be.dto;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDonCtDto {
    private Long id;

    private String hinhAnh;

    private String tenSanPham;

    private Double giaTien;

    private Integer soLuong;

    private Double thanhTien;

    private String tenMauSac;

    private String tenChatLieu;

    private String tenThuongHieu;

    private String tenKichThuoc;

    public HoaDonCtDto(Long id, String hinhAnh, String tenSanPham, Double giaTien, Integer soLuong, Double thanhTien) {
        this.id = id;
        this.hinhAnh = hinhAnh;
        this.tenSanPham = tenSanPham;
        this.giaTien = giaTien;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
    }
}
