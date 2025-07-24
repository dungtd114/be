package com.example.style_store_be_adminSP.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "mau_sac")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MauSacSpAdm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ma", nullable = false, unique = true, length = 7)
    private String ma; // Sử dụng ma để lưu mã hex (VD: #FF0000)

    @Column(name = "ten")
    private String ten;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "ngay_sua")
    private LocalDateTime ngaySua;

    @Column(name = "ngay_xoa")
    private LocalDateTime ngayXoa;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "trang_thai")
    private Integer trangThai;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hinh_anh_mau_sac_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private HinhAnhMauSacAdm hinhAnhMauSac;


}