package com.example.style_store_be_adminSP.service;

import com.example.style_store_be_adminSP.dto.SanPhamCtDTOAdm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SanPhamCtServiceAdm {
    // Thêm mới sản phẩm chi tiết
    SanPhamCtDTOAdm addSanPhamCt(SanPhamCtDTOAdm sanPhamCtDTO);

    // Cập nhật sản phẩm chi tiết
    SanPhamCtDTOAdm updateSanPhamCt(Long id, SanPhamCtDTOAdm sanPhamCtDTO);

    // Xóa (soft delete) sản phẩm chi tiết
    void deleteSanPhamCt(Long id);

    // Tìm sản phẩm chi tiết theo ID
    Optional<SanPhamCtDTOAdm> findById(Long id);

    // Tìm sản phẩm chi tiết theo mã
    Optional<SanPhamCtDTOAdm> findByMa(String ma);

    // Tìm kiếm sản phẩm chi tiết theo tên sản phẩm với phân trang
    Page<SanPhamCtDTOAdm> searchBySanPhamTen(String ten, Pageable pageable);

    // Lấy danh sách sản phẩm chi tiết theo trạng thái với phân trang
    Page<SanPhamCtDTOAdm> findByTrangThai(Integer trangThai, Pageable pageable);

    // Lấy tất cả sản phẩm chi tiết với phân trang
    Page<SanPhamCtDTOAdm> findAll(Pageable pageable);

    // Lấy danh sách sản phẩm chi tiết theo id_san_pham với phân trang
    Page<SanPhamCtDTOAdm> findBySanPhamId(Long sanPhamId, Pageable pageable);

    // Tìm sản phẩm chi tiết theo id_mau_sac
    Page<SanPhamCtDTOAdm> findByMauSacId(Long mauSacId, Pageable pageable);
}