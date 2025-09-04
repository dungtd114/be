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


    private Long idPtTT;

    public HoaDonAdminDto(Long id, String nguoiTao, String nguoiXuat, String ptThanhToan, String soDienThoaiKhachHang, String ma, String nguoiDatHang, String nguoiNhanHang, String diaChiNhanHang, Integer tongSoLuongSp, Double tongTien, Double tienThue, Date ngayDat, Date ngayNhan, Date ngayTao, Date ngaySua, Date ngayXoa, Integer trangThai, String moTa, Integer trangThaiThanhToan, String soDtNguoiNhan) {
        this.id = id;
        this.nguoiTao = nguoiTao;
        this.nguoiXuat = nguoiXuat;
        this.ptThanhToan = ptThanhToan;
        this.soDienThoaiKhachHang = soDienThoaiKhachHang;
        this.ma = ma;
        this.nguoiDatHang = nguoiDatHang;
        this.nguoiNhanHang = nguoiNhanHang;
        this.diaChiNhanHang = diaChiNhanHang;
        this.tongSoLuongSp = tongSoLuongSp;
        this.tongTien = tongTien;
        this.tienThue = tienThue;
        this.ngayDat = ngayDat;
        this.ngayNhan = ngayNhan;
        this.ngayTao = ngayTao;
        this.ngaySua = ngaySua;
        this.ngayXoa = ngayXoa;
        this.trangThai = trangThai;
        this.moTa = moTa;
        this.trangThaiThanhToan = trangThaiThanhToan;
        this.soDtNguoiNhan = soDtNguoiNhan;
    }
}
