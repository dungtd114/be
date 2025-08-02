package com.example.style_store_be.dto.request;

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
    private String hoTen;
    private String soDienThoai;
    private String email;
    private String cccd;
    private String diaChi;
    private Integer gioiTinh;
    private Date namSinh;
    private String tenDangNhap;
    private String matKhau;
    private Integer trangThai;
    private Long idChucVu;
    private String tinh;
    private String huyen;
    private String xa;
}
