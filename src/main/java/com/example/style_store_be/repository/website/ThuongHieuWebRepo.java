package com.example.style_store_be.repository.website;

import com.example.style_store_be.entity.ThuongHieu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThuongHieuWebRepo extends JpaRepository<ThuongHieu,Long> {
    List<ThuongHieu> findByTrangThai(int i, Sort ngayTao);
}
