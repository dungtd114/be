package com.example.style_store_be_adminSell.dto;

import com.example.style_store_be_adminSell.entity.ChucVuSAdm;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NguoiDungDto {
        // Thông tin người dùng
        private Long idNguoiDung;
        private String maNguoiDung;
        private String hoTen;
        private String soDienThoai;
        private String email;
        private String cccd;
        private String diaChiNguoiDung;
        private Integer gioiTinh;
        private Date namSinh;
        private String tenDangNhap;
        private String matKhau;
        private String tinhNguoiDung;
        private String huyenNguoiDung;
        private String xaNguoiDung;
        private Long idChucVu;

        // Thông tin địa chỉ nhận
        private Long idDiaChi;
        private String maDiaChi;
        private String tenNguoiNhan;
        private String soDienThoaiNhan;
        private String soNhaNhan;
        private String diaChiNhan;
        private String tinhNhan;
        private String huyenNhan;
        private String xaNhan;
}


