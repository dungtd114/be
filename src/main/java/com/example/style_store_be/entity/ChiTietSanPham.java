package com.example.style_store_be.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "san_pham_ct")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietSanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_san_pham")
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "id_mau_sac")
    private MauSacSp mauSac;

    @ManyToOne
    @JoinColumn(name = "id_thuong_hieu")
    private ThuongHieu thuongHieu;

    @ManyToOne
    @JoinColumn(name = "id_size")
    private KichThuoc kichThuoc;

    @ManyToOne
    @JoinColumn(name = "id_xuat_xu")
    private XuatXu xuatXu;

    @ManyToOne
    @JoinColumn(name = "id_chat_lieu")
    private ChatLieu chatLieu;

    @ManyToOne
    @JoinColumn(name = "id_hinh_anh_mau_sac")
    private HinhAnh hinhAnhSp;

    @Column(name = "ma")
    private String ma;

    @Column(name = "gia_nhap")
    private Double giaNhap;

    @Column(name = "gia_ban")
    private Double giaBan;

    @Column(name = "so_luong")
    private Integer soLuong;

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

    @Column(name = "gia_ban_goc")
    private Double giaBanGoc;

    @ManyToMany
    @JoinTable(
            name = "giam_gia_san_pham_ct",
            joinColumns = @JoinColumn(name = "id_san_pham_ct"),
            inverseJoinColumns = @JoinColumn(name = "id_giam_gia")
    )
    private Set<GiamGia> dotGiamGias;
}
