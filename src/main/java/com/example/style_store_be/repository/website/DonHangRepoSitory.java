package com.example.style_store_be.repository.website;

import com.example.style_store_be.dto.HoaDonAdminDto;
import com.example.style_store_be.dto.HoaDonCtDto;
import com.example.style_store_be.dto.LichSuDonHangDto;
import com.example.style_store_be.dto.response.HoaDonAdminResponse;
import com.example.style_store_be.dto.response.SanPhamHoaDonAdminResponse;
import com.example.style_store_be.entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        spct.hinhAnhSp.hinhAnh,
        ct.tenSanPham,
        ct.giaTien,
        ct.soLuong,
        ct.thanhTien,
        spct.mauSac.ten,
        spct.chatLieu.ten,
        spct.thuongHieu.ten,
        spct.kichThuoc.ten
    )
    FROM HoaDonCt ct JOIN ct.sanPhamCt spct
    WHERE ct.hoaDon.id = :hoaDonId
    AND (:tenSanPham IS NULL OR LOWER(ct.tenSanPham) LIKE LOWER(CONCAT('%', :tenSanPham, '%')))
""")
    List<HoaDonCtDto> getChiTietByHoaDonIdAndTenSanPham(
            @Param("hoaDonId") Long hoaDonId,
            @Param("tenSanPham") String tenSanPham
    );

    @Query("SELECT new com.example.style_store_be.dto.HoaDonAdminDto(" +
            "h.id, " +
            "nt.hoTen, " + // nguoiTao
            "nx.hoTen, " + // nguoiXuat
            "ptt.ten, " +  // ptThanhToan (giả sử PtThanhToan có trường ten)
            "kh.soDienThoai, " + // soDienThoaiKhachHang
            "h.ma, " +
            "h.nguoiDatHang, " +
            "h.nguoiNhanHang, " +
            "h.diaChiNhanHang, " +
            "h.tongSoLuongSp, " +
            "h.tongTien, " +
            "h.tienThue, " +
            "h.ngayDat, " +
            "h.ngayNhan, " +
            "h.ngayTao, " +
            "h.ngaySua, " +
            "h.ngayXoa, " +
            "h.trangThai, " +
            "h.moTa, " +
            "h.trangThaiThanhToan, " +
            "h.soDtNguoiNhan," +
            "ptt.id) " +
            "FROM HoaDon h " +
            "LEFT JOIN h.nguoiTao nt " +
            "LEFT JOIN h.nguoiXuat nx " +
            "LEFT JOIN h.thanhToan ptt " +
            "LEFT JOIN h.khachHang kh " +
            "WHERE (:search IS NULL OR h.ma LIKE %:search% OR h.nguoiDatHang LIKE %:search% OR h.soDtNguoiNhan LIKE %:search%) " +
            "AND (:trangThaiDonHang IS NULL OR h.trangThai = :trangThaiDonHang) " +
            "AND (:trangThaiThanhToan IS NULL OR h.trangThaiThanhToan = :trangThaiThanhToan) " +
            "AND (:phuongThucThanhToan IS NULL OR ptt.id = :phuongThucThanhToan) " +
            "ORDER BY h.ngayDat DESC")
    Page<HoaDonAdminDto> getPageHoaDonAdmin(
            @Param("search") String maHoaDonOrTenKhachHang0rSoDienThoai,
            @Param("trangThaiDonHang") Integer trangThaiDonHang,
            @Param("trangThaiThanhToan") Integer trangThaiThanhToan,
            @Param("phuongThucThanhToan") Integer phuongThucThanhToan,
            Pageable pageable);




    @Query("SELECT new com.example.style_store_be.dto.response.HoaDonAdminResponse(" +
            "hd.id, " +
            "COALESCE(tt.ten, ''), " +
            "COALESCE(kh.soDienThoai, ''), " +
            "hd.ma, " +
            "hd.nguoiDatHang, " +
            "hd.nguoiNhanHang, " +
            "hd.diaChiNhanHang, " +
            "hd.tongSoLuongSp, " +
            "hd.tongTien, " +
            "hd.tienThue, " +
            "hd.ngayDat, " +
            "hd.ngayNhan, " +
            "hd.ngayTao, " +
            "hd.ngaySua, " +
            "hd.ngayXoa, " +
            "hd.trangThai, " +
            "hd.moTa, " +
            "hd.trangThaiThanhToan, " +
            "hd.soDtNguoiNhan, " +
            "hd.tenNguoiGiaoHang, " +
            "hd.sdtNguoiGiaoHang, " +
            "hd.tienKhachTra, " +
            "hd.tienThua) " +
            "FROM HoaDon hd " +
            "LEFT JOIN hd.thanhToan tt " +
            "LEFT JOIN hd.khachHang kh " +
            "WHERE hd.id = :id")
    Optional<HoaDonAdminResponse> findHoaDonAdminResponseById(@Param("id") Long id);


    @Query("SELECT new com.example.style_store_be.dto.response.SanPhamHoaDonAdminResponse(" +
                "hdct.id, " +  // idHoaDonCt
                "hdct.sanPhamCt.id, " +  // idSanPham
                "hdct.tenSanPham, " +
                "hdct.sanPhamCt.hinhAnhSp.hinhAnh, " +  // hinhAnhSanPham
                "CAST(hdct.giaTien AS string), " +  // giaBanSanPham
                "CAST(hdct.sanPhamCt.giaBanGoc AS string), " +  // giaBanGocSanPham
                "hdct.sanPhamCt.thuongHieu.ten, " +  // thuongHieuSanPham
                "hdct.sanPhamCt.mauSac.ten, " +  // mauSacSanPham
                "hdct.sanPhamCt.chatLieu.ten, " +  // chatLieuSanPham
                "hdct.soLuong) " +
                "FROM HoaDonCt hdct " +
                "WHERE hdct.hoaDon.id = :hoaDonId")
        List<SanPhamHoaDonAdminResponse> findSanPhamByHoaDonId(@Param("hoaDonId") Long hoaDonId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE hoa_don SET trang_thai = :trangThai WHERE id = :id", nativeQuery = true)
    int updateTrangThaiHoaDon(
            @Param("id") Long id,
            @Param("trangThai") int nextTrangThai
    );

}

