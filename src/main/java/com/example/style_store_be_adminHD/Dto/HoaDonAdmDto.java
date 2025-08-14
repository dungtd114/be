package com.example.style_store_be_adminHD.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonAdmDto {
    private Long id;
    private String ma;
    private String nguoiDatHang;
    private String nguoiNhanHang;
    private String diaChiNhanHang;
    private Integer tongSoLuongSp;
    private Double tongTien;
    private Double tienThue;
    private Date ngayDat;
    private Date ngayNhan;
    private Date ngayTao;
    private Date ngaySua;
    private Date ngayXoa;
    private Integer trangThai;
    private String moTa;
    private String tenNguoiTao;
    private String tenKhachHang;
    private String tenThanhToan;
    private List<HoaDonCtAdmDto> chiTietHoaDon;
}
