package com.example.style_store_be.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ApDungGGRequest {
    @NotBlank(message = "MISSING_REQUIRED_FIELDS")
    @Size(max = 100, message = "INVALID_NAME_SIZE")
    private String tenDotGiam;

    @NotNull(message = "MISSING_REQUIRED_FIELDS")
    @DecimalMin(value = "0.0", inclusive = false, message = "INVALID_DISCOUNT_AMOUNT_ZERO")
    @DecimalMax(value = "100.0", message = "INVALID_DISCOUNT_PERCENTAGE")
    private Double giamGia;

    @NotNull(message = "MISSING_REQUIRED_FIELDS")
    @DecimalMin(value = "0.0", inclusive = false, message = "INVALID_MAX_DISCOUNT")
    private Double giamToiDa;

    @NotNull(message = "MISSING_REQUIRED_FIELDS")
    @DecimalMin(value = "0.0", inclusive = false, message = "INVALID_CONDITION")
    private Double dieuKienGiam;

    private Integer soLuong;

    @NotNull(message = "MISSING_REQUIRED_FIELDS")
    private Date ngayBatDau;

    @NotNull(message = "MISSING_REQUIRED_FIELDS")
    @Future(message = "INVALID_END_DATE")
    private Date ngayKetThuc;

    @Size(max = 500, message = "INVALID_DESCRIPTION")
    private String moTa;

    @NotEmpty(message = "INVALID_PRODUCT_IDS")
    private List<Long> sanPhamCtIds;

}
