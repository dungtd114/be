package com.example.style_store_be.dto.request;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDonUpdateRequest {

    private String nguoiNhanHang;

    private String diaChiNhanHang;

    private String soDtNguoiNhan;

    private Double tienThue;

    private String tenNguoiGiaoHang;

    private String sdtNguoiGiaoHang;
}
