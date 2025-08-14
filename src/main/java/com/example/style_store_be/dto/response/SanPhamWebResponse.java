package com.example.style_store_be.dto.response;

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
public class SanPhamWebResponse {
    private Long id;

    private String tenSanPham;

    private String tenMauSac;

    private String maMauSac;

    private String tenThuongHieu;

    private String tenKichThuoc;

    private String tenXuatXu;

    private String tenChatLieu;

    private String hinhAnhSp;

    private String ma;

    private Double giaNhap;

    private Double giaBan;

    private Integer soLuong;

    private Integer trangThai;

    private String moTa;

    private Double giaBanGoc;


}
