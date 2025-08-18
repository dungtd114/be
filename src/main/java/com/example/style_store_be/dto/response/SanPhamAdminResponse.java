package com.example.style_store_be.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanPhamAdminResponse {
    private Long id;

    private Long idSanPham;

    private Long idMauSac;

    private String maMauSac;

    private Long idThuongHieu;

    private Long idKichThuoc;

    private Long idXuatXu;

    private Long idChatLieu;

    private Long idHinhAnhSp;

    private String ma;

    private Double giaNhap;

    private Double giaBan;

    private Integer soLuong;

    private Integer trangThai;

    private String moTa;

    private Double giaBanGoc;


}
