package com.example.style_store_be.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class SanPhamAdminCrRequest {
    private Long sanPhamId;

    private Long mauSacId;

    private Long thuongHieuId;

    private Long kichThuocId;

    private Long xuatXuId;

    private Long chatLieuId;

    private Long hinhAnhSpId;

    private Double giaNhap;

    private Double giaBan;

    private Integer soLuong;

    private String moTa;
}
