package com.example.style_store_be_adminSP.reposytory;

import com.example.style_store_be_adminSP.entity.HinhAnhMauSacAdm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HinhAnhRepoAdm extends JpaRepository<HinhAnhMauSacAdm, Long> {
    Page<HinhAnhMauSacAdm> findByNgayXoaIsNull(Pageable pageable);
    Page<HinhAnhMauSacAdm> findByNgayXoaIsNotNull(Pageable pageable);
    Page<HinhAnhMauSacAdm> findByHinhAnhContainingIgnoreCase(String hinhAnh, Pageable pageable);

    Optional<HinhAnhMauSacAdm> findFirstByMauSacId(Long id);

    @Query("SELECT h FROM HinhAnhMauSacAdm h WHERE h.mauSac.id = :id AND h.ngayXoa IS NULL ORDER BY h.ngayTao DESC")
    Optional<HinhAnhMauSacAdm> findFirstByMauSacIdAndNotDeleted(@Param("id") Long id);

    @Query("SELECT h FROM HinhAnhMauSacAdm h WHERE h.mauSac.id = :id AND h.trangThai = 1 AND h.ngayXoa IS NULL")
    List<HinhAnhMauSacAdm> findByMauSacIdAndActive(@Param("id") Long id);
}