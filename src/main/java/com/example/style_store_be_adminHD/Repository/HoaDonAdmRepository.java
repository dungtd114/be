package com.example.style_store_be_adminHD.Repository;

import com.example.style_store_be_adminHD.Entity.HoaDonAdm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoaDonAdmRepository extends JpaRepository<HoaDonAdm, Long> {
    Page<HoaDonAdm> findByMaContaining(String ma, Pageable pageable);
}
