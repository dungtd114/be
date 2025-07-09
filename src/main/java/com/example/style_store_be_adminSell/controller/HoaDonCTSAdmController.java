package com.example.style_store_be_adminSell.controller;

import com.example.style_store_be_adminSell.dto.HoaDonCTSAdmDto;
import com.example.style_store_be_adminSell.repository.SanPhamCTSAdmRepo;
import com.example.style_store_be_adminSell.service.HoaDonCTSAdmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Controller
@RestController
@RequestMapping("/api/admin/hoa-don-chi-tiet")
@CrossOrigin(origins = "http://localhost:3000")
public class HoaDonCTSAdmController {
    @Autowired
    private HoaDonCTSAdmService service;
    @Autowired
    private HoaDonCTSAdmService hoaDonCTSAdmService;
    @Autowired
    private SanPhamCTSAdmRepo sanPhamCTSAdmRepo;

    @GetMapping
    public ResponseEntity<Page<HoaDonCTSAdmDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        if (page < 0 || size <= 0) {
            return ResponseEntity.badRequest().body(null);
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<HoaDonCTSAdmDto> result = service.getAllHoaDonCTS(pageable);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/addHDCTC")
    public ResponseEntity<?> addHDCT(@RequestBody HoaDonCTSAdmDto hoaDonCTSAdmDto) {
        if (hoaDonCTSAdmDto == null||hoaDonCTSAdmDto.getHoaDonId()==null
        ||hoaDonCTSAdmDto.getSanPhamCTId()==null) {
            Map<String, String> error = new HashMap<>();
            error.put("eror", "Dữ liệu k hợp lệ, không được để trống");
            return ResponseEntity.badRequest().body(error);
        }
        try{
            HoaDonCTSAdmDto create = service.addHDCTS(hoaDonCTSAdmDto);
            return ResponseEntity.ok(create);
        }catch (IllegalArgumentException e){
            Map<String, String> error = new HashMap<>();
            error.put("eror", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    @GetMapping("/idHD/{id}")
    public ResponseEntity<Page<HoaDonCTSAdmDto>> getByID(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        {
            if (id == null||page < 0 || size <= 0) {
                return ResponseEntity.badRequest().body(null);
            }
            Pageable pageable = PageRequest.of(page, size);
            Page<HoaDonCTSAdmDto> result = hoaDonCTSAdmService.findByIdHoaDon(id,pageable);
            return ResponseEntity.ok(result);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateHoaDon(@PathVariable Long id, @RequestBody HoaDonCTSAdmDto hoaDonCTSAdmDto) {
        if (id == null || id <= 0 || hoaDonCTSAdmDto == null || hoaDonCTSAdmDto.getId() == null ||
                hoaDonCTSAdmDto.getSanPhamCTId() == null || hoaDonCTSAdmDto.getHoaDonId() == null ||
                hoaDonCTSAdmDto.getSoLuong() == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Dữ liệu đầu vào không hợp lệ: Các trường bắt buộc không được để trống");
            return ResponseEntity.badRequest().body(error);
        }
        try {
            HoaDonCTSAdmDto updated = hoaDonCTSAdmService.updateHDCTS(id, hoaDonCTSAdmDto);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            hoaDonCTSAdmService.deleteHDCTS(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
