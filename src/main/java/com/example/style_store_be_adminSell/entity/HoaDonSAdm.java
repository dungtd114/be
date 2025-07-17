package com.example.style_store_be_adminSell.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "hoa_don")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDonSAdm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_nguoi_tao", nullable = false)
    private NguoiDungSAdm nguoiTao;

    @ManyToOne
    @JoinColumn(name = "id_nguoi_xuat")
    private NguoiDungSAdm nguoiXuat;

    @ManyToOne
    @JoinColumn(name = "id_thanh_toan")
    private PtThanhToanSAdm thanhToan;

    @Column(name = "ma", length = 225)
    private String ma;

    @Column(name = "nguoi_dat_hang", length = 225)
    private String nguoiDatHang;

    @Column(name = "nguoi_nhan_hang", length = 225)
    private String nguoiNhanHang;

    @Column(name = "dia_chi_nhan_hang")
    private String diaChiNhanHang;

    @Column(name = "tong_so_luong_sp")
    private Integer tongSoLuongSp;


    @Column(name = "tong_tien")
    private BigDecimal tongTien;

    @Column(name = "tien_thue")
    private BigDecimal tienThue;

    @Column(name = "ngay_dat")
    private LocalDateTime ngayDat;

    @Column(name = "ngay_nhan")
    private LocalDateTime ngayNhan;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "ngay_sua")
    private LocalDateTime ngaySua;

    @Column(name = "ngay_xoa")
    private LocalDateTime ngayXoa;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "mo_ta")
    private String moTa;

    @ManyToOne
    @JoinColumn(name = "id_khach_hang")
    private NguoiDungSAdm khachHang;

}
