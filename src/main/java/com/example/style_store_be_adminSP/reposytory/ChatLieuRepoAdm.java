package com.example.style_store_be_adminSP.reposytory;
import com.example.style_store_be_adminSP.entity.ChatLieuAdm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatLieuRepoAdm extends JpaRepository<ChatLieuAdm, Long> {
    Optional<ChatLieuAdm> findByTen(String ten);

    Optional<ChatLieuAdm> findByMa(String ma);

    @Query("SELECT c FROM ChatLieuAdm c WHERE LOWER(c.ten) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(c.ma) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<ChatLieuAdm> findByTenOrMaContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);

    Page<ChatLieuAdm> findByTenContainingIgnoreCase(String ten, Pageable pageable);

    Page<ChatLieuAdm> findByNgayXoaIsNull(Pageable pageable);

    Page<ChatLieuAdm> findByNgayXoaIsNotNull(Pageable pageable);

    Page<ChatLieuAdm> findAllByOrderByNgayTaoDesc(Pageable pageable);
}
