package com.example.style_store_be_adminSell.controller;


import com.example.style_store_be_adminSell.entity.NguoiDungSAdm;
import com.example.style_store_be_adminSell.service.NguoiDungSAdmService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/api/admin/nguoi-dung")
@CrossOrigin(origins = "http://localhost:3000")
public class NguoiDungSAdmController {
    @Autowired
    private NguoiDungSAdmService nguoiDungSAdmService;

    @GetMapping("/sdt/{sdt}")
    public ResponseEntity<NguoiDungSAdm> getKhachHangBySDT(@PathVariable String sdt) {
        if (sdt == null || sdt.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            NguoiDungSAdm nguoiDung = nguoiDungSAdmService.searchNguoiDungBySDT(sdt, 3L); // 3L = id chức vụ KH
            return ResponseEntity.ok(nguoiDung);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/searchID/{id}")
    public ResponseEntity<NguoiDungSAdm> getByID(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().body(null);
        }
        NguoiDungSAdm result = nguoiDungSAdmService.searchUserById(id);
        return ResponseEntity.ok(result);
    }

}
