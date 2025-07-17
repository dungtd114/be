package com.example.style_store_be_adminHD.Entity;

import com.example.style_store_be.entity.ChatLieu;
import com.example.style_store_be.entity.KichThuoc;
import com.example.style_store_be.entity.MauSacSp;
import com.example.style_store_be.entity.SanPham;
import com.example.style_store_be.entity.ThuongHieu;
import com.example.style_store_be.entity.XuatXu;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "san_pham_ct")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietSanPhamAdm {
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
}
