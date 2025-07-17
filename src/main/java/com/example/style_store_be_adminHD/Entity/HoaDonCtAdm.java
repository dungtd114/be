package com.example.style_store_be_adminHD.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hoa_don_ct")
public class HoaDonCtAdm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_hoa_don")
    @JsonIgnore
    private HoaDonAdm hoaDon;

    @ManyToOne
    @JoinColumn(name = "id_san_pham_ct")
    private ChiTietSanPhamAdm idSanPhamCt;

    @Column(name = "ten_san_pham")
    private String tenSanPham;

    @Column(name = "gia_tien")
    private Double giaTien;

    @Column(name = "so_luong")
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
