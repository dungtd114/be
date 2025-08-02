package com.example.style_store_be_adminSell.service.Impl;

import com.example.style_store_be.entity.DiaChiNhan;
import com.example.style_store_be_adminSell.entity.DiaChiNhanSAdm;
import com.example.style_store_be_adminSell.repository.DiaChiNhanSAdmRepo;
import com.example.style_store_be_adminSell.service.DiaChiNhanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiaChiNhanSAdmServiceImpl implements DiaChiNhanService {

    @Autowired
    private DiaChiNhanSAdmRepo diaChiNhanSAdmRepo;


    @Override
    public List<DiaChiNhanSAdm> getAllDiaChiNhanByIdKhachHang(Long idKhachHang) {
        return diaChiNhanSAdmRepo.findAllByNguoiDungSAdm_Id(idKhachHang);
    }

    @Override
    public DiaChiNhanSAdm addDiaChiNhan(DiaChiNhanSAdm diaChiNhanSAdm) {
        return diaChiNhanSAdmRepo.save(diaChiNhanSAdm);
    }
}
