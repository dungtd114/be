package com.example.style_store_be_adminSP.service.impl;

import com.example.style_store_be.entity.SanPham;
import com.example.style_store_be.entity.ThuongHieu;
import com.example.style_store_be_adminSP.entity.SanPhamAdm;
import com.example.style_store_be_adminSP.entity.ThuongHieuAdm;
import com.example.style_store_be_adminSP.entity.XuatXuAdm;
import com.example.style_store_be_adminSP.reposytory.ThuongHieuRepoAdm;
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
public class ThuongHieuServiceImplAdm implements ICommonServiceAdm<ThuongHieuAdm> {
    @Autowired
    private ThuongHieuRepoAdm thuongHieuRepository;

    @Override
    public Page<ThuongHieuAdm> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return thuongHieuRepository.findAllByOrderByNgayTaoDesc(pageable);
    }
    public Page<ThuongHieuAdm> searchByNameOrCode(String keyword, int page, int size) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new RuntimeException("Từ khóa tìm kiếm không được để trống");
        }
        Pageable pageable = PageRequest.of(page, size);
        return thuongHieuRepository.findByTenOrMaContainingIgnoreCase(keyword.trim(), pageable);
    }
    @Override
    public ThuongHieuAdm getOne(Long id) {
        return thuongHieuRepository.findById(id).orElse(null);
    }

    @Override
    public ThuongHieuAdm add(ThuongHieuAdm object) {
        validate(object);
        if (object.getMa() == null || object.getMa().trim().isEmpty()) {
            object.setMa("TH-" + UUID.randomUUID().toString().substring(0, 8));
        }

        object.setTrangThai(1); // Mặc định là đang hoạt động
        object.setNgayTao(LocalDateTime.now());
        object.setNgaySua(null);
        object.setNgayXoa(null);


        return  thuongHieuRepository.save(object);
    }

    @Override
    public ThuongHieuAdm update(ThuongHieuAdm object) {
        if (object.getId() == null) {
            throw new RuntimeException("ID không được để trống khi cập nhật");
        }
        validate(object);

        ThuongHieuAdm existing = thuongHieuRepository.findById(object.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Thương Hiệu với id: " + object.getId()));

        existing.setTen(object.getTen());
        existing.setMoTa(object.getMoTa());
        existing.setTrangThai(object.getTrangThai() != null ? object.getTrangThai() : existing.getTrangThai());

        // Nếu đã xóa mềm → khôi phục
        if (existing.getNgayXoa() != null) {
            existing.setNgayXoa(null);
            existing.setTrangThai(1);
        }

        existing.setNgaySua(LocalDateTime.now());

        return thuongHieuRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new RuntimeException("ID không được để trống khi xóa");
        }
        ThuongHieuAdm existing = thuongHieuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Thương Hiệu với id: " + id));

        if (existing.getTrangThai() == 1) {
            existing.setNgayXoa(LocalDateTime.now());
            existing.setTrangThai(0);
        } else {
            existing.setNgayXoa(null);
            existing.setTrangThai(1);
            existing.setNgaySua(LocalDateTime.now());
        }

        thuongHieuRepository.save(existing);
    }
    public Page<ThuongHieuAdm> searchByName(String ten, int page, int size) {
        if (ten == null || ten.trim().isEmpty()) {
            throw new RuntimeException("Tên chất liệu tìm kiếm không được để trống");
        }
        Pageable pageable = PageRequest.of(page, size);
        return thuongHieuRepository.findByTenContainingIgnoreCase(ten.trim(), pageable);
    }

    public Page<ThuongHieuAdm> getActive(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return thuongHieuRepository.findByNgayXoaIsNull(pageable);
    }

    public Page<ThuongHieuAdm> getDeleted(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return thuongHieuRepository.findByNgayXoaIsNotNull(pageable);
    }

    private void validate(ThuongHieuAdm object) {
        if (object.getTen() == null || object.getTen().trim().isEmpty()) {
            throw new RuntimeException("Tên thương hiệu không được để trống");
        }
        if (!object.getTen().trim().matches("^[\\p{L}\\s]+$")) {
            throw new RuntimeException("Tên thương hiệu chỉ được chứa chữ cái và khoảng trắng, không chứa số hoặc ký tự đặc biệt");
        }
        if (object.getTen().length() > 50) {
            throw new RuntimeException("Tên thương hiệu không được vượt quá 50 ký tự");
        }
        Optional<ThuongHieuAdm> existing = thuongHieuRepository.findByTen(object.getTen().trim());
        if (existing.isPresent()) {
            // Nếu đang cập nhật thì phải bỏ qua chính nó
            if (object.getId() == null || !existing.get().getId().equals(object.getId())) {
                throw new RuntimeException("Thương hiệu đã tồn tại");
            }
        }

    }
}
