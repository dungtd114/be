package com.example.style_store_be_adminSell.service;

import com.example.style_store_be_adminSP.dto.SanPhamCtDTOAdm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SanPhamCTSAdmService {
    Optional<SanPhamCtDTOAdm> findById(Long id);

    // Tìm sản phẩm chi tiết theo mã
    Optional<SanPhamCtDTOAdm> findByMa(String ma);

    // Tìm kiếm sản phẩm chi tiết theo tên sản phẩm với phân trang
    Page<SanPhamCtDTOAdm> searchBySanPhamTen(String ten, Pageable pageable);

    // Lấy danh sách sản phẩm chi tiết theo trạng thái với phân trang
    Page<SanPhamCtDTOAdm> findByTrangThai(Integer trangThai, Pageable pageable);

    // Lấy tất cả sản phẩm chi tiết với phân trang
    Page<SanPhamCtDTOAdm> findAll(Pageable pageable);
    SanPhamCtDTOAdm updateSoLuongSanPhamCT(Long id, Integer soLuong);

    // Tìm kiếm sản phẩm chi tiết theo ma sản phẩm với phân trang
    Page<SanPhamCtDTOAdm> searchBySanPhamMa(String ma, Pageable pageable);

    Page<SanPhamCtDTOAdm> filterByAttributes(
            Long sanPhamId,
            String sanPhamMa,
            String sanPhamTen,
            Long mauSacId,
            Long thuongHieuId,
            Long kichThuocId,
            Long xuatXuId,
            Long chatLieuId,
            Pageable pageable);
}
