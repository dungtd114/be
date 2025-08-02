package com.example.style_store_be_adminThongKe.service;


import com.example.style_store_be_adminThongKe.DTO.SanPhamBanChayDTO;
import com.example.style_store_be_adminThongKe.reposytory.HoaDonTkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThongKeService {
    @Autowired
    private HoaDonTkRepo thongKeRepository;

    public List<SanPhamBanChayDTO> laySanPhamBanChayTheoThang(int thang, int nam) {
        return thongKeRepository.thongKeTheoThang(thang, nam);
    }

    public List<SanPhamBanChayDTO> laySanPhamBanChayTheoNam(int nam) {
        return thongKeRepository.thongKeTheoNam(nam);
    }

    public List<SanPhamBanChayDTO> laySanPhamBanChayTheoTuan(int tuan, int nam) {
        return thongKeRepository.thongKeTheoTuan(tuan, nam);
    }
}