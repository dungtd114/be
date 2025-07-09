package com.example.style_store_be.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class DonHangChiTietRequest {

    private Long sanPhamctId;

    private String tenSanPham;

    private Double giaTien;

    private Integer soLuong;

    private Double thanhTien;

}
