package com.example.style_store_be_adminSP.controller;

import com.example.style_store_be_adminSP.dto.HinhAnhMauSacDTOAdm;
import com.example.style_store_be_adminSP.entity.HinhAnhMauSacAdm;
import com.example.style_store_be_adminSP.service.HinhAnhMauSacServiceAdm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/hinh-anh-mau-sac")
@CrossOrigin("*")
public class HinhAnhMauSacControllerAdm {
    @Autowired
    private HinhAnhMauSacServiceAdm hinhAnhMauSacService;

    @GetMapping("/all")
    public ResponseEntity<Page<HinhAnhMauSacAdm>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<HinhAnhMauSacAdm> hinhAnhPage = hinhAnhMauSacService.getAll(page, size);
        return ResponseEntity.ok(hinhAnhPage);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<HinhAnhMauSacAdm>> searchByHinhAnh(
            @RequestParam String hinhAnh,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<HinhAnhMauSacAdm> hinhAnhPage = hinhAnhMauSacService.searchByHinhAnh(hinhAnh, page, size);
            return ResponseEntity.ok(hinhAnhPage);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }
    }

    @GetMapping("/active")
    public ResponseEntity<Page<HinhAnhMauSacAdm>> getActive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<HinhAnhMauSacAdm> hinhAnhPage = hinhAnhMauSacService.getActive(page, size);
        return ResponseEntity.ok(hinhAnhPage);
    }

    @GetMapping("/deleted")
    public ResponseEntity<Page<HinhAnhMauSacAdm>> getDeleted(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<HinhAnhMauSacAdm> hinhAnhPage = hinhAnhMauSacService.getDeleted(page, size);
        return ResponseEntity.ok(hinhAnhPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HinhAnhMauSacAdm> getOne(@PathVariable Long id) {
        HinhAnhMauSacAdm hinhAnh = hinhAnhMauSacService.getOne(id);
        return hinhAnh != null ? ResponseEntity.ok(hinhAnh) : ResponseEntity.notFound().build();
    }
    @PostMapping("/upload")
    public ResponseEntity<HinhAnhMauSacDTOAdm> uploadHinhAnh(
            @RequestParam("file") MultipartFile file,
            @RequestParam("mauSacId") Long mauSacId) {

        String fileName = hinhAnhMauSacService.uploadImage(file, mauSacId);

        // Truy vấn lại ảnh vừa upload
        List<HinhAnhMauSacAdm> list = hinhAnhMauSacService.getByMauSacId(mauSacId);
        HinhAnhMauSacAdm last = list.get(list.size() - 1); // lấy ảnh mới nhất

        HinhAnhMauSacDTOAdm dto = new HinhAnhMauSacDTOAdm();
        dto.setId(last.getId());
        dto.setHinhAnh(last.getHinhAnh());
        dto.setMauSacId(mauSacId);
        dto.setTenMauSac(last.getMauSac().getTen());

        return ResponseEntity.ok(dto);
    }




    @PostMapping
    public ResponseEntity<Void> create(@RequestBody HinhAnhMauSacAdm hinhAnhMauSac) {
        try {
            hinhAnhMauSacService.add(hinhAnhMauSac);
            return ResponseEntity.status(201).build(); // 201 Created
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }
    }
    @GetMapping("/mau-sac/{mauSacId}")
    public ResponseEntity<List<HinhAnhMauSacDTOAdm>> getByMauSacId(@PathVariable Long mauSacId) {
        List<HinhAnhMauSacAdm> hinhAnhList = hinhAnhMauSacService.getByMauSacId(mauSacId);
        List<HinhAnhMauSacDTOAdm> hinhAnhDTOs = hinhAnhList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(hinhAnhDTOs);
    }

    private HinhAnhMauSacDTOAdm mapToDTO(HinhAnhMauSacAdm entity) {
        HinhAnhMauSacDTOAdm dto = new HinhAnhMauSacDTOAdm();
        dto.setId(entity.getId());
        dto.setHinhAnh(entity.getHinhAnh());
        dto.setMauSacId(entity.getMauSac().getId());
        dto.setTenMauSac(entity.getMauSac().getTen());
        return dto;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody HinhAnhMauSacAdm hinhAnhMauSac) {
        try {
            hinhAnhMauSac.setId(id); // Gán ID từ path variable
            hinhAnhMauSacService.update(hinhAnhMauSac);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }
    }

    @PutMapping("/toggle-status/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            hinhAnhMauSacService.delete(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}
