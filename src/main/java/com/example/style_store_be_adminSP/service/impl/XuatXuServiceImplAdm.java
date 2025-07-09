package com.example.style_store_be_adminSP.service.impl;

import com.example.style_store_be.entity.SanPham;
import com.example.style_store_be.entity.XuatXu;
import com.example.style_store_be_adminSP.entity.ChatLieuAdm;
import com.example.style_store_be_adminSP.entity.KichThuocAdm;
import com.example.style_store_be_adminSP.entity.SanPhamAdm;
import com.example.style_store_be_adminSP.entity.XuatXuAdm;
import com.example.style_store_be_adminSP.reposytory.XuatXuRepoAdm;
import com.example.style_store_be_adminSP.service.ICommonServiceAdm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class XuatXuServiceImplAdm implements ICommonServiceAdm<XuatXuAdm> {
    @Autowired
    private XuatXuRepoAdm xuatXuRepository;

    @Override
    public Page<XuatXuAdm> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return xuatXuRepository.findAllByOrderByNgayTaoDesc(pageable);
    }
    public Page<XuatXuAdm> searchByNameOrCode(String keyword, int page, int size) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new RuntimeException("Từ khóa tìm kiếm không được để trống");
        }
        Pageable pageable = PageRequest.of(page, size);
        return xuatXuRepository.findByTenOrMaContainingIgnoreCase(keyword.trim(), pageable);
    }
    @Override
    public XuatXuAdm getOne(Long id) {
        return xuatXuRepository.findById(id).orElse(null);
    }

    @Override
    public XuatXuAdm add(XuatXuAdm object) {
        validate(object);
        if (object.getMa() == null || object.getMa().trim().isEmpty()) {
            object.setMa("XX-" + UUID.randomUUID().toString().substring(0, 8));
        }
        object.setTrangThai(1); // Mặc định là đang hoạt động
        object.setNgayTao(LocalDateTime.now());
        object.setNgaySua(null);
        object.setNgayXoa(null);
        return xuatXuRepository.save(object);
    }

    @Override
    public XuatXuAdm update(XuatXuAdm object) {
        if (object.getId() == null) {
            throw new RuntimeException("ID không được để trống khi cập nhật");
        }
        validate(object);

        XuatXuAdm existing = xuatXuRepository.findById(object.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Kích Thước với id: " + object.getId()));

        existing.setTen(object.getTen());
        existing.setMoTa(object.getMoTa());
        existing.setNgaySua(LocalDateTime.now());


        return  xuatXuRepository.save(existing);
    }
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new RuntimeException("ID không được để trống khi xóa");
        }
        XuatXuAdm existing = xuatXuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Xuất Xứ với id: " + id));

        if (existing.getTrangThai() == 1) {
            existing.setNgayXoa(LocalDateTime.now());
            existing.setTrangThai(0);
        } else {
            existing.setNgayXoa(null);
            existing.setTrangThai(1);
            existing.setNgaySua(LocalDateTime.now());
        }

        xuatXuRepository.save(existing);
    }

    public Page<XuatXuAdm> getActive(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return xuatXuRepository.findByNgayXoaIsNull(pageable);
    }

    public Page<XuatXuAdm> getDeleted(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return xuatXuRepository.findByNgayXoaIsNotNull(pageable);
    }
    public Page<XuatXuAdm> searchByName(String ten, int page, int size) {
        if (ten == null || ten.trim().isEmpty()) {
            throw new RuntimeException("Tên chất liệu tìm kiếm không được để trống");
        }
        Pageable pageable = PageRequest.of(page, size);
        return xuatXuRepository.findByTenContainingIgnoreCase(ten.trim(), pageable);
    }

    private void validate(XuatXuAdm object) {
        if (object.getTen() == null || object.getTen().trim().isEmpty()) {
            throw new RuntimeException("Tên xuất xứ không được để trống");
        }
        if (!object.getTen().trim().matches("^[\\p{L}\\s]+$")) {
            throw new RuntimeException("Tên xuất xứ chỉ được chứa chữ cái và khoảng trắng, không chứa số hoặc ký tự đặc biệt");
        }
        if (object.getTen().length() > 50) {
            throw new RuntimeException("Tên xuất xứ không được vượt quá 50 ký tự");
        }
        Optional<XuatXuAdm> existing = xuatXuRepository.findByTen(object.getTen().trim());
        if (existing.isPresent()) {
            // Nếu đang cập nhật thì phải bỏ qua chính nó
            if (object.getId() == null || !existing.get().getId().equals(object.getId())) {
                throw new RuntimeException("Kích thước đã tồn tại");
            }
        }

    }
}
