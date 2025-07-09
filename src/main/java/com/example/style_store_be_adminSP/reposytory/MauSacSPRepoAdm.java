package com.example.style_store_be_adminSP.reposytory;

import com.example.style_store_be_adminSP.entity.MauSacSpAdm;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MauSacSPRepoAdm extends JpaRepository<MauSacSpAdm, Long> {
    Optional<MauSacSpAdm> findByTen(String ten);
    Optional<MauSacSpAdm> findByMa(String ma);
    @Query("SELECT m FROM MauSacSpAdm m WHERE " +
            "(:keyword IS NULL OR " +
            "LOWER(m.ten) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(m.ma) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "ORDER BY m.ngayTao DESC")
    Page<MauSacSpAdm> searchAllByKeyword(@Param("keyword") String keyword, Pageable pageable);

    Page<MauSacSpAdm> findByTenContainingIgnoreCase(String ten, Pageable pageable);
    Page<MauSacSpAdm> findByNgayXoaIsNull(Pageable pageable);
    Page<MauSacSpAdm> findByNgayXoaIsNotNull(Pageable pageable);
    Page<MauSacSpAdm> findAllByOrderByNgayTaoDesc(Pageable pageable);


}