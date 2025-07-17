package com.example.style_store_be.service;

import com.example.style_store_be.dto.SanPhamWebDto;
import com.example.style_store_be.entity.*;
import com.example.style_store_be.repository.SanPhamWebRepo;
import com.example.style_store_be.repository.website.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SanPhamWebService {
    private final SanPhamWebRepo sanPhamWebRepo;
    private final ThuongHieuWebRepo thuongHieuWebRepo;
    private final KichKoWebRepo kichKoWebRepo;
    private final MauSacWebRepo mauSacWebRepo;
    private final ChatLieuWebRepo chatLieuWebRepo;
    private final TTSanPhamWebRepo ttSanPhamWebRepo;
    public SanPhamWebService(SanPhamWebRepo sanPhamWebRepo, ThuongHieuWebRepo thuongHieuWebRepo, KichKoWebRepo kichKoWebRepo, MauSacWebRepo mauSacWebRepo, ChatLieuWebRepo chatLieuWebRepo, TTSanPhamWebRepo ttSanPhamWebRepo) {
        this.sanPhamWebRepo = sanPhamWebRepo;
        this.thuongHieuWebRepo = thuongHieuWebRepo;
        this.kichKoWebRepo = kichKoWebRepo;
        this.mauSacWebRepo = mauSacWebRepo;
        this.chatLieuWebRepo = chatLieuWebRepo;
        this.ttSanPhamWebRepo = ttSanPhamWebRepo;
    }

 //   @PreAuthorize("hasAuthority('VIEW_PRODUCT')")
    public Page<SanPhamWebDto> getPageChiTietSanPham(
            String tenSanPham, Long thuongHieuId, Long mauSacId, Long chatLieuId, Long kichThuocId,
            Double minPrice, Double maxPrice,Long sanPhamId, Pageable pageable) {
        return sanPhamWebRepo.findByFilters(tenSanPham, thuongHieuId, mauSacId, chatLieuId, kichThuocId, minPrice, maxPrice,sanPhamId, pageable);
    }

    public List<ThuongHieu> getListThuongHieu() {
        return thuongHieuWebRepo.findAll(Sort.by("ngayTao").descending());
    }

    public List<MauSacSp> getListMauSac() {
        return mauSacWebRepo.findAll(Sort.by("ngayTao").descending());
    }
    public List<KichThuoc> getListKichThuoc() {
        return kichKoWebRepo.findAll(Sort.by("ngayTao").descending());
    }
    public List<ChatLieu> getListChatLieu() {
        return chatLieuWebRepo.findAll(Sort.by("ngayTao").descending());
    }

    public ChiTietSanPham detailSanPhamCt(Long id) {
        return sanPhamWebRepo.findById(id).orElseThrow(() -> new RuntimeException("Chi tiết sản phẩm không tồn tại với id: " + id));
    }

    public List<SanPham> getListSanPham() {
        return ttSanPhamWebRepo.findAll(Sort.by("ngayTao").descending());
    }


    public Page<SanPham> getPageSanPham(Pageable pageable) {
        return ttSanPhamWebRepo.findAll(pageable);
    }

    public List<SanPhamWebDto> getListChiTietSanPham(
            String tenSanPham,
            Long thuongHieuId,
            Long mauSacId,
            Long chatLieuId,
            Long kichThuocId,
            Double minPrice,
            Double maxPrice,
            Sort sort,
            Long sanPhamId
    ) {
        return sanPhamWebRepo.findByFiltersNoPaging(tenSanPham, thuongHieuId, mauSacId,
                chatLieuId, kichThuocId, minPrice, maxPrice,
                sanPhamId, sort);
    }
}
