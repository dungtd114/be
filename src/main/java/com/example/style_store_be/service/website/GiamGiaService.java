package com.example.style_store_be.service.website;


import com.example.style_store_be.dto.GiamGiaDto;
import com.example.style_store_be.dto.request.ApDungGGRequest;
import com.example.style_store_be.dto.request.ApDungGGUpdateRequest;
import com.example.style_store_be.dto.request.GiamGiaRequest;
import com.example.style_store_be.dto.response.GiamGiaResponse;
import com.example.style_store_be.entity.ChiTietSanPham;
import com.example.style_store_be.entity.GiamGia;
import com.example.style_store_be.entity.User;
import com.example.style_store_be.exception.AppException;
import com.example.style_store_be.exception.Errorcode;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @PreAuthorize("hasRole('ADMIN')")
    public GiamGia applyVocher(ApDungGGRequest request) {
        GiamGia giamGia = apDungGGMapper.toApDungGiamGia(request);
        if (giamGia.getMa() == null || giamGia.getMa().isEmpty()) {
            giamGia.setMa("GG" + UUID.randomUUID().toString().substring(0, 8));

        }
        if (request.getNgayBatDau().after(request.getNgayKetThuc())) {
            throw new AppException(Errorcode.INVALID_DATE_RANGE);
        }
        giamGia.setNgayTao(new Date());
        giamGia.setSoLuong(1); // Giả định mỗi mã giảm giá được áp dụng một lần
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepoSitory.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
        giamGia.setUser(user);
        giamGia.setNguoiTao(user.getHoTen());
        Date currentDate = new Date();
        if (currentDate.after(giamGia.getNgayBatDau()) && currentDate.before(giamGia.getNgayKetThuc())) {
            giamGia.setTrangThai(1); // Đang hoạt động
        } else {
            giamGia.setTrangThai(2); // Không hoạt động (có thể là chưa đến hạn hoặc đã hết hạn)
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

                    if (savedGiamGia.getGiamToiDa() > chiTietSanPham.getGiaBanGoc()) {
                        throw new AppException(Errorcode.INVALID_DISCOUNT_AMOUNT);
                    }

                    // ⭐ Đã thêm / 100.0 vào đây
                    double discountAmount = chiTietSanPham.getGiaBanGoc() * (savedGiamGia.getGiamGia() / 100.0);
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

    @PreAuthorize("hasRole('ADMIN')")
    public Page<GiamGiaDto> getPageGiamGia(String tenGiamGia, Integer idTrangThai, String giamGia, Date ngayBatDau ,Date ngayKetThuc,Pageable pageable) {
        return dotGiamGiaRepository.getPageGiamGia(tenGiamGia, idTrangThai, giamGia,ngayBatDau,ngayKetThuc, pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public GiamGiaResponse detailGiamGia(Long id) {
        GiamGia giamGia = dotGiamGiaRepository.findByIdWithChiTietSanPhams(id)
                .orElseThrow(() -> new RuntimeException("Mã giảm giá không tồn tại"));

        GiamGiaResponse response = apDungGGMapper.toGiamGiaResponse(giamGia);

        if (giamGia.getChiTietSanPhams() != null) {
            response.setIdChiTietSanPham(
                    giamGia.getChiTietSanPhams().stream()
                            .map(ChiTietSanPham::getId)
                            .collect(Collectors.toList())
            );

            response.setIdSanPham(
                    giamGia.getChiTietSanPhams().stream()
                            .map(ct -> ct.getSanPham().getId())
                            .distinct()
                            .collect(Collectors.toList())
            );
        } else {
            response.setIdChiTietSanPham(List.of());
            response.setIdSanPham(List.of());
        }

        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public GiamGiaResponse updateGiamGia(Long id, ApDungGGUpdateRequest apDungGGUpdateRequest) {
        GiamGia giamGia = dotGiamGiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mã giảm giá không tồn tại"));

        apDungGGMapper.apDungGGUpdateRequest(giamGia, apDungGGUpdateRequest);

        Date currentDate = new Date();
        if (currentDate.after(giamGia.getNgayBatDau()) && currentDate.before(giamGia.getNgayKetThuc())) {
            giamGia.setTrangThai(1);
        } else {
            giamGia.setTrangThai(2);
        }
        if (apDungGGUpdateRequest.getNgayBatDau().after(apDungGGUpdateRequest.getNgayKetThuc())) {
            throw new AppException(Errorcode.INVALID_DATE_RANGE);
        }
        giamGia.setNgaySua(new Date());

        List<ChiTietSanPham> newChiTietSanPhams = sanPhamWebRepo.findAllById(apDungGGUpdateRequest.getSanPhamCtIds());
        if (newChiTietSanPhams.isEmpty()) {
            throw new RuntimeException("Không tìm thấy sản phẩm chi tiết nào");
        }

        Set<Long> newSanPhamCtIds = newChiTietSanPhams.stream()
                .map(ChiTietSanPham::getId)
                .collect(Collectors.toSet());

        Set<ChiTietSanPham> currentChiTietSanPhams = giamGia.getChiTietSanPhams();
        if (currentChiTietSanPhams != null) {
            currentChiTietSanPhams.removeIf(chiTietSanPham -> {
                if (!newSanPhamCtIds.contains(chiTietSanPham.getId())) {
                    chiTietSanPham.getDotGiamGias().remove(giamGia);
                    if (chiTietSanPham.getGiaBanGoc() != null) {
                        chiTietSanPham.setGiaBan(chiTietSanPham.getGiaBanGoc());
                    }
                    sanPhamWebRepo.save(chiTietSanPham);
                    return true;
                }
                return false;
            });
        }

        for (ChiTietSanPham chiTietSanPham : newChiTietSanPhams) {
            if (chiTietSanPham.getGiaBanGoc() != null && apDungGGUpdateRequest.getGiamToiDa() > chiTietSanPham.getGiaBanGoc()) {
                throw new RuntimeException("Số tiền giảm tối đa (" + apDungGGUpdateRequest.getGiamToiDa() +
                        ") không được lớn hơn giá bán gốc của sản phẩm (" + chiTietSanPham.getGiaBanGoc() + ") cho sản phẩm ID: " + chiTietSanPham.getId());
            }

            if (!chiTietSanPham.getDotGiamGias().contains(giamGia)) {
                chiTietSanPham.getDotGiamGias().add(giamGia);
                giamGia.getChiTietSanPhams().add(chiTietSanPham);
            }
            updateProductPrice(chiTietSanPham);
            sanPhamWebRepo.save(chiTietSanPham);
        }

        GiamGia savedGiamGia = dotGiamGiaRepository.save(giamGia);
        return apDungGGMapper.toGiamGiaResponse(savedGiamGia);
    }

    private void updateProductPrice(ChiTietSanPham chiTietSanPham) {
        GiamGia bestGiamGia = null;
        for (GiamGia giamGia : chiTietSanPham.getDotGiamGias()) {
            if (giamGia.getTrangThai() == 1 &&
                    (bestGiamGia == null || giamGia.getGiamGia() > bestGiamGia.getGiamGia())) {
                bestGiamGia = giamGia;
            }
        }

        if (bestGiamGia != null && chiTietSanPham.getGiaBanGoc() != null &&
                chiTietSanPham.getGiaBanGoc() >= bestGiamGia.getDieuKienGiam()) {
            // ⭐ Đã thêm / 100.0 vào đây
            double discountAmount = chiTietSanPham.getGiaBanGoc() * (bestGiamGia.getGiamGia() / 100.0);
            if (discountAmount > bestGiamGia.getGiamToiDa()) {
                discountAmount = bestGiamGia.getGiamToiDa();
            }
            double newPrice = chiTietSanPham.getGiaBanGoc() - discountAmount;
            chiTietSanPham.setGiaBan(newPrice);
        } else {
            chiTietSanPham.setGiaBan(chiTietSanPham.getGiaBanGoc());
        }
    }
}
