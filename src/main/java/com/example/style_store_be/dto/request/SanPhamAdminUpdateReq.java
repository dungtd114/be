package com.example.style_store_be.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanPhamAdminUpdateReq {

    private Long idSanPham;

    private Long idMauSac;

    private String maMauSac;

    private Long idThuongHieu;

    private Long idKichThuoc;

    private Long idXuatXu;

    private Long idChatLieu;

    private Long idHinhAnhSp;

    private String ma;

    @NotNull(message = "MISSING_REQUIRED_FIELDS")
    @DecimalMin(value = "0.0", inclusive = false, message = "INVALID_PRICE")
    private Double giaNhap;

    @DecimalMin(value = "0.0", inclusive = false, message = "INVALID_PRICE")
    @NotNull(message = "MISSING_REQUIRED_FIELDS")
    private Double giaBan;

    @DecimalMin(value = "0.0", inclusive = false, message = "INVALID_QUANTITY")
    @NotNull(message = "MISSING_REQUIRED_FIELDS")
    private Integer soLuong;

    private Integer trangThai;

    private String moTa;



}
