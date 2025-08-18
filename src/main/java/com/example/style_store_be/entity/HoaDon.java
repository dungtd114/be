package com.example.style_store_be.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "hoa_don")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_nguoi_tao")
    private User nguoiTao;

    @ManyToOne
    @JoinColumn(name = "id_nguoi_xuat")
    private User nguoiXuat;

    @ManyToOne
    @JoinColumn(name = "id_thanh_toan")
    private PtThanhToan thanhToan;

    @Column(name = "ma")
    private String ma;

    @Column(name = "nguoi_dat_hang")
    private String nguoiDatHang;

    @Column(name = "nguoi_nhan_hang")
    private String nguoiNhanHang;

    @Column(name = "dia_chi_nhan_hang")
    private String diaChiNhanHang;

    @Column(name = "tong_so_luong_sp")
    private Integer tongSoLuongSp;


    @Column(name = "tong_tien")
    private Double tongTien;

    @Column(name = "tien_thue")
    private Double tienThue;

    @Column(name = "ngay_dat")
    private Date ngayDat;

    @Column(name = "ngay_nhan")
    private Date ngayNhan;

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @Column(name = "ngay_sua")
    private Date ngaySua;

    @Column(name = "ngay_xoa")
    private Date ngayXoa;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "mo_ta")
    private String moTa;

    @ManyToOne
    @JoinColumn(name = "id_khach_hang")
    private User khachHang;

    @Column(name = "trang_thai_thanh_toan")
    private Integer trangThaiThanhToan;

    @Column(name = "so_dt_nguoi_nhan")
    private String soDtNguoiNhan;

    @OneToMany(mappedBy = "hoaDon")
    private Set<HoaDonCt> hoaDonCts;

    @Column(name = "ten_nguoi_giao_hang")
    private String tenNguoiGiaoHang;

    @Column(name = "so_dt_nguoi_giao_hang")
    private String sdtNguoiGiaoHang;

    @Column(name = "tien_khach_tra")
    private Double tienKhachTra;

    @Column(name="tien_thua")
    private Double tienThua;
}