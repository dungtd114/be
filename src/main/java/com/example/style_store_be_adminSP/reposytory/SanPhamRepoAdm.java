package com.example.style_store_be_adminSP.reposytory;

import com.example.style_store_be.entity.SanPham;
import com.example.style_store_be_adminSP.entity.SanPhamAdm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SanPhamRepoAdm extends JpaRepository<SanPhamAdm, Long> {
    Page<SanPhamAdm> findByNgayXoaIsNull(Pageable pageable);
    Page<SanPhamAdm> findByNgayXoaIsNotNull(Pageable pageable);
    Optional<SanPhamAdm> findByTen(String ten);
    Page<SanPhamAdm> findAllByOrderByNgayTaoDesc(Pageable pageable);

    @Query("SELECT sp FROM SanPhamAdm sp WHERE sp.ngayXoa IS NULL ORDER BY sp.ngayTao DESC")
    Page<SanPhamAdm> findAllActiveByOrderByNgayTaoDesc(Pageable pageable);

    @Query("SELECT sp AS sanPham, " +
            "COALESCE(SUM(CASE WHEN ctsp.trangThai IN (1, 0) THEN ctsp.soLuong ELSE 0 END), 0) AS totalQuantity " +
            "FROM SanPhamAdm sp " +
            "LEFT JOIN ChiTietSanPham ctsp ON sp.id = ctsp.sanPham.id " +
            "WHERE (:search IS NULL OR " +
            "LOWER(sp.ten) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(sp.ma) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "GROUP BY sp " +
            "ORDER BY sp.ngayTao DESC")
    Page<Object[]> findSanPhamWithTotalQuantityAndOrderByNgayTaoDesc(@Param("search") String search, Pageable pageable);


    Optional<SanPhamAdm> findByMa(String ma);
}