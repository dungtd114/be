package com.example.style_store_be_adminSell.repository;

import com.example.style_store_be_adminSell.entity.HoaDonSAdm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface HoaDonSAdmRepo extends JpaRepository<HoaDonSAdm, Long> {

    boolean existsByMa(String ma);

    Page<HoaDonSAdm> findByTrangThai(Integer trangThai, Pageable pageable);

    HoaDonSAdm findByMa(String ma);

    @Query("SELECT h FROM HoaDonSAdm h WHERE h.ngayNhan >= :fromDate AND h.trangThai = 1")
    List<HoaDonSAdm> findHoaDonTrongThangVaTrangThai1(@Param("fromDate") LocalDateTime fromDate);

    @Query("SELECT h FROM HoaDonSAdm h WHERE h.ngayNhan BETWEEN :startOfDay AND :endOfDay AND h.trangThai = 1")
    List<HoaDonSAdm> findHoaDonNgayBDVaNgayKTAdnTrangThai1(@Param("startOfDay") LocalDateTime startOfDay,
                                                           @Param("endOfDay") LocalDateTime endOfDay);
    @Query("SELECT h FROM HoaDonSAdm h WHERE h.ngayNhan BETWEEN :startOfDay AND :endOfDay")
    List<HoaDonSAdm> findHoaDonNgayBDVaNgayKT(@Param("startOfDay") LocalDateTime startOfDay,
                                              @Param("endOfDay") LocalDateTime endOfDay);

    @Modifying
    @Query("UPDATE HoaDonSAdm h SET h.trangThai = 4 WHERE h.id = :id AND h.trangThai = 6")
    int updateHoaDonTrangThai(@Param("id") Long id);

}
