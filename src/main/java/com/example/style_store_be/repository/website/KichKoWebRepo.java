package com.example.style_store_be.repository.website;

import com.example.style_store_be.entity.KichThuoc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KichKoWebRepo extends JpaRepository<KichThuoc,Long> {
}
