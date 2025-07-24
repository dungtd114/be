package com.example.style_store_be_adminSP.dto;

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
public class SanPhamCtDTOAdm {
    private Long id;

    private Long hinhAnhMauSacId;

    private Long sanPhamId;

    private Long mauSacId;

    private Long thuongHieuId;

    private Long kichThuocId;

    private Long xuatXuId;

    private Long chatLieuId;

    private String ma;

    private BigDecimal giaNhap;

    private BigDecimal giaBan;

    private BigDecimal giaBanGoc;

    private Integer soLuong;

    private String moTa;

    private Integer trangThai;

    private LocalDateTime ngayTao;

    private LocalDateTime ngaySua;

    private LocalDateTime ngayXoa;

    private String tenSanPham;

    private String tenMauSac;

    private String tenThuongHieu;

    private String tenKichThuoc;

    private String tenXuatXu;

    private String tenChatLieu;

    private String urlHinhAnhMauSac;
}