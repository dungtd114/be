package com.example.style_store_be.dto;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDonAdminDto {
    private Long id;

    private String nguoiTao;

    private String nguoiXuat;

    private String ptThanhToan;

    private String soDienThoaiKhachHang;

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

    private Integer trangThaiThanhToan;

    private String soDtNguoiNhan;




}
