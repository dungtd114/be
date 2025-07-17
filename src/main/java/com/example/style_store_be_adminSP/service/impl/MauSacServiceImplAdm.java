package com.example.style_store_be_adminSP.service.impl;

import com.example.style_store_be_adminSP.entity.MauSacSpAdm;
import com.example.style_store_be_adminSP.reposytory.MauSacSPRepoAdm;
import com.example.style_store_be_adminSP.service.ICommonServiceAdm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MauSacServiceImplAdm implements ICommonServiceAdm<MauSacSpAdm> {
    @Autowired
    private MauSacSPRepoAdm mauSacSPRepository;

    @Override
    public Page<MauSacSpAdm> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return mauSacSPRepository.findAllByOrderByNgayTaoDesc(pageable); // Sửa để sắp xếp theo ngày tạo
    }

    @Override
    public MauSacSpAdm getOne(Long id) {
        return mauSacSPRepository.findById(id).orElse(null);
    }

    @Override
    public MauSacSpAdm add(MauSacSpAdm object) {
        validate(object); // Kiểm tra tên trước khi thêm

        // Kiểm tra tính hợp lệ của mã (mã hex)
        if (object.getMa() == null || !object.getMa().matches("^#[0-9A-Fa-f]{6}$")) {
            throw new RuntimeException("Mã màu không hợp lệ. Vui lòng nhập mã hex hợp lệ (VD: #FF0000)");
        }

        // Kiểm tra xem mã đã tồn tại chưa
        Optional<MauSacSpAdm> existingByMa = mauSacSPRepository.findByMa(object.getMa());
        if (existingByMa.isPresent()) {
            throw new RuntimeException("Mã màu " + object.getMa() + " đã tồn tại");
        }

        object.setTrangThai(1); // Mặc định là đang hoạt động
        object.setNgayTao(LocalDateTime.now());
        object.setNgaySua(null);
        object.setNgayXoa(null);

        return mauSacSPRepository.save(object);
    }


    @Override
    public MauSacSpAdm update(MauSacSpAdm object) {
        if (object.getId() == null) {
            throw new RuntimeException("ID không được để trống khi cập nhật");
        }

        validate(object); // Kiểm tra tên trước khi cập nhật

        // Kiểm tra tính hợp lệ của mã (mã hex)
        if (object.getMa() == null || !object.getMa().matches("^#[0-9A-Fa-f]{6}$")) {
            throw new RuntimeException("Mã màu không hợp lệ. Vui lòng nhập mã hex hợp lệ (VD: #FF0000)");
        }

        MauSacSpAdm existing = mauSacSPRepository.findById(object.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy màu sắc với ID: " + object.getId()));

        // Kiểm tra xem mã mới có trùng với mã màu khác (ngoại trừ chính nó)
        Optional<MauSacSpAdm> existingByMa = mauSacSPRepository.findByMa(object.getMa());
        if (existingByMa.isPresent() && !existingByMa.get().getId().equals(object.getId())) {
            throw new RuntimeException("Mã màu " + object.getMa() + " đã tồn tại");
        }

        existing.setMa(object.getMa());
        existing.setTen(object.getTen());
        existing.setMoTa(object.getMoTa());
        existing.setNgaySua(LocalDateTime.now());

        return mauSacSPRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new RuntimeException("ID không được để trống khi xóa");
        }
        MauSacSpAdm existing = mauSacSPRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy màu sắc với ID: " + id));

        if (existing.getTrangThai() == 1) {
            existing.setNgayXoa(LocalDateTime.now());
            existing.setTrangThai(0);
        } else {
            existing.setNgayXoa(null);
            existing.setTrangThai(1);
            existing.setNgaySua(LocalDateTime.now());
        }

        mauSacSPRepository.save(existing);
    }

    public Page<MauSacSpAdm> getActive(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return mauSacSPRepository.findByNgayXoaIsNull(pageable);
    }

    public Page<MauSacSpAdm> getDeleted(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return mauSacSPRepository.findByNgayXoaIsNotNull(pageable);
    }

    public Page<MauSacSpAdm> searchByKeyword(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return mauSacSPRepository.searchAllByKeyword(
                (keyword == null || keyword.trim().isEmpty()) ? null : keyword.trim(),
                pageable
        );
    }


    private void validate(MauSacSpAdm object) {
        if (object.getTen() == null || object.getTen().trim().isEmpty()) {
            throw new RuntimeException("Tên màu sắc không được để trống");
        }
        if (object.getTen().length() > 50) {
            throw new RuntimeException("Tên màu sắc không được vượt quá 50 ký tự");
        }
        if (!object.getTen().trim().matches("^[\\p{L}\\s]+$")) {
            throw new RuntimeException("Tên màu sắc chỉ được chứa chữ cái và khoảng trắng, không chứa số hoặc ký tự đặc biệt");
        }
        Optional<MauSacSpAdm> existing = mauSacSPRepository.findByTen(object.getTen().trim());
        if (existing.isPresent() && (object.getId() == null || !existing.get().getId().equals(object.getId()))) {
            throw new RuntimeException("Tên màu sắc " + object.getTen() + " đã tồn tại");
        }
    }
}