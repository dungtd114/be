package com.example.style_store_be_adminSP.controller;

import com.example.style_store_be_adminSP.entity.SanPhamAdm;
import com.example.style_store_be_adminSP.service.impl.SanPhamAdminServiceImplAdm;
import com.example.style_store_be_adminSP.service.impl.SanPhamAdminServiceImplAdm.SanPhamException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin-san-pham")
@CrossOrigin("*")
public class SanPhamAdminControllerAdm {
    @Autowired
    private SanPhamAdminServiceImplAdm sanPhamAdminService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<SanPhamAdm> sanPhamPage = sanPhamAdminService.getAll(page, size);
            return ResponseEntity.ok(sanPhamPage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Lỗi khi lấy danh sách sản phẩm: " + e.getMessage()));
        }
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<SanPhamAdm> sanPhamPage = sanPhamAdminService.getActive(page, size);
            return ResponseEntity.ok(sanPhamPage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Lỗi khi lấy danh sách sản phẩm hoạt động: " + e.getMessage()));
        }
    }

    @GetMapping("/deleted")
    public ResponseEntity<?> getDeleted(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<SanPhamAdm> sanPhamPage = sanPhamAdminService.getDeleted(page, size);
            return ResponseEntity.ok(sanPhamPage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Lỗi khi lấy danh sách sản phẩm đã xóa: " + e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchWithTotalQuantity(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<SanPhamAdminServiceImplAdm.SanPhamWithQuantity> sanPhamPage =
                    sanPhamAdminService.searchSanPhamWithTotalQuantity(search, page, size);
            return ResponseEntity.ok(sanPhamPage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Lỗi khi tìm kiếm sản phẩm: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        try {
            SanPhamAdm sanPham = sanPhamAdminService.getOne(id);
            return ResponseEntity.ok(sanPham);
        } catch (SanPhamException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody SanPhamAdm sanPham) {
        try {
            SanPhamAdm createdSanPham = sanPhamAdminService.add(sanPham);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSanPham);
        } catch (SanPhamException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody SanPhamAdm sanPham) {
        try {
            sanPham.setId(id);
            SanPhamAdm updatedSanPham = sanPhamAdminService.update(sanPham);
            return ResponseEntity.ok(updatedSanPham);
        } catch (SanPhamException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            sanPhamAdminService.delete(id);
            return ResponseEntity.ok(Map.of("message", "Xóa mềm sản phẩm thành công"));
        } catch (SanPhamException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/restore/{id}")
    public ResponseEntity<?> restore(@PathVariable Long id) {
        try {
            sanPhamAdminService.delete(id);
            return ResponseEntity.ok(Map.of("message", "Khôi phục sản phẩm thành công"));
        } catch (SanPhamException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/toggle-status/{id}")
    public ResponseEntity<?> toggleStatus(@PathVariable Long id) {
        try {
            SanPhamAdm sanPham = sanPhamAdminService.getOne(id);
            if (sanPham.getTrangThai() == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Không thể thay đổi trạng thái của sản phẩm hết hàng"));
            }
            sanPham.setTrangThai(sanPham.getTrangThai() == 1 ? 2 : 1);
            SanPhamAdm updatedSanPham = sanPhamAdminService.update(sanPham);
            return ResponseEntity.ok(Map.of(
                    "message", "Chuyển đổi trạng thái sản phẩm thành công",
                    "sanPham", updatedSanPham
            ));
        } catch (SanPhamException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}