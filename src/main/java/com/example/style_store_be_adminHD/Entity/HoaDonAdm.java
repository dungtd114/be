package com.example.style_store_be_adminHD.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "hoa_don")
@AllArgsConstructor
@NoArgsConstructor
public class HoaDonAdm {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_nguoi_tao")
    private NguoiDungAdm idNguoiTao;

    @ManyToOne
    @JoinColumn(name = "id_nguoi_xuat")
    private NguoiDungAdm idNguoiXuat;

    @ManyToOne
    @JoinColumn(name = "id_thanh_toan")
    private PtThanhToanAdm idThanhToan;

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
    private NguoiDungAdm idKhachHang;

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HoaDonCtAdm> chiTietHoaDon;
}
