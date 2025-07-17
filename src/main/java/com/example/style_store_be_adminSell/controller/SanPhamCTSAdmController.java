package com.example.style_store_be_adminSell.controller;


import com.example.style_store_be_adminSP.dto.SanPhamCtDTOAdm;
import com.example.style_store_be_adminSell.service.SanPhamCTSAdmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/san-pham-ct")
@CrossOrigin("*")
public class SanPhamCTSAdmController {
    @Autowired
    private SanPhamCTSAdmService sanPhamCTSAdmService;

    @GetMapping
    public ResponseEntity<Page<SanPhamCtDTOAdm>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (page < 0 || size <= 0) {
            return ResponseEntity.badRequest().body(null);
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<SanPhamCtDTOAdm> result = sanPhamCTSAdmService.findAll(pageable);
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
        Page<SanPhamCtDTOAdm> result = sanPhamCTSAdmService.findByTrangThai(trangThai, pageable);
        return ResponseEntity.ok(result);
    }

    // Tìm kiếm sản phẩm chi tiết theo ma sản phẩm với phân trang
    @GetMapping("/ma")
    public ResponseEntity<Page<SanPhamCtDTOAdm>> searchByMa(
            @RequestParam String ma,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (ma == null || ma.trim().isEmpty() || page < 0 || size <= 0) {
            return ResponseEntity.badRequest().body(null);
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<SanPhamCtDTOAdm> result = sanPhamCTSAdmService.searchBySanPhamMa(ma, pageable);
        return ResponseEntity.ok(result);
    }

    // Tìm kiếm sản phẩm chi tiết theo ma sản phẩm với phân trang
    @GetMapping("/ten")
    public ResponseEntity<Page<SanPhamCtDTOAdm>> searchByTen(
            @RequestParam String ten,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (ten == null || ten.trim().isEmpty() || page < 0 || size <= 0) {
            return ResponseEntity.badRequest().body(null);
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<SanPhamCtDTOAdm> result = sanPhamCTSAdmService.searchBySanPhamTen(ten, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/filter")
    public Page<SanPhamCtDTOAdm> filterSanPhamCt(
            @RequestParam(required = false) Long sanPhamId,
            @RequestParam(required = false) String sanPhamMa,
            @RequestParam(required = false) String sanPhamTen,
            @RequestParam(required = false) Long mauSacId,
            @RequestParam(required = false) Long thuongHieuId,
            @RequestParam(required = false) Long kichThuocId,
            @RequestParam(required = false) Long xuatXuId,
            @RequestParam(required = false) Long chatLieuId,
            Pageable pageable) {
        return sanPhamCTSAdmService.filterByAttributes(
                sanPhamId, sanPhamMa, sanPhamTen, mauSacId, thuongHieuId, kichThuocId, xuatXuId, chatLieuId, pageable
        );
    }
}
