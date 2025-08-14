package com.example.style_store_be_adminThongKe.service;


import com.example.style_store_be_adminThongKe.DTO.SanPhamBanChayDTO;
import com.example.style_store_be_adminThongKe.reposytory.HoaDonTkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThongKeService {
    @Autowired
    private HoaDonTkRepo thongKeRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public List<SanPhamBanChayDTO> laySanPhamBanChayTheoThang(int thang, int nam) {
        return thongKeRepository.thongKeTheoThang(thang, nam);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<SanPhamBanChayDTO> laySanPhamBanChayTheoNam(int nam) {
        return thongKeRepository.thongKeTheoNam(nam);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<SanPhamBanChayDTO> laySanPhamBanChayTheoTuan(int tuan, int nam) {
        return thongKeRepository.thongKeTheoTuan(tuan, nam);
    }
}