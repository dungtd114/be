package com.example.style_store_be_adminSell.controller;


import com.example.style_store_be.exception.AppException;
import com.example.style_store_be.exception.Errorcode;
import com.example.style_store_be_adminSell.dto.NguoiDungDto;
import com.example.style_store_be_adminSell.entity.DiaChiNhanSAdm;
import com.example.style_store_be_adminSell.entity.NguoiDungSAdm;
import com.example.style_store_be_adminSell.repository.DiaChiNhanSAdmRepo;
import com.example.style_store_be_adminSell.repository.NguoiDungSAdmRepo;
import com.example.style_store_be_adminSell.service.DiaChiNhanService;
import com.example.style_store_be_adminSell.service.NguoiDungSAdmService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api/admin/nguoi-dung")
@CrossOrigin(origins = "http://localhost:3000")
public class NguoiDungSAdmController {
    @Autowired
    private NguoiDungSAdmService nguoiDungSAdmService;
    @Autowired
    private DiaChiNhanSAdmRepo diaChiNhanSAdmRepo;
    @Autowired
    private NguoiDungSAdmRepo nguoiDungSAdmRepo;
    @Autowired
    private DiaChiNhanService diaChiNhanService;

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

    //Sử dụng địa chỉ nhận
    @PostMapping("/addND")
    public ResponseEntity<DiaChiNhanSAdm> themNguoiDung(@RequestBody NguoiDungDto dto) {
        DiaChiNhanSAdm diaChiNhanSAdm = nguoiDungSAdmService.addNguoiDung(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(diaChiNhanSAdm);
    }

    @GetMapping("/idDC/{id}")
    public ResponseEntity<DiaChiNhanSAdm> getByIdDC(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().body(null);
        }
        DiaChiNhanSAdm result = diaChiNhanSAdmRepo.findById(id).orElse(null);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/dcn/{id}")
    public ResponseEntity<?> getDcnById(@PathVariable Long id) {
        List<DiaChiNhanSAdm> ds = diaChiNhanSAdmRepo.findAllByNguoiDungSAdm_Id(id);
        return ResponseEntity.ok(ds);
    }

    @PostMapping("/addDCN")
    public ResponseEntity<?> addDCN(@RequestBody DiaChiNhanSAdm dcn) {
        try {
            // Validate dữ liệu đầu vào
            if (dcn.getNguoiDungSAdm() == null || dcn.getNguoiDungSAdm().getId() == null) {
                throw new AppException(Errorcode.MISSING_REQUIRED_FIELDS);
            }

            if (StringUtils.isEmpty(dcn.getDiaChi())) {
                throw new AppException(Errorcode.MISSING_REQUIRED_FIELDS);
            }
            dcn.setSoNha(dcn.getDiaChi());
            DiaChiNhanSAdm saved = diaChiNhanService.addDiaChiNhan(dcn);

            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            // Log lỗi chi tiết
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi thêm địa chỉ nhận: " + e.getMessage());
        }
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<NguoiDungSAdm> getKhachHangByEmail(@PathVariable String email) {
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            NguoiDungSAdm nguoiDung = nguoiDungSAdmRepo.findByEmail(email).orElse(null);
            return ResponseEntity.ok(nguoiDung);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
