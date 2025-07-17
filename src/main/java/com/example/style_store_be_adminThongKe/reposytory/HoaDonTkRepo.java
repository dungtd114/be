package com.example.style_store_be_adminThongKe.reposytory;

import com.example.style_store_be_adminThongKe.entity.HoaDonTK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HoaDonTkRepo extends JpaRepository<HoaDonTK, Long> {

    @Query(value = "SELECT CONVERT(VARCHAR(10), h.ngay_dat, 120) AS ngay, SUM(h.tong_tien) AS tongDoanhThu " +
            "FROM hoa_don h WHERE h.trang_thai = 1 " +
            "GROUP BY CONVERT(VARCHAR(10), h.ngay_dat, 120) " +
            "ORDER BY ngay DESC", nativeQuery = true)
    List<Object[]> thongKeDoanhThuTheoNgay();

    @Query(value = "SELECT CONVERT(VARCHAR(10), h.ngay_dat, 120) AS ngay, SUM(h.tong_tien) AS tongDoanhThu " +
            "FROM hoa_don h WHERE h.trang_thai = 1 AND CONVERT(VARCHAR(10), h.ngay_dat, 120) = :date " +
            "GROUP BY CONVERT(VARCHAR(10), h.ngay_dat, 120)", nativeQuery = true)
    List<Object[]> thongKeDoanhThuTheoNgay(@Param("date") String date);

    @Query(value = "SELECT YEAR(h.ngay_dat) AS nam, DATEPART(WEEK, h.ngay_dat) AS tuan, SUM(h.tong_tien) AS tongDoanhThu " +
            "FROM hoa_don h WHERE h.trang_thai = 1 " +
            "GROUP BY YEAR(h.ngay_dat), DATEPART(WEEK, h.ngay_dat) " +
            "ORDER BY nam DESC, tuan DESC", nativeQuery = true)
    List<Object[]> thongKeDoanhThuTheoTuan();

    @Query(value = "SELECT YEAR(h.ngay_dat) AS nam, MONTH(h.ngay_dat) AS thang, SUM(h.tong_tien) AS tongDoanhThu " +
            "FROM hoa_don h WHERE h.trang_thai = 1 " +
            "GROUP BY YEAR(h.ngay_dat), MONTH(h.ngay_dat) " +
            "ORDER BY nam DESC, thang DESC", nativeQuery = true)
    List<Object[]> thongKeDoanhThuTheoThang();

    @Query(value = "SELECT YEAR(h.ngay_dat) AS nam, SUM(h.tong_tien) AS tongDoanhThu " +
            "FROM hoa_don h WHERE h.trang_thai = 1 " +
            "GROUP BY YEAR(h.ngay_dat) " +
            "ORDER BY nam DESC", nativeQuery = true)
    List<Object[]> thongKeDoanhThuTheoNam();
}
