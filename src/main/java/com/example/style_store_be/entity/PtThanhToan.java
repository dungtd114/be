package com.example.style_store_be.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@Entity
@Table(name = "pt_thanh_toan")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PtThanhToan {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "ten", nullable = false, length = 50)
    private String ten;

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @Column(name = "ngay_sua")
    private Date ngaySua;

    @Column(name = "ngay_xoa")
    private Date ngayXoa;

    @Column(name = "trang_thai", nullable = false)
    private Integer trangThai;

}