package com.example.style_store_be_adminSP.reposytory;

import com.example.style_store_be_adminSP.entity.SanPhamCtAdm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public interface ChiTietSPRepoAdm extends JpaRepository<SanPhamCtAdm, Long> {
    // Find by product code
    Optional<SanPhamCtAdm> findByMa(String ma);

    // Check existence by product code
    boolean existsByMa(String ma);

    // Find by product name containing
    @Query("SELECT spct FROM SanPhamCtAdm spct WHERE spct.sanPham.ten LIKE %:ten%")
    Page<SanPhamCtAdm> findBySanPhamTenContaining(@Param("ten") String ten, Pageable pageable);

    // Find by status
    Page<SanPhamCtAdm> findByTrangThai(Integer trangThai, Pageable pageable);

    // Find by sanPhamId
    Page<SanPhamCtAdm> findBySanPhamId(Long sanPhamId, Pageable pageable);

    // Find by mauSacId
    Page<SanPhamCtAdm> findByMauSacId(Long mauSacId, Pageable pageable);

    // Find by thuongHieuId
    Page<SanPhamCtAdm> findByThuongHieuId(Long thuongHieuId, Pageable pageable);

    // Find by kichThuocId
    Page<SanPhamCtAdm> findByKichThuocId(Long kichThuocId, Pageable pageable);

    // Find by xuatXuId
    Page<SanPhamCtAdm> findByXuatXuId(Long xuatXuId, Pageable pageable);

    // Find by chatLieuId
    Page<SanPhamCtAdm> findByChatLieuId(Long chatLieuId, Pageable pageable);

    // Find by sanPhamId and mauSacId
    @Query("SELECT spct FROM SanPhamCtAdm spct WHERE spct.sanPham.id = :sanPhamId AND spct.mauSac.id = :mauSacId")
    Page<SanPhamCtAdm> findBySanPhamIdAndMauSacId(@Param("sanPhamId") Long sanPhamId, @Param("mauSacId") Long mauSacId, Pageable pageable);

    // Find by thuongHieuId and mauSacId
    @Query("SELECT spct FROM SanPhamCtAdm spct WHERE spct.thuongHieu.id = :thuongHieuId AND spct.mauSac.id = :mauSacId")
    Page<SanPhamCtAdm> findByThuongHieuIdAndMauSacId(@Param("thuongHieuId") Long thuongHieuId, @Param("mauSacId") Long mauSacId, Pageable pageable);

    // Find by kichThuocId and chatLieuId
    @Query("SELECT spct FROM SanPhamCtAdm spct WHERE spct.kichThuoc.id = :kichThuocId AND spct.chatLieu.id = :chatLieuId")
    Page<SanPhamCtAdm> findByKichThuocIdAndChatLieuId(@Param("kichThuocId") Long kichThuocId, @Param("chatLieuId") Long chatLieuId, Pageable pageable);

    // Find by sanPhamId, thuongHieuId, and mauSacId
    @Query("SELECT spct FROM SanPhamCtAdm spct WHERE spct.sanPham.id = :sanPhamId AND spct.thuongHieu.id = :thuongHieuId AND spct.mauSac.id = :mauSacId")
    Page<SanPhamCtAdm> findBySanPhamIdAndThuongHieuIdAndMauSacId(@Param("sanPhamId") Long sanPhamId, @Param("thuongHieuId") Long thuongHieuId, @Param("mauSacId") Long mauSacId, Pageable pageable);

    // Find by price range
    @Query("SELECT spct FROM SanPhamCtAdm spct WHERE spct.giaBan BETWEEN :minPrice AND :maxPrice")
    Page<SanPhamCtAdm> findByGiaBanBetween(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice, Pageable pageable);

    // Find by quantity range
    @Query("SELECT spct FROM SanPhamCtAdm spct WHERE spct.soLuong BETWEEN :minQuantity AND :maxQuantity")
    Page<SanPhamCtAdm> findBySoLuongBetween(@Param("minQuantity") Integer minQuantity, @Param("maxQuantity") Integer maxQuantity, Pageable pageable);

    // Find by creation date range
    @Query("SELECT spct FROM SanPhamCtAdm spct WHERE spct.ngayTao BETWEEN :startDate AND :endDate")
    Page<SanPhamCtAdm> findByNgayTaoBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);

    // Find by multiple criteria
    @Query("SELECT spct FROM SanPhamCtAdm spct " +
            "WHERE (:sanPhamId IS NULL OR spct.sanPham.id = :sanPhamId) " +
            "AND (:thuongHieuId IS NULL OR spct.thuongHieu.id = :thuongHieuId) " +
            "AND (:mauSacId IS NULL OR spct.mauSac.id = :mauSacId) " +
            "AND (:kichThuocId IS NULL OR spct.kichThuoc.id = :kichThuocId) " +
            "AND (:xuatXuId IS NULL OR spct.xuatXu.id = :xuatXuId) " +
            "AND (:chatLieuId IS NULL OR spct.chatLieu.id = :chatLieuId) " +
            "AND (:minPrice IS NULL OR spct.giaBan >= :minPrice) " +
            "AND (:maxPrice IS NULL OR spct.giaBan <= :maxPrice) " +
            "AND (:minQuantity IS NULL OR spct.soLuong >= :minQuantity) " +
            "AND (:maxQuantity IS NULL OR spct.soLuong <= :maxQuantity) " +
            "AND (:startDate IS NULL OR spct.ngayTao >= :startDate) " +
            "AND (:endDate IS NULL OR spct.ngayTao <= :endDate)")
    Page<SanPhamCtAdm> findByMultipleCriteria(
            @Param("sanPhamId") Long sanPhamId,
            @Param("thuongHieuId") Long thuongHieuId,
            @Param("mauSacId") Long mauSacId,
            @Param("kichThuocId") Long kichThuocId,
            @Param("xuatXuId") Long xuatXuId,
            @Param("chatLieuId") Long chatLieuId,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("minQuantity") Integer minQuantity,
            @Param("maxQuantity") Integer maxQuantity,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );

    // Find by product name containing and status
    Page<SanPhamCtAdm> findBySanPhamTenContainingAndTrangThai(String ten, Integer trangThai, Pageable pageable);

    // Find by sanPhamId and status
    Page<SanPhamCtAdm> findBySanPhamIdAndTrangThai(Long sanPhamId, Integer trangThai, Pageable pageable);

    // Find by mauSacId and status
    Page<SanPhamCtAdm> findByMauSacIdAndTrangThai(Long mauSacId, Integer trangThai, Pageable pageable);
}