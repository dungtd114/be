package com.example.style_store_be.repository.website;

import com.example.style_store_be.entity.KichThuoc;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KichKoWebRepo extends JpaRepository<KichThuoc,Long> {
    List<KichThuoc> findByTrangThai(int i, Sort ngayTao);
}
