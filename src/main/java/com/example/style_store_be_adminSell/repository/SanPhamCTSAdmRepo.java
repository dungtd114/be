package com.example.style_store_be_adminSell.repository;

import com.example.style_store_be_adminSP.entity.SanPhamCtAdm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SanPhamCTSAdmRepo extends JpaRepository<SanPhamCtAdm, Long> {
    // Find by product code
    Optional<SanPhamCtAdm> findByMa(String ma);

    // Check existence by product code
    boolean existsByMa(String ma);

    // Find by product code containing
    @Query("SELECT spct FROM SanPhamCtAdm spct WHERE spct.sanPham.ma LIKE CONCAT('%', :ma, '%')")
    Page<SanPhamCtAdm> findBySanPhamMaContaining(@Param("ma") String ma, Pageable pageable);

    // Find by product name containing
    @Query("SELECT spct FROM SanPhamCtAdm spct WHERE spct.sanPham.ten LIKE CONCAT('%', :ten, '%')")
    Page<SanPhamCtAdm> findBySanPhamTenContaining(@Param("ten") String ten, Pageable pageable);

    // Find by status
    Page<SanPhamCtAdm> findByTrangThai(Integer trangThai, Pageable pageable);

    // Find by attributes with condition that SanPhamAdm must have trangThai = 1
    @Query("SELECT spct FROM SanPhamCtAdm spct " +
            "WHERE spct.trangThai = 1 " +
            "AND spct.sanPham.trangThai = 1 " +
            "AND (" +
            "  (:sanPhamMa IS NULL OR LOWER(spct.ma) LIKE LOWER(CONCAT('%', :sanPhamMa, '%'))) " +
            "  OR (:sanPhamTen IS NULL OR LOWER(spct.sanPham.ten) LIKE LOWER(CONCAT('%', :sanPhamTen, '%'))) " +
            ") " +
            "AND (:sanPhamId IS NULL OR spct.sanPham.id = :sanPhamId) " +
            "AND (:mauSacId IS NULL OR spct.mauSac.id = :mauSacId) " +
            "AND (:thuongHieuId IS NULL OR spct.thuongHieu.id = :thuongHieuId) " +
            "AND (:kichThuocId IS NULL OR spct.kichThuoc.id = :kichThuocId) " +
            "AND (:xuatXuId IS NULL OR spct.xuatXu.id = :xuatXuId) " +
            "AND (:chatLieuId IS NULL OR spct.chatLieu.id = :chatLieuId)")
    Page<SanPhamCtAdm> findByAttributes(
            @Param("sanPhamId") Long sanPhamId,
            @Param("sanPhamMa") String sanPhamMa,
            @Param("sanPhamTen") String sanPhamTen,
            @Param("mauSacId") Long mauSacId,
            @Param("thuongHieuId") Long thuongHieuId,
            @Param("kichThuocId") Long kichThuocId,
            @Param("xuatXuId") Long xuatXuId,
            @Param("chatLieuId") Long chatLieuId,
            Pageable pageable
    );

}
