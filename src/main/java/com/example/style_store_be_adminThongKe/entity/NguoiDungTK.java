package com.example.style_store_be_adminThongKe.entity;


import com.example.style_store_be_adminSell.entity.ChucVuSAdm;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "nguoi_dung")
@Data
public class NguoiDungTK {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_chuc_vu", nullable = false)
    private ChucVuSAdm chucVu;

    @Column(name = "ma", length = 20, nullable = false)
    private String ma;

    @Column(name = "ho_ten", length = 50, nullable = false)
    private String hoTen;

    @Column(name = "so_dien_thoai", length = 15, nullable = false)
    private String soDienThoai;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "cccd", length = 20)
    private String cccd;

    @Column(name = "dia_chi", length = 255)
    private String diaChi;

    @Column(name = "gioi_tinh", nullable = false)
    private Integer gioiTinh;

    @Column(name = "nam_sinh", nullable = false)
    private LocalDateTime namSinh;

    @Column(name = "ten_dang_nhap", length = 50, nullable = false)
    private String tenDangNhap;

    @Column(name = "mat_khau", length = 100, nullable = false)
    private String matKhau;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "ngay_sua")
    private LocalDateTime ngaySua;

    @Column(name = "ngay_xoa")
    private LocalDateTime ngayXoa;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "refresh_token")
    private String refreshToken;
}
