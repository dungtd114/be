package com.example.style_store_be_adminThongKe.service;

import com.example.style_store_be_adminThongKe.DTO.DoanhThuDTO;
import com.example.style_store_be_adminThongKe.DTO.SanPhamBanChayDTO;
import com.example.style_store_be_adminThongKe.reposytory.HoaDonTkRepo;
import com.example.style_store_be_adminThongKe.reposytory.SanPhamThongKeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThongKeService {

    @Autowired
    private HoaDonTkRepo hoaDonRepository;

    public List<DoanhThuDTO> thongKeDoanhThuTheoNgay() {
        List<Object[]> result = hoaDonRepository.thongKeDoanhThuTheoNgay();
        System.out.println("Doanh thu theo ngày (tất cả): " + result);
        return result.stream()
                .map(row -> new DoanhThuDTO((String) row[0], ((Number) row[1]).doubleValue()))
                .collect(Collectors.toList());
    }

    public List<DoanhThuDTO> thongKeDoanhThuTheoNgay(String date) {
        List<Object[]> result = hoaDonRepository.thongKeDoanhThuTheoNgay(date);
        System.out.println("Doanh thu theo ngày (date=" + date + "): " + result);
        return result.stream()
                .map(row -> new DoanhThuDTO((String) row[0], ((Number) row[1]).doubleValue()))
                .collect(Collectors.toList());
    }

    public List<DoanhThuDTO> thongKeDoanhThuTheoTuan() {
        List<Object[]> result = hoaDonRepository.thongKeDoanhThuTheoTuan();
        System.out.println("Doanh thu theo tuần: " + result);
        return result.stream()
                .map(row -> new DoanhThuDTO(row[0] + "-W" + row[1], ((Number) row[2]).doubleValue()))
                .collect(Collectors.toList());
    }

    public List<DoanhThuDTO> thongKeDoanhThuTheoThang() {
        List<Object[]> result = hoaDonRepository.thongKeDoanhThuTheoThang();
        System.out.println("Doanh thu theo tháng: " + result);
        return result.stream()
                .map(row -> new DoanhThuDTO(row[0] + "-" + String.format("%02d", row[1]), ((Number) row[2]).doubleValue()))
                .collect(Collectors.toList());
    }

    public List<DoanhThuDTO> thongKeDoanhThuTheoNam() {
        List<Object[]> result = hoaDonRepository.thongKeDoanhThuTheoNam();
        System.out.println("Doanh thu theo năm: " + result);
        return result.stream()
                .map(row -> new DoanhThuDTO(row[0].toString(), ((Number) row[1]).doubleValue()))
                .collect(Collectors.toList());
    }

    @Autowired
    private SanPhamThongKeRepo sanPhamRepository;

    public List<SanPhamBanChayDTO> thongKeSanPhamBanChay() {
        List<Object[]> result = sanPhamRepository.thongKeSanPhamBanChay();
        System.out.println("Sản phẩm bán chạy: " + result);
        return result.stream()
                .map(row -> {
                    SanPhamBanChayDTO dto = new SanPhamBanChayDTO();
                    dto.setMaSanPham((String) row[0]);
                    dto.setTenSanPham((String) row[1]);
                    dto.setMauSac((String) row[2]);
                    dto.setThuongHieu((String) row[3]);
                    dto.setKichThuoc((String) row[4]);
                    dto.setTongSoLuongBan(((Number) row[5]).intValue());
                    dto.setTongDoanhThu(((Number) row[6]).doubleValue());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}