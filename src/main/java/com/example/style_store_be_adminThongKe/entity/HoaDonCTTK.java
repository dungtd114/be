package com.example.style_store_be_adminThongKe.entity;

import com.example.style_store_be_adminSP.entity.SanPhamCtAdm;
import com.example.style_store_be_adminSell.entity.HoaDonSAdm;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "hoa_don_ct")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDonCTTK {
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
