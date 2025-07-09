package com.example.style_store_be.dto.response;

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
public class UserResponse {
    private Long id;
    private String ma;
    private String hoTen;
    private String soDienThoai;
    private String email;
    private String cccd;
    private String diaChi;
    private Integer gioiTinh;
    private Date namSinh;
    private String tenDangNhap;
    private Date ngayTao;
    private Date ngaySua;
    private Date ngayXoa;
    private Integer trangThai;
    private Long idChucVu;
}
