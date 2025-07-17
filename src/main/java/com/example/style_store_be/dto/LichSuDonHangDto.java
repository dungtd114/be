package com.example.style_store_be.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LichSuDonHangDto {
    private Long id;
    private String maDonHang;
    private String tenKhachHang;
    private Integer soLuongSanPham;
    private String noiNhanHang;
    private String phuongThucThanhToan;
    private Double tongTien;
    private Integer trangThaiDonHang;
    private Integer trangThaiThanhToan;
    private Date ngayDatHang;
    private Date ngayNhanHang;
    private Double tienThue;

}
