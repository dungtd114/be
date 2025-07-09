package com.example.style_store_be_adminSP.reposytory;

import com.example.style_store_be.entity.KichThuoc;
import com.example.style_store_be_adminSP.entity.ChatLieuAdm;
import com.example.style_store_be_adminSP.entity.KichThuocAdm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface KichThuocRepoAdm extends JpaRepository<KichThuocAdm,Long> {
    Optional<KichThuocAdm> findByTen(String ten);

    Optional<KichThuocAdm> findByMa(String ma);

    @Query("SELECT c FROM KichThuocAdm c WHERE LOWER(c.ten) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(c.ma) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<KichThuocAdm> findByTenOrMaContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);

    Page<KichThuocAdm> findByTenContainingIgnoreCase(String ten, Pageable pageable);

    Page<KichThuocAdm> findByNgayXoaIsNull(Pageable pageable);

    Page<KichThuocAdm> findByNgayXoaIsNotNull(Pageable pageable);

    Page<KichThuocAdm> findAllByOrderByNgayTaoDesc(Pageable pageable);
}
