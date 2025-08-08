package com.example.style_store_be_adminSell.repository;


import com.example.style_store_be_adminSell.entity.NguoiDungSAdm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NguoiDungSAdmRepo extends JpaRepository<NguoiDungSAdm, Long> {
    Optional<NguoiDungSAdm> findBySoDienThoaiAndIdChucVu(String soDienThoai,Long chucVuId);
    NguoiDungSAdm findBySoDienThoai(String soDienThoai);
    Optional<NguoiDungSAdm> findByEmail(String email);
    NguoiDungSAdm searchNguoiDungSAdmBySoDienThoai(String soDienThoai);
    boolean existsByEmail(String email);

}
