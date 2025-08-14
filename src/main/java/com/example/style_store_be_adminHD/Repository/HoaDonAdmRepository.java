package com.example.style_store_be_adminHD.Repository;

import com.example.style_store_be_adminHD.Entity.HoaDonAdm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HoaDonAdmRepository extends JpaRepository<HoaDonAdm, Long> {
    Page<HoaDonAdm> findByMaContaining(String ma, Pageable pageable);

    @Query("SELECT hd FROM HoaDonAdm hd LEFT JOIN FETCH hd.chiTietHoaDon ct WHERE hd.id = :id")
    Optional<HoaDonAdm> findByIdWithChiTietHoaDon(Long id);
}
