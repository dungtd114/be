package com.example.style_store_be_adminSP.service.impl;

import com.example.style_store_be_adminSP.entity.HinhAnhMauSacAdm;
import com.example.style_store_be_adminSP.reposytory.HinhAnhRepoAdm;
import com.example.style_store_be_adminSP.service.HinhAnhMauSacServiceAdm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HinhAnhMauSacServiceImplAdm implements HinhAnhMauSacServiceAdm {

    @Autowired
    private HinhAnhRepoAdm repository;

    // Phương thức tiện ích để định dạng hinhAnh
    private String formatHinhAnh(String hinhAnh) {
        if (hinhAnh != null && !hinhAnh.isEmpty() && !hinhAnh.startsWith("/uploads/")) {
            return "/uploads/" + hinhAnh;
        }
        return hinhAnh;
    }

    @Override
    public Page<HinhAnhMauSacAdm> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HinhAnhMauSacAdm> pageResult = repository.findAll(pageable);
        pageResult.getContent().forEach(h -> h.setHinhAnh(formatHinhAnh(h.getHinhAnh())));
        return pageResult;
    }

    @Override
    public Page<HinhAnhMauSacAdm> searchByHinhAnh(String hinhAnh, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HinhAnhMauSacAdm> pageResult = repository.findByHinhAnhContainingIgnoreCase(hinhAnh, pageable);
        pageResult.getContent().forEach(h -> h.setHinhAnh(formatHinhAnh(h.getHinhAnh())));
        return pageResult;
    }

    @Override
    public Page<HinhAnhMauSacAdm> getActive(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HinhAnhMauSacAdm> pageResult = repository.findByNgayXoaIsNull(pageable);
        pageResult.getContent().forEach(h -> h.setHinhAnh(formatHinhAnh(h.getHinhAnh())));
        return pageResult;
    }

    @Override
    public Page<HinhAnhMauSacAdm> getDeleted(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HinhAnhMauSacAdm> pageResult = repository.findByNgayXoaIsNotNull(pageable);
        pageResult.getContent().forEach(h -> h.setHinhAnh(formatHinhAnh(h.getHinhAnh())));
        return pageResult;
    }

    @Override
    public HinhAnhMauSacAdm getOne(Long id) {
        HinhAnhMauSacAdm hinhAnh = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hình ảnh màu sắc không tồn tại với id: " + id));
        hinhAnh.setHinhAnh(formatHinhAnh(hinhAnh.getHinhAnh()));
        return hinhAnh;
    }

    @Override
    public void add(HinhAnhMauSacAdm hinhAnhMauSac) {
        if (hinhAnhMauSac.getHinhAnh() == null || hinhAnhMauSac.getHinhAnh().isEmpty()) {
            throw new RuntimeException("Đường dẫn hình ảnh không được rỗng");
        }
        if (!hinhAnhMauSac.getHinhAnh().startsWith("/uploads/")) {
            hinhAnhMauSac.setHinhAnh("/uploads/" + hinhAnhMauSac.getHinhAnh());
        }
        hinhAnhMauSac.setNgayTao(LocalDateTime.now());
        hinhAnhMauSac.setTrangThai(1);
        repository.save(hinhAnhMauSac);
    }

    @Override
    public void update(HinhAnhMauSacAdm hinhAnhMauSac) {
        HinhAnhMauSacAdm existing = getOne(hinhAnhMauSac.getId());
        if (hinhAnhMauSac.getHinhAnh() != null && !hinhAnhMauSac.getHinhAnh().isEmpty()) {
            existing.setHinhAnh(formatHinhAnh(hinhAnhMauSac.getHinhAnh()));
        }
        existing.setMoTa(hinhAnhMauSac.getMoTa());
        existing.setMauSac(hinhAnhMauSac.getMauSac());
        existing.setTrangThai(hinhAnhMauSac.getTrangThai());
        existing.setNgaySua(LocalDateTime.now());
        repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        HinhAnhMauSacAdm existing = getOne(id);
        existing.setNgayXoa(LocalDateTime.now());
        existing.setTrangThai(0); // Giả sử 0 là không hoạt động
        repository.save(existing);
    }

    @Override
    public List<HinhAnhMauSacAdm> getByMauSacId(Long mauSacId) {
        List<HinhAnhMauSacAdm> hinhAnhList = repository.findByMauSacIdAndActive(mauSacId);
        hinhAnhList.forEach(h -> h.setHinhAnh(formatHinhAnh(h.getHinhAnh())));
        return hinhAnhList;
    }
}