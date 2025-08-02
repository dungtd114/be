package com.example.style_store_be.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
@Entity
@Table(name = "chat_lieu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatLieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ma", columnDefinition = "NVARCHAR(20)")
    private String ma;

    @Column(name = "ten", columnDefinition = "NVARCHAR(50)")
    private String ten;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "ngay_sua")
    private LocalDateTime ngaySua;

    @Column(name = "ngay_xoa")
    private LocalDateTime ngayXoa;

    @Column(name = "mo_ta", columnDefinition = "NVARCHAR(255)")
    private String moTa;

    @Column(name = "trang_thai")
    private Integer trangThai;
}
