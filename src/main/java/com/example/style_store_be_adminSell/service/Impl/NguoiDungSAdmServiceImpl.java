package com.example.style_store_be_adminSell.service.Impl;


import com.example.style_store_be_adminSell.entity.NguoiDungSAdm;
import com.example.style_store_be_adminSell.repository.NguoiDungSAdmRepo;
import com.example.style_store_be_adminSell.service.NguoiDungSAdmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NguoiDungSAdmServiceImpl implements NguoiDungSAdmService {
    @Autowired
    private NguoiDungSAdmRepo nguoiDungSAdmRepo;

    @Override
    public NguoiDungSAdm searchUserById(Long id) {
        return nguoiDungSAdmRepo.findById(id).orElseThrow(()-> new IllegalArgumentException("Không tìm thấy người dùng"));
    }

    @Override
    public NguoiDungSAdm searchNguoiDungBySDT(String sdt,Long idChucVu) {
        return nguoiDungSAdmRepo.findBySoDienThoaiAndChucVuId(sdt,idChucVu).orElseThrow(()-> new IllegalArgumentException("Không tìm thấy khách hàng"));
    }
}
