package com.example.style_store_be.repository.website;

import com.example.style_store_be.dto.HoaDonCtDto;
import com.example.style_store_be.dto.LichSuDonHangDto;
import com.example.style_store_be.entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface DonHangRepoSitory extends JpaRepository<HoaDon,Long> {
    @Query("""
    SELECT hd
    FROM HoaDon hd
    WHERE hd.khachHang.id = :userId
      AND (:trangThaiDonHang IS NULL OR hd.trangThai = :trangThaiDonHang)
      AND (:trangThaiThanhToan IS NULL OR hd.trangThaiThanhToan = :trangThaiThanhToan)
      AND (:phuongThucThanhToan IS NULL OR hd.thanhToan.ten = :phuongThucThanhToan)
      AND (:maDonHang IS NULL OR hd.ma LIKE %:maDonHang%)
      AND (:tuNgay IS NULL OR hd.ngayDat >= :tuNgay)
      AND (:denNgay IS NULL OR hd.ngayDat <= :denNgay)
      AND (
          :tenSanPham IS NULL OR EXISTS (
              SELECT 1 FROM HoaDonCt ct 
              JOIN ct.sanPhamCt ctsp 
              JOIN ctsp.sanPham sp 
              WHERE ct.hoaDon.id = hd.id AND sp.ten LIKE %:tenSanPham%
          )
      )
    ORDER BY hd.ngayDat DESC
""")
    Page<HoaDon> filterLichSuHoaDon(
            @Param("userId") Long userId,
            @Param("trangThaiDonHang") Integer trangThaiDonHang,
            @Param("trangThaiThanhToan") Integer trangThaiThanhToan,
            @Param("phuongThucThanhToan") Integer phuongThucThanhToan,
            @Param("maDonHang") String maDonHang,
            @Param("tenSanPham") String tenSanPham,
            @Param("tuNgay") Date tuNgay,
            @Param("denNgay") Date denNgay,
            Pageable pageable
    );

    @Query("""
        SELECT new com.example.style_store_be.dto.HoaDonCtDto(
            ct.id,
            ct.sanPhamCt.hinhAnhSp.hinhAnh,
            ct.tenSanPham,
            ct.giaTien,
            ct.soLuong,
            ct.thanhTien
        )
        FROM HoaDonCt ct
        WHERE ct.hoaDon.id = :hoaDonId
          AND (:tenSanPham IS NULL OR LOWER(ct.tenSanPham) LIKE LOWER(CONCAT('%', :tenSanPham, '%')))
    """)
    List<HoaDonCtDto> getChiTietByHoaDonIdAndTenSanPham(
            @Param("hoaDonId") Long hoaDonId,
            @Param("tenSanPham") String tenSanPham
    );}
