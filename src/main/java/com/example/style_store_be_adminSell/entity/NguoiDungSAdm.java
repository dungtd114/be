package com.example.style_store_be_adminSell.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "nguoi_dung")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NguoiDungSAdm {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "ma", nullable = false, length = 20)
    private String ma;


    @Column(name = "ho_ten", nullable = false, length = 50)
    private String hoTen;


    @Column(name = "so_dien_thoai", nullable = false, length = 15)
    private String soDienThoai;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "cccd", length = 20)
    private String cccd;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "gioi_tinh", nullable = false)
    private Integer gioiTinh;

    @Column(name = "nam_sinh", nullable = false)
    private Date namSinh;

    @Column(name = "ten_dang_nhap", nullable = false, length = 50)
    private String tenDangNhap;

    @Column(name = "mat_khau", nullable = false, length = 100)
    private String matKhau;

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @Column(name = "ngay_sua")
    private Date ngaySua;

    @Column(name = "ngay_xoa")
    private Date ngayXoa;

    @Column(name = "trang_thai", nullable = false)
    private Integer trangThai ;

    @Column(name = "tinh")
    private String tinh;

    @Column(name = "huyen")
    private String huyen;

    @Column(name = "xa")
    private String xa;

    @Column(name = "id_chuc_vu")
    private Long idChucVu;
}
