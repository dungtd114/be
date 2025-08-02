package com.example.style_store_be.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "nguoi_dung")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "trang_thai", nullable = false)
    private Integer trangThai;

    @ManyToOne()
    @JoinColumn(name = "id_chuc_vu")
    private Role role;



    @Column(name = "tinh")
    private String tinh;

    @Column(name = "huyen")
    private String huyen;

    @Column(name = "xa")
    private String xa;
}