package com.example.style_store_be.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class DonHangRequest {

    private String nguoiDatHang;

    private String nguoiNhanHang;

    private String diaChiNhanHang;

    private Integer tongSoLuongSp;
    private Double tongTien;

    private Double tienThue;

    private String moTa;
    private List<DonHangChiTietRequest> chiTietDonHang;
    private String soDtNguoiNhan;

}
