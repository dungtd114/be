package com.example.style_store_be_adminSP.controller;

import com.example.style_store_be_adminSP.entity.MauSacSpAdm;
import com.example.style_store_be_adminSP.service.impl.MauSacServiceImplAdm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mau-sac")
@CrossOrigin("*")
public class MauSacControllerAdm {

    @Autowired
    private MauSacServiceImplAdm mauSacService;

    @GetMapping("/all")
    public ResponseEntity<Page<MauSacSpAdm>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<MauSacSpAdm> mauSacPage = mauSacService.getAll(page, size);
        return ResponseEntity.ok(mauSacPage);
    }

    @GetMapping("/active")
    public ResponseEntity<Page<MauSacSpAdm>> getActive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<MauSacSpAdm> mauSacPage = mauSacService.getActive(page, size);
        return ResponseEntity.ok(mauSacPage);
    }

    @GetMapping("/deleted")
    public ResponseEntity<Page<MauSacSpAdm>> getDeleted(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<MauSacSpAdm> mauSacPage = mauSacService.getDeleted(page, size);
        return ResponseEntity.ok(mauSacPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MauSacSpAdm> getOne(@PathVariable Long id) {
        MauSacSpAdm mauSac = mauSacService.getOne(id);
        return mauSac != null ? ResponseEntity.ok(mauSac) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody MauSacSpAdm mauSac) {
        try {
            MauSacSpAdm savedMauSac = mauSacService.add(mauSac);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMauSac);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody MauSacSpAdm mauSac) {
        try {
            mauSac.setId(id);
            MauSacSpAdm updatedMauSac = mauSacService.update(mauSac);
            return ResponseEntity.ok(updatedMauSac);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    @PutMapping("/toggle-status/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            mauSacService.delete(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchByKeyword(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<MauSacSpAdm> mauSacPage = mauSacService.searchByKeyword(keyword, page, size);
            return ResponseEntity.ok(mauSacPage);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}