package com.example.style_store_be_adminSell.service.Impl;


import com.example.style_store_be_adminSell.dto.HoaDonSAdmDto;
import com.example.style_store_be_adminSell.dto.NguoiDungDto;
import com.example.style_store_be_adminSell.entity.DiaChiNhanSAdm;
import com.example.style_store_be_adminSell.entity.HoaDonSAdm;
import com.example.style_store_be_adminSell.entity.NguoiDungSAdm;
import com.example.style_store_be_adminSell.repository.DiaChiNhanSAdmRepo;
import com.example.style_store_be_adminSell.repository.NguoiDungSAdmRepo;
import com.example.style_store_be_adminSell.service.NguoiDungSAdmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NguoiDungSAdmServiceImpl implements NguoiDungSAdmService {
    @Autowired
    private NguoiDungSAdmRepo nguoiDungSAdmRepo;

    @Autowired
    private DiaChiNhanSAdmRepo diaChiNhanSAdmRepo;

    private NguoiDungSAdm mapToNguoiDungEntity(NguoiDungDto dto) {
        NguoiDungSAdm entity = new NguoiDungSAdm();
        entity.setId(dto.getIdNguoiDung());
        entity.setMa(dto.getMaNguoiDung());
        entity.setHoTen(dto.getHoTen());
        entity.setSoDienThoai(dto.getSoDienThoai());
        entity.setEmail(dto.getEmail());
        entity.setCccd(dto.getCccd());
        entity.setDiaChi(dto.getDiaChiNguoiDung());
        entity.setGioiTinh(dto.getGioiTinh());
        entity.setNamSinh(dto.getNamSinh());
        entity.setTenDangNhap(dto.getTenDangNhap());
        entity.setMatKhau(dto.getMatKhau()); // → Nên mã hóa BCrypt trong Service
        entity.setTinh(dto.getTinhNguoiDung());
        entity.setHuyen(dto.getHuyenNguoiDung());
        entity.setXa(dto.getXaNguoiDung());
        entity.setIdChucVu(3L);
        entity.setTrangThai(1);
        entity.setNgayTao(new Date());
        return entity;
    }

    private DiaChiNhanSAdm mapToDiaChiEntity(NguoiDungDto dto, NguoiDungSAdm nguoiDung) {
        DiaChiNhanSAdm entity = new DiaChiNhanSAdm();
        entity.setId(dto.getIdDiaChi());
        entity.setMa(dto.getMaDiaChi());
        entity.setTenNguoiNhan(dto.getTenNguoiNhan());
        entity.setSoDienThoai(dto.getSoDienThoaiNhan());
        entity.setDiaChi(dto.getDiaChiNhan());
        entity.setTinh(dto.getTinhNhan());
        entity.setHuyen(dto.getHuyenNhan());
        entity.setXa(dto.getXaNhan());
        entity.setNguoiDungSAdm(nguoiDung);
        entity.setNgayTao(new Date());
        entity.setTrangThai(1);
        return entity;
    }

    private NguoiDungDto mapToDto(DiaChiNhanSAdm diaChi, NguoiDungSAdm nguoiDung) {
        NguoiDungDto dto = new NguoiDungDto();
        dto.setIdNguoiDung(nguoiDung.getId());
        dto.setMaNguoiDung(nguoiDung.getMa());
        dto.setHoTen(nguoiDung.getHoTen());
        dto.setSoDienThoai(nguoiDung.getSoDienThoai());
        dto.setEmail(nguoiDung.getEmail());
        dto.setCccd(nguoiDung.getCccd());
        dto.setDiaChiNguoiDung(nguoiDung.getDiaChi());
        dto.setGioiTinh(nguoiDung.getGioiTinh());
        dto.setNamSinh(nguoiDung.getNamSinh());
        dto.setTenDangNhap(nguoiDung.getTenDangNhap());
        dto.setMatKhau(nguoiDung.getMatKhau());
        dto.setTinhNguoiDung(nguoiDung.getTinh());
        dto.setHuyenNguoiDung(nguoiDung.getHuyen());
        dto.setXaNguoiDung(nguoiDung.getXa());
        dto.setIdChucVu(nguoiDung.getId());

        dto.setIdDiaChi(diaChi.getId());
        dto.setMaDiaChi(diaChi.getMa());
        dto.setTenNguoiNhan(diaChi.getTenNguoiNhan());
        dto.setSoDienThoai(diaChi.getSoDienThoai());
        dto.setDiaChiNhan(diaChi.getDiaChi());
        dto.setTinhNhan(diaChi.getTinh());
        dto.setHuyenNhan(diaChi.getHuyen());
        dto.setXaNhan(diaChi.getXa());

        return dto;
    }



    @Override
    public NguoiDungSAdm searchUserById(Long id) {
        return nguoiDungSAdmRepo.findById(id).orElseThrow(()-> new IllegalArgumentException("Không tìm thấy người dùng"));
    }

    @Override
    public NguoiDungSAdm searchNguoiDungBySDT(String sdt,Long idChucVu) {
        return nguoiDungSAdmRepo.findBySoDienThoaiAndIdChucVu(sdt,idChucVu).orElseThrow(()-> new IllegalArgumentException("Không tìm thấy khách hàng"));
    }

    @Override
    public DiaChiNhanSAdm addNguoiDung(NguoiDungDto dto) {
        NguoiDungSAdm nguoiDung = mapToNguoiDungEntity(dto);
        nguoiDungSAdmRepo.save(nguoiDung);

        DiaChiNhanSAdm diaChi = mapToDiaChiEntity(dto,nguoiDung);
        return diaChiNhanSAdmRepo.save(diaChi);
    }

    @Override
    public NguoiDungSAdm searchNguoiDungBySDT(String sdt) {
        return nguoiDungSAdmRepo.searchNguoiDungSAdmBySoDienThoai((sdt));
    }
}
