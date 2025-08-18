package com.example.style_store_be_adminSell.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonSAdmDto {
    private Long id;

    private Long nguoiTaoId;
    private String tenNguoiTao;

    private Long nguoiXuatId;
    private String tenNguoiXuat;

    private Long ptThanhToanId;
    private String tenPTThanhToan;

    private Long khachHangId;
    private String tenkhachHang;

    private String ma;

    private String nguoiDatHang;

    private Long diaChiNhanId;
    private String nguoiNhanHang;

    private String diaChiNhanHang;

    private Integer tongSoLuongSp;

    private BigDecimal tongTien;

    private BigDecimal tienThue;

    private LocalDateTime ngayDat;

    private LocalDateTime ngayNhan;

    private LocalDateTime ngayTao;

    private LocalDateTime ngaySua;

    private LocalDateTime ngayXoa;

    private Integer trangThai;

    private String moTa;

    private Integer trangThaiThanhToan;

    private Integer hinhThucNhanHang;

    private String soDtNguoiNhan;

    private String tenNguoiGiaoHang;

    private String sdtNguoiGiaoHang;

    private BigDecimal tienKhachTra;

    private BigDecimal tienThua;
}
