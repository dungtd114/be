package com.example.style_store_be.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "giam_gia")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiamGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_nguoi_dung")
    private User user;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ten_dot_giam")
    private String tenDotGiam;

    @Column(name = "giam_gia")
    private Double giamGia;

    @Column(name = "giam_toi_da")
    private Double giamToiDa;

    @Column(name = "dieu_kien_giam")
    private Double dieuKienGiam;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "nguoi_tao")
    private String nguoiTao;

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @Column(name = "ngay_sua")
    private Date ngaySua;

    @Column(name = "ngay_xoa")
    private Date ngayXoa;

    @Column(name = "ngay_bat_dau")
    private Date ngayBatDau;

    @Column(name = "ngay_ket_thuc")
    private Date ngayKetThuc;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @ManyToMany(mappedBy = "dotGiamGias")
    private Set<ChiTietSanPham> chiTietSanPhams;

}