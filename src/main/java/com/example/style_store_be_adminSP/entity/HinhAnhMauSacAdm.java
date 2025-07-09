package com.example.style_store_be_adminSP.entity;

import com.example.style_store_be.entity.MauSacSp;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "hinh_anh_mau_sac")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class HinhAnhMauSacAdm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mau_sac")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private MauSacSp mauSac;

    @Column(name = "hinh_anh", nullable = false, length = 255)
    private String hinhAnh;

    @Column(name = "mo_ta", columnDefinition = "NVARCHAR(MAX)")
    private String moTa;

    @Column(name = "ngay_tao", nullable = false)
    private LocalDateTime ngayTao = LocalDateTime.now();

    @Column(name = "ngay_sua")
    private LocalDateTime ngaySua;

    @Column(name = "ngay_xoa")
    private LocalDateTime ngayXoa;

    @Column(name = "trang_thai", nullable = false)
    private Integer trangThai;
}
