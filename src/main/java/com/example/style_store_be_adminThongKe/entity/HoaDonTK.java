package com.example.style_store_be_adminThongKe.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "hoa_don")
@Data
public class HoaDonTK {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_nguoi_tao", nullable = false)
    private NguoiDungTK nguoiTao;

    @ManyToOne
    @JoinColumn(name = "id_nguoi_xuat")
    private NguoiDungTK nguoiXuat;

    @ManyToOne
    @JoinColumn(name = "id_thanh_toan")
    private PhuongThucThanhToanTK thanhToan;

    @ManyToOne
    @JoinColumn(name = "id_khach_hang")
    private NguoiDungTK khachHang;

    @Column(name = "ma", length = 225)
    private String ma;

    @Column(name = "nguoi_dat_hang", length = 225)
    private String nguoiDatHang;

    @Column(name = "nguoi_nhan_hang", length = 225)
    private String nguoiNhanHang;

    @Column(name = "dia_chi_nhan_hang")
    private String diaChiNhanHang;

    @Column(name = "tong_so_luong_sp", nullable = false)
    private Integer tongSoLuongSp;

    @Column(name = "tong_tien", nullable = false, precision = 12, scale = 2)
    private BigDecimal tongTien;

    @Column(name = "tien_thue", nullable = false, precision = 10, scale = 2)
    private BigDecimal tienThue;

    @Column(name = "ngay_dat", nullable = false)
    private LocalDateTime ngayDat = LocalDateTime.now();

    @Column(name = "ngay_nhan")
    private LocalDateTime ngayNhan;

    @Column(name = "ngay_tao", nullable = false)
    private LocalDateTime ngayTao = LocalDateTime.now();

    @Column(name = "ngay_sua")
    private LocalDateTime ngaySua;

    @Column(name = "ngay_xoa")
    private LocalDateTime ngayXoa;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "mo_ta")
    private String moTa;
}