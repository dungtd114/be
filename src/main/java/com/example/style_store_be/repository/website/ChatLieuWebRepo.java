package com.example.style_store_be.repository.website;

import com.example.style_store_be.entity.ChatLieu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatLieuWebRepo extends JpaRepository<ChatLieu,Long> {
    List<ChatLieu> findByTrangThai(int i, Sort ngayTao);
}
