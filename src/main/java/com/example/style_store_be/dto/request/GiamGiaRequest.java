package com.example.style_store_be.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class GiamGiaRequest {
    @NotBlank(message = "INVALID_NAME_VOUCHER")
    @Size(max = 100, message = "INVALID_NAME_VOUCHER")
    private String tenDotGiam;

    @NotNull(message = "INVALID_VOUCHER")
    @DecimalMin(value = "0.0", inclusive = false, message = "INVALID_VOUCHER")
    @DecimalMax(value = "100.0", message = "INVALID_VOUCHER")
    private Double giamGia;

    @NotNull(message = "Giảm tối đa không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá trị giảm tối đa phải lớn hơn 0")
    private Double giamToiDa;

    @NotNull(message = "Điều kiện giảm không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Điều kiện giảm phải lớn hơn 0")
    private Double dieuKienGiam;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn hoặc bằng 1")
    private Integer soLuong;

    @NotNull(message = "Ngày bắt đầu không được để trống")
    private Date ngayBatDau;

    @NotNull(message = "Ngày kết thúc không được để trống")
    @Future(message = "Ngày kết thúc phải là trong tương lai")
    private Date ngayKetThuc;

    private String moTa;

}
