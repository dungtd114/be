package com.example.style_store_be_adminThongKe.reposytory;

import com.example.style_store_be_adminThongKe.DTO.SanPhamBanChayDTO;
import com.example.style_store_be_adminThongKe.entity.HoaDonTK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonTkRepo extends JpaRepository<HoaDonTK, Long> {

    // Thống kê theo tháng - năm
    @Query(value = """
        SELECT 
            sp.ten AS tenSanPhamTK,
            spct.ma AS maSanPhamCtTK,
            SUM(hdct.so_luong) AS tongSoLuongBanTK,
            SUM(hdct.thanh_tien) AS tongTienBanTK,
            hams.hinh_anh AS urlHinhAnhMauSac
        FROM hoa_don_ct hdct
        JOIN hoa_don hd ON hd.id = hdct.id_hoa_don
        JOIN san_pham_ct spct ON spct.id = hdct.id_san_pham_ct
        JOIN san_pham sp ON sp.id = spct.id_san_pham
        LEFT JOIN hinh_anh_mau_sac hams ON spct.id_hinh_anh_mau_sac = hams.id
        WHERE hd.trang_thai = 3
          AND MONTH(hd.ngay_dat) = ?1
          AND YEAR(hd.ngay_dat) = ?2
        GROUP BY sp.ten, spct.ma, hams.hinh_anh
        ORDER BY tongSoLuongBanTK DESC
    """, nativeQuery = true)
    List<SanPhamBanChayDTO> thongKeTheoThang(int thang, int nam);

    // Thống kê theo năm
    @Query(value = """
        SELECT 
            sp.ten AS tenSanPhamTK,
            spct.ma AS maSanPhamCtTK,
            SUM(hdct.so_luong) AS tongSoLuongBanTK,
            SUM(hdct.thanh_tien) AS tongTienBanTK,
            hams.hinh_anh AS urlHinhAnhMauSac
        FROM hoa_don_ct hdct
        JOIN hoa_don hd ON hd.id = hdct.id_hoa_don
        JOIN san_pham_ct spct ON spct.id = hdct.id_san_pham_ct
        JOIN san_pham sp ON sp.id = spct.id_san_pham
        LEFT JOIN hinh_anh_mau_sac hams ON spct.id_hinh_anh_mau_sac = hams.id
        WHERE hd.trang_thai = 3
          AND YEAR(hd.ngay_dat) = ?1
        GROUP BY sp.ten, spct.ma, hams.hinh_anh
        ORDER BY tongSoLuongBanTK DESC
    """, nativeQuery = true)
    List<SanPhamBanChayDTO> thongKeTheoNam(int nam);

    // Thống kê theo tuần - năm
    @Query(value = """
        SELECT 
            sp.ten AS tenSanPhamTK,
            spct.ma AS maSanPhamCtTK,
            SUM(hdct.so_luong) AS tongSoLuongBanTK,
            SUM(hdct.thanh_tien) AS tongTienBanTK,
            hams.hinh_anh AS urlHinhAnhMauSac
        FROM hoa_don_ct hdct
        JOIN hoa_don hd ON hd.id = hdct.id_hoa_don
        JOIN san_pham_ct spct ON spct.id = hdct.id_san_pham_ct
        JOIN san_pham sp ON sp.id = spct.id_san_pham
        LEFT JOIN hinh_anh_mau_sac hams ON spct.id_hinh_anh_mau_sac = hams.id
        WHERE hd.trang_thai = 3
          AND DATEPART(WEEK, hd.ngay_dat) = ?1
          AND YEAR(hd.ngay_dat) = ?2
        GROUP BY sp.ten, spct.ma, hams.hinh_anh
        ORDER BY tongSoLuongBanTK DESC
    """, nativeQuery = true)
    List<SanPhamBanChayDTO> thongKeTheoTuan(int tuan, int nam);
}
