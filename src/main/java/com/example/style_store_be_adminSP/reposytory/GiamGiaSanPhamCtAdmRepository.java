package com.example.style_store_be_adminSP.reposytory;

import com.example.style_store_be_adminSP.entity.GiamGiaSanPhamCtAdm;
import com.example.style_store_be_adminSP.entity.GiamGiaSanPhamCtId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface GiamGiaSanPhamCtAdmRepository extends JpaRepository<GiamGiaSanPhamCtAdm, GiamGiaSanPhamCtId> {
    @Query("SELECT COUNT(gct) > 0 " +
            "FROM GiamGiaSanPhamCtAdm gct " +
            "JOIN gct.giamGia gg " +
            "WHERE gct.idSanPhamCt = :spctId " +
            "AND gg.trangThai = 1 " +
            "AND gg.ngayBatDau <= :now " +
            "AND gg.ngayKetThuc >= :now")
    boolean existsActiveBySanPhamCtId(@Param("spctId") Long spctId,
                                      @Param("now") LocalDateTime now);
}
