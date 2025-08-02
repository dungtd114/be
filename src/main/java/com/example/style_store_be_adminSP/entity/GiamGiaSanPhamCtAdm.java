package com.example.style_store_be_adminSP.entity;

import com.example.style_store_be.entity.GiamGia;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "giam_gia_san_pham_ct")
@IdClass(com.example.style_store_be_adminSP.entity.GiamGiaSanPhamCtId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GiamGiaSanPhamCtAdm {

    @Id
    @Column(name = "id_giam_gia")
    private Long idGiamGia;

    @Id
    @Column(name = "id_san_pham_ct")
    private Long idSanPhamCt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_giam_gia", insertable = false, updatable = false)
    private GiamGia giamGia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_san_pham_ct", insertable = false, updatable = false)
    private SanPhamCtAdm sanPhamCt;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "ngay_sua")
    private LocalDateTime ngaySua;
}
