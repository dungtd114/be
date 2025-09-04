package com.example.style_store_be.service;

import com.example.style_store_be.dto.request.HoaDonChiTietRequest;
import com.example.style_store_be.entity.HoaDon;
import com.example.style_store_be.entity.HoaDonCt;
import com.example.style_store_be.entity.LichSuHoaDon;
import com.example.style_store_be.entity.User;
import com.example.style_store_be.mapper.HoaDonChiTietMapper;
import com.example.style_store_be.repository.LichSuDonHangRepo;
import com.example.style_store_be.repository.website.DonHangChiTietRepo;
import com.example.style_store_be.repository.website.DonHangRepoSitory;
import com.example.style_store_be.repository.website.UserRepoSitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class HoaDonChiTietService {
    DonHangChiTietRepo donHangChiTietRepo;
    DonHangRepoSitory donHangRepoSitory;
    HoaDonChiTietMapper hoaDonChiTietMapper;
    LichSuDonHangRepo lichSuDonHangRepo;
    UserRepoSitory userRepoSitory;
    public HoaDonCt addSanPhamHoaDon(HoaDonChiTietRequest request) {
        HoaDonCt hoaDonCt = hoaDonChiTietMapper.toHoaDonCt(request);

        Long hoaDonId = request.getHoaDonId();
        HoaDon hoaDon = donHangRepoSitory.findById(hoaDonId)
                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));

        if (hoaDon.getTongTien() == null) {
            hoaDon.setTongTien(0.0);
        }
        hoaDon.setTongTien(hoaDon.getTongTien() + request.getThanhTien());
        if (hoaDon.getTongSoLuongSp() == null) {
            hoaDon.setTongSoLuongSp(0);
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepoSitory.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
        hoaDon.setTongSoLuongSp(hoaDon.getTongSoLuongSp() + request.getSoLuong());
        LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
        lichSuHoaDon.setHoaDon(hoaDon);
        lichSuHoaDon.setNgayCapNhat(new Date());
        lichSuHoaDon.setTieuDe("Thêm sản phẩm vào hoá đơn");
        lichSuHoaDon.setNoiDung("Thêm sản phẩm " + request.getTenSanPham() + " vào hoá đơn");
        lichSuHoaDon.setNguoiThucHien(user.getHoTen());
        lichSuDonHangRepo.save(lichSuHoaDon);

        donHangRepoSitory.save(hoaDon);

        return donHangChiTietRepo.save(hoaDonCt);
    }


    public void deleteSanPhamHoaDon(Long id) {
        HoaDonCt hoaDonCt =donHangChiTietRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Sản phẩm hóa đơn không tồn tại"));
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepoSitory.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
        HoaDon hoaDon = hoaDonCt.getHoaDon();
        LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
        lichSuHoaDon.setHoaDon(hoaDon);
        lichSuHoaDon.setNgayCapNhat(new Date());
        lichSuHoaDon.setTieuDe("Xoá sản phẩm khỏi hoá đơn");
        lichSuHoaDon.setNoiDung("Xoá sản phẩm " + hoaDonCt.getTenSanPham() + " khỏi hoá đơn");
        lichSuHoaDon.setNguoiThucHien(user.getHoTen());
        lichSuDonHangRepo.save(lichSuHoaDon);

        donHangChiTietRepo.delete(hoaDonCt);
    }
}
