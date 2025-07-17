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


}
