package com.example.style_store_be_adminSP.reposytory;

import com.example.style_store_be_adminSP.entity.SanPhamAdm;
import com.example.style_store_be_adminSP.entity.SanPhamCtAdm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChiTietSPRepoAdm extends JpaRepository<SanPhamCtAdm, Long> {
    // Find by product code
    Optional<SanPhamCtAdm> findByMa(String ma);
    Page<SanPhamCtAdm> findAllByOrderByNgayTaoDesc(Pageable pageable);
    Page<SanPhamCtAdm> findBySanPhamIdOrderByNgayTaoDesc(Long sanPhamId, Pageable pageable);

    // Check existence by product code
    boolean existsByMa(String ma);

    // Find by product name containing
    @Query("SELECT spct FROM SanPhamCtAdm spct WHERE spct.sanPham.ma LIKE CONCAT('%', :ma, '%')")
    Page<SanPhamCtAdm> findBySanPhamMaContaining(@Param("ma") String ma, Pageable pageable);

    // Find by status
    Page<SanPhamCtAdm> findByTrangThai(Integer trangThai, Pageable pageable);

    // Find by sanPhamId
    Page<SanPhamCtAdm> findBySanPhamId(Long sanPhamId, Pageable pageable);

    // Find by mauSacId
    Page<SanPhamCtAdm> findByMauSacId(Long mauSacId, Pageable pageable);

    @Query("SELECT spct FROM SanPhamCtAdm spct " +
            "WHERE (:sanPhamId IS NULL OR spct.sanPham.id = :sanPhamId) " +
            "AND (:mauSacId IS NULL OR spct.mauSac.id = :mauSacId) " +
            "AND (:thuongHieuId IS NULL OR spct.thuongHieu.id = :thuongHieuId) " +
            "AND (:kichThuocId IS NULL OR spct.kichThuoc.id = :kichThuocId) " +
            "AND (:xuatXuId IS NULL OR spct.xuatXu.id = :xuatXuId) " +
            "AND (:chatLieuId IS NULL OR spct.chatLieu.id = :chatLieuId)")
    Page<SanPhamCtAdm> findByAttributes(
            @Param("sanPhamId") Long sanPhamId,
            @Param("mauSacId") Long mauSacId,
            @Param("thuongHieuId") Long thuongHieuId,
            @Param("kichThuocId") Long kichThuocId,
            @Param("xuatXuId") Long xuatXuId,
            @Param("chatLieuId") Long chatLieuId,
            Pageable pageable);

    Optional<SanPhamCtAdm> findBySanPhamIdAndMauSacIdAndThuongHieuIdAndKichThuocIdAndXuatXuIdAndChatLieuId(
            Long sanPhamId,
            Long mauSacId,
            Long thuongHieuId,
            Long kichThuocId,
            Long xuatXuId,
            Long chatLieuId
    );
    @Modifying
    @Query("UPDATE SanPhamCtAdm spct SET spct.trangThai = :trangThaiMoi WHERE spct.sanPham.id = :idSanPham")
    void updateTrangThaiBySanPham(@Param("idSanPham") Long idSanPham, @Param("trangThaiMoi") Integer trangThaiMoi);


}