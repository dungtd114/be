package com.example.style_store_be_adminThongKe.reposytory;

import com.example.style_store_be_adminThongKe.entity.SanPhamTK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SanPhamThongKeRepo extends JpaRepository<SanPhamTK, Long> {

    SanPhamTK findByMa(String ma);

    @Query(value = """
    SELECT sp.ma, sp.ten, ms.ten AS mauSac, th.ten AS thuongHieu, kt.ten AS kichThuoc,
           SUM(ct.so_luong) AS tongSoLuong, SUM(ct.thanh_tien) AS tongDoanhThu
    FROM hoa_don_ct ct
    JOIN san_pham_ct spct ON ct.san_pham_ct_id = spct.id
    JOIN san_pham sp ON spct.san_pham_id = sp.id
    JOIN mau_sac ms ON spct.mau_sac_id = ms.id
    JOIN thuong_hieu th ON spct.thuong_hieu_id = th.id
    JOIN kich_thuoc kt ON spct.kich_thuoc_id = kt.id
    JOIN hoa_don hd ON ct.hoa_don_id = hd.id
    WHERE hd.trang_thai = 1
    GROUP BY sp.ma, sp.ten, ms.ten, th.ten, kt.ten
    ORDER BY tongSoLuong DESC, tongDoanhThu DESC
""", nativeQuery = true)
    List<Object[]> thongKeSanPhamBanChay();

}
