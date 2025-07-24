package com.example.style_store_be.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class GiamGiaUpdateRequest {

    private String tenDotGiam;

    private Double giamGia;

    private Double giamToiDa;

    private Double dieuKienGiam;

    private Integer soLuong;

    private Date ngayBatDau;

    private Date ngayKetThuc;

    private String moTa;

}
