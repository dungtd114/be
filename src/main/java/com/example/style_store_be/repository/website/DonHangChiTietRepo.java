package com.example.style_store_be.repository.website;

import com.example.style_store_be.dto.response.SanPhamHoaDonAdminResponse;
import com.example.style_store_be.entity.HoaDon;
import com.example.style_store_be.entity.HoaDonCt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonHangChiTietRepo extends JpaRepository<HoaDonCt,Long> {
    List<HoaDonCt> findByHoaDon(HoaDon hoaDon);

    List<HoaDonCt> findByHoaDonId(Long id);
}
