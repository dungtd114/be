package com.example.style_store_be_adminSP.reposytory;

import com.example.style_store_be.entity.ThuongHieu;
import com.example.style_store_be_adminSP.entity.ThuongHieuAdm;
import com.example.style_store_be_adminSP.entity.XuatXuAdm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ThuongHieuRepoAdm extends JpaRepository<ThuongHieuAdm,Long> {
    Optional<ThuongHieuAdm> findByTen(String ten);

    Optional<ThuongHieuAdm> findByMa(String ma);

    @Query("SELECT c FROM ThuongHieuAdm c WHERE LOWER(c.ten) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(c.ma) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<ThuongHieuAdm> findByTenOrMaContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);

    Page<ThuongHieuAdm> findByTenContainingIgnoreCase(String ten, Pageable pageable);

    Page<ThuongHieuAdm> findByNgayXoaIsNull(Pageable pageable);

    Page<ThuongHieuAdm> findByNgayXoaIsNotNull(Pageable pageable);

    Page<ThuongHieuAdm> findAllByOrderByNgayTaoDesc(Pageable pageable);
}
