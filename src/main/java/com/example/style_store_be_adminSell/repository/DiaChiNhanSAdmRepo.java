package com.example.style_store_be_adminSell.repository;

import com.example.style_store_be_adminSell.entity.DiaChiNhanSAdm;
import com.example.style_store_be_adminSell.entity.NguoiDungSAdm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaChiNhanSAdmRepo extends JpaRepository<DiaChiNhanSAdm, Long> {

    DiaChiNhanSAdm findByNguoiDungSAdm(NguoiDungSAdm nguoiDungSAdm);
}
