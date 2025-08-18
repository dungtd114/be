package com.example.style_store_be.repository.website;

import com.example.style_store_be.entity.SanPham;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TTSanPhamWebRepo extends JpaRepository<SanPham,Long> {
    List<SanPham> findByTrangThai(int i, Sort ngayTao);
}
