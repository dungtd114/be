package com.example.style_store_be.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "lich_su_thao_tac_don_hang")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LichSuHoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_hoa_don")
    private HoaDon hoaDon;

    @Column(name = "tieu_de")
    private String tieuDe;

    @Column(name = "nguoi_thuc_hien")
    private String nguoiThucHien;

    @Column(name = "noi_dung")
    private String noiDung;

    @Column(name = "ngay_cap_nhat")
    private Date ngayCapNhat;
}
