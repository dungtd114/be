package com.example.style_store_be.entity;

import com.example.style_store_be.dto.response.SanPhamHoaDonAdminResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "hoa_don_ct")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDonCt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_hoa_don", nullable = false)
    private HoaDon hoaDon;

    @ManyToOne
    @JoinColumn(name = "id_san_pham_ct", nullable = false)
    private ChiTietSanPham sanPhamCt;

    @Column(name = "ten_san_pham", length = 225)
    private String tenSanPham;

    @Column(name = "gia_tien")
    private Double giaTien;

    @Column(name = "so_luong", nullable = false)
    private Integer soLuong;

    @Column(name = "thanh_tien")
    private Double thanhTien;

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @Column(name = "ngay_sua")
    private Date ngaySua;

    @Column(name = "ngay_xoa")
    private Date ngayXoa;

    @Column(name = "trang_thai")
    private Integer trangThai;



}