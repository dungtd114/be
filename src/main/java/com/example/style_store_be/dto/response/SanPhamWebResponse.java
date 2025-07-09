package com.example.style_store_be.dto.response;

import com.example.style_store_be.entity.*;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Date;

public class SanPhamWebResponse {
    private Long id;

    private SanPham sanPham;

    private MauSacSp mauSac;

    private ThuongHieu thuongHieu;

    private KichThuoc kichThuoc;

    private XuatXu xuatXu;

    private ChatLieu chatLieu;

    private HinhAnh hinhAnhSp;

    private String ma;

    private Double giaNhap;

    private Double giaBan;

    private Integer soLuong;

    private Date ngayTao;

    private Date ngaySua;

    private Date ngayXoa;

    private Integer trangThai;

    private String moTa;

    private String giaBanGoc;
}
