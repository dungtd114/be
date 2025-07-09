package com.example.style_store_be.dto;

import com.example.style_store_be.entity.*;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanPhamWebDto {
    private Long id;

    private String ma;

    private Double giaNhap;

    private Double giaBan;

    private Integer soLuong;

    private Date ngayTao;

    private Date ngaySua;

    private Date ngayXoa;

    private Integer trangThai;

    private String moTa;

    private Long sanPhamId;

    private String tenSanPham;

    private Long mauSacId;

    private String tenMauSac;

    private Long thuongHieuId;

    private String tenThuongHieu;

    private Long kichThuocId;

    private String tenKichThuoc;

    private Long xuatXuId;

    private String tenXuatXu;

    private Long chatLieuId;

    private String tenChatLieu;

    private Long hinhAnhSpId;

    private String tenHinhAnhSp;

    private String giaBanGoc;

    private Double giamGia;

}
