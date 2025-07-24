package com.example.style_store_be_adminSP.controller;

import com.example.style_store_be_adminSP.dto.SanPhamCtDTOAdm;
import com.example.style_store_be_adminSP.service.SanPhamCtServiceAdm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin-san-pham-chi-tiet")
@CrossOrigin("*")
public class SanPhamCtControllerAdm {
    @Autowired
    private SanPhamCtServiceAdm sanPhamCtService;

    // Lấy danh sách sản phẩm chi tiết với phân trang
    @GetMapping
    public ResponseEntity<Page<SanPhamCtDTOAdm>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (page < 0 || size <= 0) {
            return ResponseEntity.badRequest().body(null);
        }

        Page<SanPhamCtDTOAdm> result = sanPhamCtService.findAll(page,size);
        return ResponseEntity.ok(result);
    }

    // Lấy danh sách sản phẩm chi tiết theo sanPhamId với phân trang
    @GetMapping("/san-pham/{sanPhamId}")
    public ResponseEntity<Page<SanPhamCtDTOAdm>> getBySanPhamId(
            @PathVariable Long sanPhamId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (sanPhamId == null || sanPhamId <= 0 || page < 0 || size <= 0) {
            return ResponseEntity.badRequest().body(null);
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<SanPhamCtDTOAdm> result = sanPhamCtService.findBySanPhamId(sanPhamId, pageable);
        return ResponseEntity.ok(result);
    }

    // Lấy danh sách sản phẩm chi tiết theo trạng thái với phân trang
    @GetMapping("/trang-thai/{trangThai}")
    public ResponseEntity<Page<SanPhamCtDTOAdm>> getByTrangThai(
            @PathVariable Integer trangThai,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (trangThai == null || page < 0 || size <= 0) {
            return ResponseEntity.badRequest().body(null);
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<SanPhamCtDTOAdm> result = sanPhamCtService.findByTrangThai(trangThai, pageable);
        return ResponseEntity.ok(result);
    }

    // Tìm kiếm sản phẩm chi tiết theo tên sản phẩm với phân trang
    @GetMapping("/search")
    public ResponseEntity<Page<SanPhamCtDTOAdm>> searchByTen(
            @RequestParam String ten,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (ten == null || ten.trim().isEmpty() || page < 0 || size <= 0) {
            return ResponseEntity.badRequest().body(null);
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<SanPhamCtDTOAdm> result = sanPhamCtService.searchBySanPhamMa(ten, pageable);
        return ResponseEntity.ok(result);
    }

    // Lấy sản phẩm chi tiết theo ID
    @GetMapping("/{id}")
    public ResponseEntity<SanPhamCtDTOAdm> getById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        Optional<SanPhamCtDTOAdm> sanPhamCt = sanPhamCtService.findById(id);
        return sanPhamCt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Lấy sản phẩm chi tiết theo mã
    @GetMapping("/ma/{ma}")
    public ResponseEntity<SanPhamCtDTOAdm> getByMa(@PathVariable String ma) {
        if (ma == null || ma.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Optional<SanPhamCtDTOAdm> sanPhamCt = sanPhamCtService.findByMa(ma);
        return sanPhamCt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Thêm mới sản phẩm chi tiết
    @PostMapping
    public ResponseEntity<?> add(@RequestBody SanPhamCtDTOAdm sanPhamCtDTO) {
        Map<String, String> error = new HashMap<>();
        if (sanPhamCtDTO == null) {
            error.put("error", "Dữ liệu đầu vào không được để trống");
            return ResponseEntity.badRequest().body(error);
        }
        if (sanPhamCtDTO.getSanPhamId() == null) {
            error.put("error", "ID sản phẩm không được để trống");
            return ResponseEntity.badRequest().body(error);
        }
        if (sanPhamCtDTO.getMauSacId() == null) {
            error.put("error", "ID màu sắc không được để trống");
            return ResponseEntity.badRequest().body(error);
        }
        if (sanPhamCtDTO.getGiaBan() == null) {
            error.put("error", "Giá bán không được để trống");
            return ResponseEntity.badRequest().body(error);
        }
        if (sanPhamCtDTO.getSoLuong() == null) {
            error.put("error", "Số lượng không được để trống");
            return ResponseEntity.badRequest().body(error);
        }
        try {
            SanPhamCtDTOAdm created = sanPhamCtService.addSanPhamCt(sanPhamCtDTO);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // Cập nhật sản phẩm chi tiết
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody SanPhamCtDTOAdm sanPhamCtDTO) {
        Map<String, String> error = new HashMap<>();
        if (id == null || id <= 0) {
            error.put("error", "ID không hợp lệ");
            return ResponseEntity.badRequest().body(error);
        }
        if (sanPhamCtDTO == null) {
            error.put("error", "Dữ liệu đầu vào không được để trống");
            return ResponseEntity.badRequest().body(error);
        }
        if (sanPhamCtDTO.getSanPhamId() == null) {
            error.put("error", "ID sản phẩm không được để trống");
            return ResponseEntity.badRequest().body(error);
        }
        if (sanPhamCtDTO.getMauSacId() == null) {
            error.put("error", "ID màu sắc không được để trống");
            return ResponseEntity.badRequest().body(error);
        }
        if (sanPhamCtDTO.getGiaBan() == null) {
            error.put("error", "Giá bán không được để trống");
            return ResponseEntity.badRequest().body(error);
        }
        if (sanPhamCtDTO.getSoLuong() == null) {
            error.put("error", "Số lượng không được để trống");
            return ResponseEntity.badRequest().body(error);
        }
        try {
            SanPhamCtDTOAdm updated = sanPhamCtService.updateSanPhamCt(id, sanPhamCtDTO);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }


    @PutMapping("/toggle-status/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (id == null || id <= 0) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "ID không hợp lệ");
            return ResponseEntity.badRequest().body(error);
        }
        try {
            sanPhamCtService.deleteSanPhamCt(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Lấy danh sách sản phẩm chi tiết theo mauSacId với phân trang
    @GetMapping("/mau-sac/{mauSacId}")
    public ResponseEntity<Page<SanPhamCtDTOAdm>> getByMauSacId(
            @PathVariable Long mauSacId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (mauSacId == null || mauSacId <= 0 || page < 0 || size <= 0) {
            return ResponseEntity.badRequest().body(null);
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<SanPhamCtDTOAdm> result = sanPhamCtService.findByMauSacId(mauSacId, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/filter")
    public Page<SanPhamCtDTOAdm> filterSanPhamCt(
            @RequestParam(required = false) Long sanPhamId,
            @RequestParam(required = false) Long mauSacId,
            @RequestParam(required = false) Long thuongHieuId,
            @RequestParam(required = false) Long kichThuocId,
            @RequestParam(required = false) Long xuatXuId,
            @RequestParam(required = false) Long chatLieuId,
            Pageable pageable) {
        return sanPhamCtService.filterByAttributes(
                sanPhamId, mauSacId, thuongHieuId, kichThuocId, xuatXuId, chatLieuId, pageable
        );
    }


}
