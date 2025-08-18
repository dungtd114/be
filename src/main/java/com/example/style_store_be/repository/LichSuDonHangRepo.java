package com.example.style_store_be.repository;

import com.example.style_store_be.dto.LichSuHoaDonDto;
import com.example.style_store_be.entity.LichSuHoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LichSuDonHangRepo extends JpaRepository<LichSuHoaDon,Long> {
    @Query("SELECT new com.example.style_store_be.dto.LichSuHoaDonDto(" +
            "l.id, l.hoaDon.id, l.tieuDe, l.nguoiThucHien, l.noiDung, l.ngayCapNhat) " +
            "FROM LichSuHoaDon l " +
            "WHERE l.hoaDon.id = :hoaDonId " +
            "ORDER BY l.ngayCapNhat DESC")
    List<LichSuHoaDonDto> findAllByHoaDonIdOrderByNgayCapNhatDesc(@Param("hoaDonId") Long hoaDonId);



}
