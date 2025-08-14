package com.example.style_store_be.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequest {

    @NotBlank(message = "MISSING_REQUIRED_FIELDS")
    @Size(max = 50, message = "USER_NAME_TOO_LONG")
    private String hoTen;

    @NotBlank(message = "MISSING_REQUIRED_FIELDS")
    @Size(min = 10, max = 10, message = "PHONE_LENGTH_INVALID")
    @Pattern(regexp = "^(0\\d{9})$", message = "PHONE_INVALID_FORMAT")
    private String soDienThoai;

    @NotBlank(message = "MISSING_REQUIRED_FIELDS")
    @Email(message = "EMAIL_INVALID_FORMAT")
    @Size(max = 100, message = "EMAIL_TOO_LONG")
    private String email;

    private String cccd;

    private String diaChi;

    @NotNull(message = "MISSING_REQUIRED_FIELDS")
    @Min(value = 0, message = "GENDER_INVALID_VALUE")
    @Max(value = 2, message = "GENDER_INVALID_VALUE")
    private Integer gioiTinh;

    @NotNull(message = "MISSING_REQUIRED_FIELDS")
    @Past(message = "DOB_INVALID")
    private Date namSinh;

    private String tenDangNhap;

    private String matKhau;

    private Integer trangThai;

    @NotBlank(message = "MISSING_REQUIRED_FIELDS")
    private String tinh;

    @NotBlank(message = "MISSING_REQUIRED_FIELDS")
    private String huyen;

    @NotBlank(message = "MISSING_REQUIRED_FIELDS")
    private String xa;
}
