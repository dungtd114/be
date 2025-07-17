package com.example.style_store_be.dto;

import com.example.style_store_be.entity.Role;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
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

    private String matKhau;

    private Date ngayTao;

    private Integer trangThai;

    private String role;
}
