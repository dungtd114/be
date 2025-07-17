package com.example.style_store_be.service.website;


import com.example.style_store_be.dto.GiamGiaDto;
import com.example.style_store_be.dto.request.ApDungGGRequest;
import com.example.style_store_be.dto.request.ApDungGGUpdateRequest;
import com.example.style_store_be.dto.request.GiamGiaRequest;
import com.example.style_store_be.entity.ChiTietSanPham;
import com.example.style_store_be.entity.GiamGia;
import com.example.style_store_be.entity.User;
import com.example.style_store_be.mapper.ApDungGGMapper;
import com.example.style_store_be.mapper.GiamGiaMapper;
import com.example.style_store_be.repository.SanPhamWebRepo;
import com.example.style_store_be.repository.website.DotGiamGiaRepository;
import com.example.style_store_be.repository.website.UserRepoSitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GiamGiaService {
    DotGiamGiaRepository dotGiamGiaRepository;
    GiamGiaMapper giamGiaMapper;
    ApDungGGMapper apDungGGMapper;
    UserRepoSitory userRepoSitory;
    SanPhamWebRepo sanPhamWebRepo;
    public GiamGia createVoucher(GiamGiaRequest request) {
        GiamGia giamGia = giamGiaMapper.toGiamGia(request);
        if (giamGia.getMa() == null || giamGia.getMa().isEmpty()) {
            giamGia.setMa("GG" + UUID.randomUUID().toString().substring(0, 8));

        }
        giamGia.setNgayTao(new Date());
        giamGia.setTrangThai(3);
        giamGia.setSoLuong(1);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepoSitory.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
        giamGia.setUser(user);
        giamGia.setNguoiTao(user.getHoTen());
        return dotGiamGiaRepository.save(giamGia);
    }

    public GiamGia applyVocher(ApDungGGRequest request) {
        GiamGia giamGia = apDungGGMapper.toApDungGiamGia(request);
        if (giamGia.getMa() == null || giamGia.getMa().isEmpty()) {
            giamGia.setMa("GG" + UUID.randomUUID().toString().substring(0, 8));

        }
        giamGia.setNgayTao(new Date());
        giamGia.setSoLuong(1);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepoSitory.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
        giamGia.setUser(user);
        giamGia.setNguoiTao(user.getHoTen());
        Date currentDate = new Date();
        if (currentDate.after(giamGia.getNgayBatDau()) && currentDate.before(giamGia.getNgayKetThuc())) {
            giamGia.setTrangThai(1);
        } else {
            giamGia.setTrangThai(2);
        }
        GiamGia savedGiamGia = dotGiamGiaRepository.save(giamGia);

        List<ChiTietSanPham> chiTietSanPhams = sanPhamWebRepo.findAllById(request.getSanPhamCtIds());
        if (chiTietSanPhams.isEmpty()) {
            throw new RuntimeException("Không tìm thấy sản phẩm chi tiết nào");
        }

        for (ChiTietSanPham chiTietSanPham : chiTietSanPhams) {
            chiTietSanPham.getDotGiamGias().add(savedGiamGia);
            GiamGia bestGiamGia = savedGiamGia;
            for (GiamGia existingGiamGia : chiTietSanPham.getDotGiamGias()) {
                if (existingGiamGia.getTrangThai() == 1 && existingGiamGia.getGiamGia() > bestGiamGia.getGiamGia()) {
                    bestGiamGia = existingGiamGia;
                }
            }

            if (bestGiamGia.getId().equals(savedGiamGia.getId()) && savedGiamGia.getTrangThai() == 1) {
                if (chiTietSanPham.getGiaBanGoc() != null && chiTietSanPham.getGiaBanGoc() >= savedGiamGia.getDieuKienGiam()) {
                    double discountAmount = chiTietSanPham.getGiaBanGoc() * savedGiamGia.getGiamGia();
                    if (discountAmount > savedGiamGia.getGiamToiDa()) {
                        discountAmount = savedGiamGia.getGiamToiDa();
                    }
                    double newPrice = chiTietSanPham.getGiaBanGoc() - discountAmount;

                    chiTietSanPham.setGiaBan(newPrice);
                } else {
                    throw new RuntimeException("Sản phẩm với ID " + chiTietSanPham.getId() + " không đủ điều kiện áp dụng mã giảm giá");
                }
            }

            sanPhamWebRepo.save(chiTietSanPham);
        }

        return savedGiamGia;
    }

    public Page<GiamGiaDto> getPageGiamGia(String tenGiamGia, Integer idTrangThai, String giamGia, Date ngayBatDau ,Date ngayKetThuc,Pageable pageable) {
        return dotGiamGiaRepository.getPageGiamGia(tenGiamGia, idTrangThai, giamGia,ngayBatDau,ngayKetThuc, pageable);
    }

    public GiamGia updateVoucher(ApDungGGUpdateRequest request) {
        GiamGia giamGia = dotGiamGiaRepository.findById(request.getIdGiamGia()).orElseThrow(()-> new RuntimeException("Mã giảm giá không tồn tại"));
        List<ChiTietSanPham> chiTietSanPhams = sanPhamWebRepo.findAllById(request.getSanPhamCtIds());
        if (chiTietSanPhams.isEmpty()) {
            throw new RuntimeException("Không tìm thấy sản phẩm chi tiết nào");
        }
        for (ChiTietSanPham chiTietSanPham :chiTietSanPhams){
            chiTietSanPham.getDotGiamGias().add(giamGia);
            GiamGia bestGiamGia = giamGia;
            for (GiamGia existingGiamGia : chiTietSanPham.getDotGiamGias()){
                if (existingGiamGia.getTrangThai() == 1 && existingGiamGia.getGiamGia()> bestGiamGia.getGiamGia()){
                    bestGiamGia = existingGiamGia;
                }
            }
            if (bestGiamGia.getId().equals(giamGia.getId()) && giamGia.getTrangThai() == 1) {
                if (chiTietSanPham.getGiaBanGoc() != null && chiTietSanPham.getGiaBanGoc() >= giamGia.getDieuKienGiam()) {
                    double discountAmount = chiTietSanPham.getGiaBanGoc() * giamGia.getGiamGia();
                    if (discountAmount > giamGia.getGiamToiDa()) {
                        discountAmount = giamGia.getGiamToiDa();
                    }
                    double newPrice = chiTietSanPham.getGiaBanGoc() - discountAmount;

                    chiTietSanPham.setGiaBan(newPrice);
                } else {
                    throw new RuntimeException("Sản phẩm với ID " + chiTietSanPham.getId() + " không đủ điều kiện áp dụng mã giảm giá");
                }
            }

            sanPhamWebRepo.save(chiTietSanPham);
            giamGia.setTrangThai(1);
        }
        return giamGia;
    }
}
