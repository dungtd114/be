package com.example.style_store_be.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class HoaDonChiTietRequest {
    private Long hoaDonId;

    private Long sanPhamCtId;

    private String tenSanPham;

    private Double giaTien;

    private Integer soLuong;

    private Double thanhTien;

}
