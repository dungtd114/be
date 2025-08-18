package com.example.style_store_be.dto.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDonUDResponse {
    private Long id;

    private String nguoiNhanHang;

    private String diaChiNhanHang;

    private String soDtNguoiNhan;

    private Double tienThue;

    private String tenNguoiGiaoHang;

    private String sdtNguoiGiaoHang;
}
