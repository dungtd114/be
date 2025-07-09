
package com.example.style_store_be_adminSP.service.impl;

import com.example.style_store_be_adminSP.entity.ChatLieuAdm;
import com.example.style_store_be_adminSP.reposytory.ChatLieuRepoAdm;
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
public class ChatLieuServiceImplAdm implements ICommonServiceAdm<ChatLieuAdm> {
    @Autowired
    private ChatLieuRepoAdm chatLieuRepository;

    @Override
    public Page<ChatLieuAdm> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size); // Loại bỏ Sort.by("ngayTao").descending()
        return chatLieuRepository.findAllByOrderByNgayTaoDesc(pageable);
    }

    @Override
    public ChatLieuAdm getOne(Long id) {
        return chatLieuRepository.findById(id).orElse(null);
    }

    @Override
    public ChatLieuAdm add(ChatLieuAdm object) {
        validate(object);
        if (object.getMa() == null || object.getMa().trim().isEmpty()) {
            object.setMa("CL-" + UUID.randomUUID().toString().substring(0, 8));
        }
        object.setTrangThai(1);
        object.setNgayTao(LocalDateTime.now());
        object.setNgaySua(null);
        object.setNgayXoa(null);
        return chatLieuRepository.save(object);
    }

    @Override
    public ChatLieuAdm update(ChatLieuAdm object) {
        if (object.getId() == null) {
            throw new RuntimeException("ID không được để trống khi cập nhật");
        }
        validate(object);
        ChatLieuAdm existing = chatLieuRepository.findById(object.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ChatLieu với id: " + object.getId()));
        existing.setTen(object.getTen());
        existing.setMoTa(object.getMoTa());
        existing.setNgaySua(LocalDateTime.now());
        return chatLieuRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new RuntimeException("ID không được để trống khi xóa");
        }
        ChatLieuAdm existing = chatLieuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ChatLieu với id: " + id));
        if (existing.getTrangThai() == 1) {
            existing.setNgayXoa(LocalDateTime.now());
            existing.setTrangThai(0);
        } else {
            existing.setNgayXoa(null);
            existing.setTrangThai(1);
            existing.setNgaySua(LocalDateTime.now());
        }
        chatLieuRepository.save(existing);
    }

    public Page<ChatLieuAdm> searchByName(String ten, int page, int size) {
        if (ten == null || ten.trim().isEmpty()) {
            throw new RuntimeException("Tên chất liệu tìm kiếm không được để trống");
        }
        Pageable pageable = PageRequest.of(page, size);
        return chatLieuRepository.findByTenContainingIgnoreCase(ten.trim(), pageable);
    }

    public Page<ChatLieuAdm> searchByNameOrCode(String keyword, int page, int size) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new RuntimeException("Từ khóa tìm kiếm không được để trống");
        }
        Pageable pageable = PageRequest.of(page, size);
        return chatLieuRepository.findByTenOrMaContainingIgnoreCase(keyword.trim(), pageable);
    }

    public Page<ChatLieuAdm> getActive(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return chatLieuRepository.findByNgayXoaIsNull(pageable);
    }

    public Page<ChatLieuAdm> getDeleted(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return chatLieuRepository.findByNgayXoaIsNotNull(pageable);
    }

    private void validate(ChatLieuAdm object) {
        if (object.getTen() == null || object.getTen().trim().isEmpty()) {
            throw new RuntimeException("Tên chất liệu không được để trống");
        }
        if (object.getTen().length() > 50) {
            throw new RuntimeException("Tên chất liệu không được vượt quá 50 ký tự");
        }
        if (!object.getTen().trim().matches("^[\\p{L}\\s]+$")) {
            throw new RuntimeException("Tên chất liệu chỉ được chứa chữ cái và khoảng trắng, không chứa số hoặc ký tự đặc biệt");
        }
        Optional<ChatLieuAdm> existing = chatLieuRepository.findByTen(object.getTen().trim());
        if (existing.isPresent()) {
            if (object.getId() == null || !existing.get().getId().equals(object.getId())) {
                throw new RuntimeException("Chất liệu đã tồn tại");
            }
        }
        Optional<ChatLieuAdm> existingByMa = chatLieuRepository.findByMa(object.getMa().trim());
        if (existingByMa.isPresent()) {
            if (object.getId() == null || !existingByMa.get().getId().equals(object.getId())) {
                throw new RuntimeException("Mã chất liệu đã tồn tại");
            }
        }
    }
}
