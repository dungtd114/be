package com.example.style_store_be.repository.website;

import com.example.style_store_be.entity.HinhAnh;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HinhAnhSpRepo extends JpaRepository<HinhAnh,Long> {
    List<HinhAnh> findByTrangThai(int i, Sort ngayTao);
}
