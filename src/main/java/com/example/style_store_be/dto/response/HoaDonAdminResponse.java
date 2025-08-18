package com.example.style_store_be.dto.response;

import jakarta.persistence.Column;
import lombok.*;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDonAdminResponse {
    private Long id;

    private String ptThanhToan;

    private String soDienThoaiKhachHang;

    private String ma;

    private String nguoiDatHang;

    private String nguoiNhanHang;

    private String diaChiNhanHang;

    private Integer tongSoLuongSp;

    private Double tongTien;

    private Double tienThue;

    private Date ngayDat;

    private Date ngayNhan;

    private Date ngayTao;

    private Date ngaySua;

    private Date ngayXoa;

    private Integer trangThai;

    private String moTa;

    private Integer trangThaiThanhToan;

    private String soDtNguoiNhan;

    private List<SanPhamHoaDonAdminResponse> sanPhams;

    private String tenNguoiGiaoHang;

    private String sdtNguoiGiaoHang;

    private Double tienKhachTra;

    private Double tienThua;

    public HoaDonAdminResponse(
            Long id,
            String ptThanhToan,
            String soDienThoaiKhachHang,
            String ma,
            String nguoiDatHang,
            String nguoiNhanHang,
            String diaChiNhanHang,
            Integer tongSoLuongSp,
            Double tongTien,
            Double tienThue,
            Date ngayDat,
            Date ngayNhan,
            Date ngayTao,
            Date ngaySua,
            Date ngayXoa,
            Integer trangThai,
            String moTa,
            Integer trangThaiThanhToan,
            String soDtNguoiNhan) {

        this.id = id;
        this.ptThanhToan = ptThanhToan;
        this.soDienThoaiKhachHang = soDienThoaiKhachHang;
        this.ma = ma;
        this.nguoiDatHang = nguoiDatHang;
        this.nguoiNhanHang = nguoiNhanHang;
        this.diaChiNhanHang = diaChiNhanHang;
        this.tongSoLuongSp = tongSoLuongSp;
        this.tongTien = tongTien;
        this.tienThue = tienThue;
        this.ngayDat = ngayDat;
        this.ngayNhan = ngayNhan;
        this.ngayTao = ngayTao;
        this.ngaySua = ngaySua;
        this.ngayXoa = ngayXoa;
        this.trangThai = trangThai;
        this.moTa = moTa;
        this.trangThaiThanhToan = trangThaiThanhToan;
        this.soDtNguoiNhan = soDtNguoiNhan;
    }

    public HoaDonAdminResponse(Long id, String ptThanhToan, String soDienThoaiKhachHang, String ma, String nguoiDatHang, String nguoiNhanHang, String diaChiNhanHang, Integer tongSoLuongSp, Double tongTien, Double tienThue, Date ngayDat, Date ngayNhan, Date ngayTao, Date ngaySua, Date ngayXoa, Integer trangThai, String moTa, Integer trangThaiThanhToan, String soDtNguoiNhan,  String tenNguoiGiaoHang, String sdtNguoiGiaoHang) {
        this.id = id;
        this.ptThanhToan = ptThanhToan;
        this.soDienThoaiKhachHang = soDienThoaiKhachHang;
        this.ma = ma;
        this.nguoiDatHang = nguoiDatHang;
        this.nguoiNhanHang = nguoiNhanHang;
        this.diaChiNhanHang = diaChiNhanHang;
        this.tongSoLuongSp = tongSoLuongSp;
        this.tongTien = tongTien;
        this.tienThue = tienThue;
        this.ngayDat = ngayDat;
        this.ngayNhan = ngayNhan;
        this.ngayTao = ngayTao;
        this.ngaySua = ngaySua;
        this.ngayXoa = ngayXoa;
        this.trangThai = trangThai;
        this.moTa = moTa;
        this.trangThaiThanhToan = trangThaiThanhToan;
        this.soDtNguoiNhan = soDtNguoiNhan;
        this.tenNguoiGiaoHang = tenNguoiGiaoHang;
        this.sdtNguoiGiaoHang = sdtNguoiGiaoHang;
    }
    public HoaDonAdminResponse(Long id, String ptThanhToan, String soDienThoaiKhachHang, String ma, String nguoiDatHang, String nguoiNhanHang, String diaChiNhanHang, Integer tongSoLuongSp, Double tongTien, Double tienThue, Date ngayDat, Date ngayNhan, Date ngayTao, Date ngaySua, Date ngayXoa, Integer trangThai, String moTa, Integer trangThaiThanhToan, String soDtNguoiNhan,  String tenNguoiGiaoHang, String sdtNguoiGiaoHang,Double tienKhachTra,Double tienThua) {
        this.id = id;
        this.ptThanhToan = ptThanhToan;
        this.soDienThoaiKhachHang = soDienThoaiKhachHang;
        this.ma = ma;
        this.nguoiDatHang = nguoiDatHang;
        this.nguoiNhanHang = nguoiNhanHang;
        this.diaChiNhanHang = diaChiNhanHang;
        this.tongSoLuongSp = tongSoLuongSp;
        this.tongTien = tongTien;
        this.tienThue = tienThue;
        this.ngayDat = ngayDat;
        this.ngayNhan = ngayNhan;
        this.ngayTao = ngayTao;
        this.ngaySua = ngaySua;
        this.ngayXoa = ngayXoa;
        this.trangThai = trangThai;
        this.moTa = moTa;
        this.trangThaiThanhToan = trangThaiThanhToan;
        this.soDtNguoiNhan = soDtNguoiNhan;
        this.tenNguoiGiaoHang = tenNguoiGiaoHang;
        this.sdtNguoiGiaoHang = sdtNguoiGiaoHang;
        this.tienKhachTra = tienKhachTra;
        this.tienThua = tienThua;
    }
}
