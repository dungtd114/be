package com.example.style_store_be.dto;

import com.example.style_store_be.entity.*;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanPhamWebDto {
    private Long id;

    private String ma;

    private Double giaNhap;

    private Double giaBan;

    private Integer soLuong;

    private Date ngayTao;

    private Date ngaySua;

    private Date ngayXoa;

    private Integer trangThai;

    private String moTa;

    private Long sanPhamId;

    private String tenSanPham;

    private Long mauSacId;

    private String tenMauSac;

    private Long thuongHieuId;

    private String tenThuongHieu;

    private Long kichThuocId;

    private String tenKichThuoc;

    private Long xuatXuId;

    private String tenXuatXu;

    private Long chatLieuId;

    private String tenChatLieu;

    private Long hinhAnhSpId;

    private String tenHinhAnhSp;

    private Double giaBanGoc;

    private Double giamGia;

    private String maMauSac;

    public SanPhamWebDto(Long id, String ma, Double giaNhap, Double giaBan, Integer soLuong, Date ngayTao, Date ngaySua, Date ngayXoa, Integer trangThai, String moTa, Long sanPhamId, String tenSanPham, Long mauSacId, String tenMauSac, Long thuongHieuId, String tenThuongHieu, Long kichThuocId, String tenKichThuoc, Long xuatXuId, String tenXuatXu, Long chatLieuId, String tenChatLieu, Long hinhAnhSpId, String tenHinhAnhSp, Double giaBanGoc, Double giamGia) {
        this.id = id;
        this.ma = ma;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.ngayTao = ngayTao;
        this.ngaySua = ngaySua;
        this.ngayXoa = ngayXoa;
        this.trangThai = trangThai;
        this.moTa = moTa;
        this.sanPhamId = sanPhamId;
        this.tenSanPham = tenSanPham;
        this.mauSacId = mauSacId;
        this.tenMauSac = tenMauSac;
        this.thuongHieuId = thuongHieuId;
        this.tenThuongHieu = tenThuongHieu;
        this.kichThuocId = kichThuocId;
        this.tenKichThuoc = tenKichThuoc;
        this.xuatXuId = xuatXuId;
        this.tenXuatXu = tenXuatXu;
        this.chatLieuId = chatLieuId;
        this.tenChatLieu = tenChatLieu;
        this.hinhAnhSpId = hinhAnhSpId;
        this.tenHinhAnhSp = tenHinhAnhSp;
        this.giaBanGoc = giaBanGoc;
        this.giamGia = giamGia;
    }


}
