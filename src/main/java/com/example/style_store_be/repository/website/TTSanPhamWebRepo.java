package com.example.style_store_be.repository.website;

import com.example.style_store_be.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TTSanPhamWebRepo extends JpaRepository<SanPham,Long> {
}
