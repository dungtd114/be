package com.example.style_store_be.dto.response;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanPhamHoaDonAdminResponse {
    private Long idHoaDonCt;
    private Long idSanPham;
    private String tenSanPham;
    private String hinhAnhSanPham;
    private String giaBanSanPham;
    private String giaBanGocSanPham;
    private String thuongHieuSanPham;
    private String mauSacSanPham;
    private String chatLieuSanPham;
    private Integer soLuong;
}