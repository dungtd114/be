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

    private Integer trangThai;
}
