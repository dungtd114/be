package com.example.style_store_be_adminSP.service.impl;

import com.example.style_store_be_adminSP.entity.KichThuocAdm;
import com.example.style_store_be_adminSP.entity.SanPhamAdm;
import com.example.style_store_be_adminSP.entity.ThuongHieuAdm;
import com.example.style_store_be_adminSP.reposytory.KichThuocRepoAdm;
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
public class KichThuocServiceImplAdm implements ICommonServiceAdm<KichThuocAdm> {
    @Autowired
    private KichThuocRepoAdm kichThuocRepository;

    @Override
    public Page<KichThuocAdm> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return kichThuocRepository.findAllByOrderByNgayTaoDesc(pageable);
    }
    public Page<KichThuocAdm> searchByNameOrCode(String keyword, int page, int size) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new RuntimeException("Từ khóa tìm kiếm không được để trống");
        }
        Pageable pageable = PageRequest.of(page, size);
        return kichThuocRepository.findByTenOrMaContainingIgnoreCase(keyword.trim(), pageable);
    }
    @Override
    public KichThuocAdm getOne(Long id) {
        return kichThuocRepository.findById(id).orElse(null);
    }

    @Override
    public KichThuocAdm add(KichThuocAdm object) {
        validate(object);
        if (object.getMa() == null || object.getMa().trim().isEmpty()) {
            object.setMa("KT-" + UUID.randomUUID().toString().substring(0, 8));
        }

        object.setTrangThai(1); // Mặc định là đang hoạt động
        object.setNgayTao(LocalDateTime.now());
        object.setNgaySua(null);
        object.setNgayXoa(null);


        return kichThuocRepository.save(object);

    }

    @Override
    public KichThuocAdm update(KichThuocAdm object) {
        if (object.getId() == null) {
            throw new RuntimeException("ID không được để trống khi cập nhật");
        }
        validate(object);

        KichThuocAdm existing = kichThuocRepository.findById(object.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Kích Thước với id: " + object.getId()));

        existing.setTen(object.getTen());
        existing.setMoTa(object.getMoTa());
        existing.setNgaySua(LocalDateTime.now());


        return  kichThuocRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new RuntimeException("ID không được để trống khi xóa");
        }
        KichThuocAdm existing = kichThuocRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Kích thước với id: " + id));

        if (existing.getTrangThai() == 1) {
            existing.setNgayXoa(LocalDateTime.now());
            existing.setTrangThai(0);
        } else {
            existing.setNgayXoa(null);
            existing.setTrangThai(1);
            existing.setNgaySua(LocalDateTime.now());
        }

        kichThuocRepository.save(existing);
    }

    public Page<KichThuocAdm> getActive(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return kichThuocRepository.findByNgayXoaIsNull(pageable);
    }

    public Page<KichThuocAdm> getDeleted(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return kichThuocRepository.findByNgayXoaIsNotNull(pageable);
    }
    public Page<KichThuocAdm> searchByName(String ten, int page, int size) {
        if (ten == null || ten.trim().isEmpty()) {
            throw new RuntimeException("Tên chất liệu tìm kiếm không được để trống");
        }
        Pageable pageable = PageRequest.of(page, size);
        return kichThuocRepository.findByTenContainingIgnoreCase(ten.trim(), pageable);
    }

    private void validate(KichThuocAdm object) {
        if (object.getTen() == null || object.getTen().trim().isEmpty()) {
            throw new RuntimeException("Tên kích thước không được để trống");
        }
        if (object.getTen().length() < 5) { // Sửa lại điều kiện để khớp với thông báo
            throw new RuntimeException("Tên kích thước phải có ít nhất 5 ký tự");
        }
        if (object.getTen().length() > 50) {
            throw new RuntimeException("Tên kích thước không được vượt quá 50 ký tự");
        }
        Optional<KichThuocAdm> existing = kichThuocRepository.findByTen(object.getTen().trim());
        if (existing.isPresent()) {
            // Nếu đang cập nhật thì phải bỏ qua chính nó
            if (object.getId() == null || !existing.get().getId().equals(object.getId())) {
                throw new RuntimeException("Kích thước đã tồn tại");
            }
        }

    }
}
