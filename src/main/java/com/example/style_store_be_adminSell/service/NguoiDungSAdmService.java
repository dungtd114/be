package com.example.style_store_be_adminSell.service;


import com.example.style_store_be_adminSell.entity.NguoiDungSAdm;

public interface NguoiDungSAdmService {
    NguoiDungSAdm searchUserById(Long id);
    NguoiDungSAdm searchNguoiDungBySDT(String sdt, Long idChucVu);
}
