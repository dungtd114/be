package com.example.style_store_be_adminSP.service;

import com.example.style_store_be_adminSP.dto.SanPhamCtDTOAdm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SanPhamCtServiceAdm {
    SanPhamCtDTOAdm addSanPhamCt(SanPhamCtDTOAdm sanPhamCtDTO);
    SanPhamCtDTOAdm updateSanPhamCt(Long id, SanPhamCtDTOAdm sanPhamCtDTO);
    void deleteSanPhamCt(Long id);
    Optional<SanPhamCtDTOAdm> findById(Long id);
    Optional<SanPhamCtDTOAdm> findByMa(String ma);
    Page<SanPhamCtDTOAdm> searchBySanPhamMa(String ma, Pageable pageable);
    Page<SanPhamCtDTOAdm> findByTrangThai(Integer trangThai, Pageable pageable);
    Page<SanPhamCtDTOAdm> findAll(int page, int size);
    Page<SanPhamCtDTOAdm> findBySanPhamId(Long sanPhamId, Pageable pageable);
    Page<SanPhamCtDTOAdm> findByMauSacId(Long mauSacId, Pageable pageable);
    // Updated method to filter by required attributes only
    Page<SanPhamCtDTOAdm> filterByAttributes(
            Long sanPhamId,
            Long mauSacId,
            Long thuongHieuId,
            Long kichThuocId,
            Long xuatXuId,
            Long chatLieuId,
            Pageable pageable);
}