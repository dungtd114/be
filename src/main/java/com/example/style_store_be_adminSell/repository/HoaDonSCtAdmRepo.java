package com.example.style_store_be_adminSell.repository;

import com.example.style_store_be_adminSell.entity.HoaDonCTSAdm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoaDonSCtAdmRepo extends JpaRepository<HoaDonCTSAdm, Long> {
    Page<HoaDonCTSAdm> findByHoaDon_Id(Long hoaDonId, Pageable pageable);
}
