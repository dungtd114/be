package com.example.style_store_be.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String ma;
    private String hoTen;
    private String soDienThoai;
    private String email;
    private String cccd;
    private String diaChi;
    private Integer gioiTinh;
    private Date namSinh;
    private String tenDangNhap;
    private String matKhau;
    private Date ngayTao;
    private Date ngaySua;
    private Date ngayXoa;
    private Integer trangThai;
    private Long idChucVu;
    private String tinh;
    private String huyen;
    private String xa;

    public UserResponse(Long id, String ma, String hoTen, String soDienThoai, String email, String cccd, String diaChi, Integer gioiTinh, Date namSinh, String tenDangNhap, String matKhau, Date ngayTao, Date ngaySua, Date ngayXoa, Integer trangThai, Long idChucVu) {
        this.id = id;
        this.ma = ma;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.cccd = cccd;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.namSinh = namSinh;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.ngayTao = ngayTao;
        this.ngaySua = ngaySua;
        this.ngayXoa = ngayXoa;
        this.trangThai = trangThai;
        this.idChucVu = idChucVu;
    }
}
