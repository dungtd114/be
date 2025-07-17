package com.example.style_store_be_adminSell.entity;

import com.example.style_store_be_adminSP.entity.SanPhamCtAdm;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "hoa_don_ct")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDonCTSAdm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_hoa_don", nullable = false)
    private HoaDonSAdm hoaDon;

    @ManyToOne
    @JoinColumn(name = "id_san_pham_ct", nullable = false)
    private SanPhamCtAdm sanPhamCt;

    @Column(name = "ten_san_pham", length = 225)
    private String tenSanPham;

    @Column(name = "gia_tien")
    private BigDecimal giaTien;

    @Column(name = "so_luong", nullable = false)
    private Integer soLuong;

    @Column(name = "thanh_tien")
    private BigDecimal thanhTien;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "ngay_sua")
    private LocalDateTime ngaySua;

    @Column(name = "ngay_xoa")
    private LocalDateTime ngayXoa;

    @Column(name = "trang_thai")
    private Integer trangThai;
}
