package com.example.style_store_be.repository.website;

import com.example.style_store_be.entity.MauSacSp;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MauSacWebRepo extends JpaRepository<MauSacSp,Long> {
    List<MauSacSp> findByTrangThai(int i, Sort ngayTao);
}
