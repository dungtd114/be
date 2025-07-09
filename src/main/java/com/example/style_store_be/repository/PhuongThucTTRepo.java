package com.example.style_store_be.repository;

import com.example.style_store_be.entity.PtThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhuongThucTTRepo extends JpaRepository<PtThanhToan ,Long> {
}
