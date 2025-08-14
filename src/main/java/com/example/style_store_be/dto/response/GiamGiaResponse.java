package com.example.style_store_be.dto.response;

import com.example.style_store_be.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiamGiaResponse {
    private Long id;

    private String tenDotGiam;

    private Double giamGia;

    private Double giamToiDa;

    private Double dieuKienGiam;

    private Date ngayBatDau;

    private Date ngayKetThuc;

    private String moTa;

    private Integer trangThai;

    private List<Long> idChiTietSanPham;

    private List<Long> idSanPham;
}
