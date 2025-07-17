package com.example.style_store_be_adminHD.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "nguoi_dung")
public class NguoiDungAdm {
    @Id
    @Column(name = "id")
    private Long id;


    @Column(name = "ma")
    private String ma;


    @Column(name = "ho_ten")
    private String hoTen;


    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "email")
    private String email;

    @Column(name = "cccd")
    private String cccd;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "gioi_tinh")
    private Integer gioiTinh;

    @Column(name = "nam_sinh")
    private Date namSinh;

    @Column(name = "ten_dang_nhap")
    private String tenDangNhap;

    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @Column(name = "ngay_sua")
    private Date ngaySua;

    @Column(name = "ngay_xoa")
    private Date ngayXoa;
    @Column(name = "trang_thai")
    private Integer trangThai;
}
