package com.example.style_store_be.dto;

import com.example.style_store_be.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiamGiaDto {

    private Long id;

    private String ma;

    private String tenDotGiam;

    private Double giamGia;

    private Date ngayBatDau;

    private Date ngayKetThuc;

    private Double dieuKienGiam;

    private Integer trangThai;

    private Double giamToiDa;

    private Integer idGiamGiaSanPhamCt;

    public GiamGiaDto(Long id, String ma, String tenDotGiam, Double giamGia, Date ngayBatDau, Date ngayKetThuc, Integer trangThai) {
        this.id = id;
        this.ma = ma;
        this.tenDotGiam = tenDotGiam;
        this.giamGia = giamGia;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
    }
}
