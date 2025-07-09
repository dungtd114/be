package com.example.style_store_be_adminSP.controller;

import com.example.style_store_be.entity.KichThuoc;
import com.example.style_store_be_adminSP.entity.ChatLieuAdm;
import com.example.style_store_be_adminSP.entity.KichThuocAdm;
import com.example.style_store_be_adminSP.service.impl.KichThuocServiceImplAdm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kich-thuoc")
@CrossOrigin("*") // Cho phép frontend truy cập
public class KichThuocControllerAdm {

    @Autowired
    private KichThuocServiceImplAdm kichThuocService;

    @GetMapping("/all")
    public ResponseEntity<Page<KichThuocAdm>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<KichThuocAdm> kichThuocPage = kichThuocService.getAll(page, size);
        return ResponseEntity.ok(kichThuocPage);
    }

    @GetMapping("/active")
    public ResponseEntity<Page<KichThuocAdm>> getActive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<KichThuocAdm> kichThuocPage = kichThuocService.getActive(page, size);
        return ResponseEntity.ok(kichThuocPage);
    }

    @GetMapping("/deleted")
    public ResponseEntity<Page<KichThuocAdm>> getDeleted(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<KichThuocAdm> kichThuocPage = kichThuocService.getDeleted(page, size);
        return ResponseEntity.ok(kichThuocPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KichThuocAdm> getOne(@PathVariable Long id) {
        KichThuocAdm kichThuoc = kichThuocService.getOne(id);
        return kichThuoc != null ? ResponseEntity.ok(kichThuoc) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<KichThuocAdm> create(@RequestBody KichThuocAdm chatLieu) {
        try {
            KichThuocAdm createdChatLieu = kichThuocService.add(chatLieu);
            return ResponseEntity.status(201).body(createdChatLieu); // Trả về đối tượng vừa tạo
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }
    }
    @GetMapping("/search-by-name-or-code")
    public ResponseEntity<Page<KichThuocAdm>> searchByNameOrCode(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<KichThuocAdm> chatLieuPage = kichThuocService.searchByNameOrCode(keyword, page, size);
            return ResponseEntity.ok(chatLieuPage);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody KichThuocAdm kichThuoc) {
        try {
            kichThuoc.setId(id); // Gán lại ID cho chắc chắn
            kichThuocService.update(kichThuoc);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }
    }

    @PutMapping("/toggle-status/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            kichThuocService.delete(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
    @GetMapping("/search")
    public ResponseEntity<Page<KichThuocAdm>> searchByName(
            @RequestParam String ten,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<KichThuocAdm> chatLieuPage = kichThuocService.searchByName(ten, page, size);
            return ResponseEntity.ok(chatLieuPage);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }
    }
}
