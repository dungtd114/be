package com.example.style_store_be_adminSell.service;

import com.example.style_store_be.entity.DiaChiNhan;
import com.example.style_store_be_adminSell.dto.NguoiDungDto;
import com.example.style_store_be_adminSell.entity.DiaChiNhanSAdm;

import java.util.List;

public interface DiaChiNhanService {
    List<DiaChiNhanSAdm> getAllDiaChiNhanByIdKhachHang(Long idKhachHang);
    DiaChiNhanSAdm addDiaChiNhan(DiaChiNhanSAdm diaChiNhanSAdm);
}
