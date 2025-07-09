package com.example.style_store_be_adminSP.controller;

import com.example.style_store_be.entity.XuatXu;
import com.example.style_store_be_adminSP.entity.ChatLieuAdm;
import com.example.style_store_be_adminSP.entity.KichThuocAdm;
import com.example.style_store_be_adminSP.entity.XuatXuAdm;
import com.example.style_store_be_adminSP.service.impl.XuatXuServiceImplAdm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/xuat-xu")
@CrossOrigin("*")
public class XuatXuControllerAdm {
    @Autowired
    private XuatXuServiceImplAdm xuatXuService;

    @GetMapping("/all")
    public ResponseEntity<Page<XuatXuAdm>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<XuatXuAdm> xuatXuPage = xuatXuService.getAll(page, size);
        return ResponseEntity.ok(xuatXuPage);
    }

    @GetMapping("/active")
    public ResponseEntity<Page<XuatXuAdm>> getActive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<XuatXuAdm> xuatXuPage = xuatXuService.getActive(page, size);
        return ResponseEntity.ok(xuatXuPage);
    }
    @GetMapping("/search")
    public ResponseEntity<Page<XuatXuAdm>> searchByName(
            @RequestParam String ten,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<XuatXuAdm> chatLieuPage = xuatXuService.searchByName(ten, page, size);
            return ResponseEntity.ok(chatLieuPage);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }
    }

    @GetMapping("/deleted")
    public ResponseEntity<Page<XuatXuAdm>> getDeleted(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<XuatXuAdm> xuatXuPage = xuatXuService.getDeleted(page, size);
        return ResponseEntity.ok(xuatXuPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<XuatXuAdm> getOne(@PathVariable Long id) {
        XuatXuAdm xuatXu = xuatXuService.getOne(id);
        return xuatXu != null ? ResponseEntity.ok(xuatXu) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<XuatXuAdm> create(@RequestBody XuatXuAdm chatLieu) {
        try {
            XuatXuAdm createdChatLieu = xuatXuService.add(chatLieu);
            return ResponseEntity.status(201).body(createdChatLieu); // Trả về đối tượng vừa tạo
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody XuatXuAdm kichThuoc) {
        try {
            kichThuoc.setId(id); // Gán lại ID cho chắc chắn
            xuatXuService.update(kichThuoc);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }
    }

    @PutMapping("/toggle-status/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            xuatXuService.delete(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
    @GetMapping("/search-by-name-or-code")
    public ResponseEntity<Page<XuatXuAdm>> searchByNameOrCode(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<XuatXuAdm> chatLieuPage = xuatXuService.searchByNameOrCode(keyword, page, size);
            return ResponseEntity.ok(chatLieuPage);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }
    }
}
