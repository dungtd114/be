package com.example.style_store_be.repository.website;

import com.example.style_store_be.entity.XuatXu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface XuatXuRepository extends JpaRepository<XuatXu,Long> {
    List<XuatXu> findByTrangThai(int i, Sort ngayTao);
}
