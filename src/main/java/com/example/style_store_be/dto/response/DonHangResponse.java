package com.example.style_store_be.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonHangResponse {
    private Long id;

    private String nguoiTao;

    private String nguoiXuat;

    private String thanhToan;

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

}
