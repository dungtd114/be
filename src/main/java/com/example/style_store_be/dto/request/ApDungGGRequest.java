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
    @NotBlank(message = "Tên đợt giảm giá không được để trống")
    @Size(max = 100, message = "Tên đợt giảm giá không được vượt quá 100 ký tự")
    private String tenDotGiam;

    @NotNull(message = "Phần trăm giảm giá không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Phần trăm giảm giá phải lớn hơn 0")
    @DecimalMax(value = "100.0", message = "Phần trăm giảm giá không được vượt quá 100")
    private Double giamGia;

    @NotNull(message = "Giảm tối đa không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá trị giảm tối đa phải lớn hơn 0")
    private Double giamToiDa;

    @NotNull(message = "Điều kiện giảm không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Điều kiện giảm phải lớn hơn 0")
    private Double dieuKienGiam;

    private Integer soLuong;

    @NotNull(message = "Ngày bắt đầu không được để trống")
    private Date ngayBatDau;

    @NotNull(message = "Ngày kết thúc không được để trống")
    @Future(message = "Ngày kết thúc phải là trong tương lai")
    private Date ngayKetThuc;

    @Size(max = 500, message = "Mô tả không được vượt quá 500 ký tự")
    private String moTa;

    @NotEmpty(message = "Danh sách ID sản phẩm chi tiết không được để trống")
    @Size(min = 1, message = "Phải cung cấp ít nhất một ID sản phẩm chi tiết")
    private List<Long> sanPhamCtIds;

}
