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
public class HoaDonCTSAdmDto {
    private Long id;

    private Long hoaDonId;

    private Long sanPhamCTId;

    private String maSanPhamCtAdm;

    private String tenSanPham;

    private BigDecimal giaTien;

    private Integer soLuong;

    private BigDecimal thanhTien;

    private LocalDateTime ngayTao;

    private LocalDateTime ngaySua;

    private LocalDateTime ngayXoa;

    private Integer trangThai;

    private Integer soLuongSP;

    private BigDecimal giaGocSP;
}
