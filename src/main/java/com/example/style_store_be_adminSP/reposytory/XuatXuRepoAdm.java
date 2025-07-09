package com.example.style_store_be_adminSP.reposytory;

import com.example.style_store_be.entity.XuatXu;
import com.example.style_store_be_adminSP.entity.ChatLieuAdm;
import com.example.style_store_be_adminSP.entity.XuatXuAdm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface XuatXuRepoAdm extends JpaRepository<XuatXuAdm,Long> {
    Optional<XuatXuAdm> findByTen(String ten);

    Optional<XuatXuAdm> findByMa(String ma);

    @Query("SELECT c FROM XuatXuAdm c WHERE LOWER(c.ten) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(c.ma) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<XuatXuAdm> findByTenOrMaContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);

    Page<XuatXuAdm> findByTenContainingIgnoreCase(String ten, Pageable pageable);

    Page<XuatXuAdm> findByNgayXoaIsNull(Pageable pageable);

    Page<XuatXuAdm> findByNgayXoaIsNotNull(Pageable pageable);

    Page<XuatXuAdm> findAllByOrderByNgayTaoDesc(Pageable pageable);
}
