package com.example.style_store_be.repository.website;

import com.example.style_store_be.dto.GiamGiaDto;
import com.example.style_store_be.entity.GiamGia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface DotGiamGiaRepository extends JpaRepository<GiamGia, Long> {


    @Query("SELECT new com.example.style_store_be.dto.GiamGiaDto(" +
            "id, ma, tenDotGiam, giamGia, ngayBatDau, ngayKetThuc, trangThai)" +
            " FROM GiamGia" +
            " WHERE (:tenGiamGia is null or tenDotGiam like concat('%', :tenGiamGia, '%'))" +
            " AND (:giamGia is null or giamGia = :giamGia)" +
            " AND (:idTrangThai is null or trangThai = :idTrangThai)" +
            " AND (:ngayBatDau is null or ngayBatDau >= :ngayBatDau)" +
            " AND (:ngayKetThuc is null or ngayKetThuc <= :ngayKetThuc)" +
            " ORDER BY ngayTao desc")
    Page<GiamGiaDto> getPageGiamGia(
            @Param("tenGiamGia") String tenGiamGia,
            @Param("idTrangThai") Integer idTrangThai,
            @Param("giamGia") String giamGia,
            @Param("ngayBatDau") Date ngayBatDau,
            @Param("ngayKetThuc") Date ngayKetThuc,
            Pageable pageable);


    @Query("SELECT g FROM GiamGia g LEFT JOIN FETCH g.chiTietSanPhams ct LEFT JOIN FETCH ct.sanPham WHERE g.id = :id")
    Optional<GiamGia> findByIdWithChiTietSanPhams(Long id);
}
