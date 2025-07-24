package com.example.style_store_be_adminSell.controller;

import com.example.style_store_be_adminSell.dto.HoaDonSAdmDto;
import com.example.style_store_be_adminSell.entity.HoaDonSAdm;
import com.example.style_store_be_adminSell.entity.NguoiDungSAdm;
import com.example.style_store_be_adminSell.entity.PtThanhToanSAdm;
import com.example.style_store_be_adminSell.repository.HoaDonSAdmRepo;
import com.example.style_store_be_adminSell.repository.NguoiDungSAdmRepo;
import com.example.style_store_be_adminSell.repository.PtThanhToanSAdmRepo;
import com.example.style_store_be_adminSell.service.HoaDonSAdmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RestController
@RequestMapping("/api/admin/hoa-don")
@CrossOrigin(origins = "http://localhost:3000")
public class HoaDonSAdmController {
    @Autowired
    private HoaDonSAdmService hoaDonSAdmService;
    @Autowired
    private HoaDonSAdmRepo hoaDonSAdmRepo;
    @Autowired
    private NguoiDungSAdmRepo nguoiDungSAdmRepo;
    @Autowired
    private PtThanhToanSAdmRepo ptThanhToanSAdmRepo;

    @GetMapping
    public ResponseEntity<Page<HoaDonSAdmDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        if (page < 0 || size <= 0) {
            return ResponseEntity.badRequest().body(null);
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<HoaDonSAdmDto> result = hoaDonSAdmService.findAll(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/trang-thai/{trangThai}")
    public ResponseEntity<Page<HoaDonSAdmDto>> getAllByTrangThai(
            @PathVariable Integer trangThai,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        if (trangThai == null||page < 0 || size <= 0) {
            return ResponseEntity.badRequest().body(null);
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<HoaDonSAdmDto> result = hoaDonSAdmService.findByTrangThai(trangThai,pageable);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/addHDC")
    public ResponseEntity<?> addHDWithNullIdNguoiTao(@RequestBody HoaDonSAdmDto hoaDonSDtoAdm) {
        if (hoaDonSDtoAdm == null||hoaDonSDtoAdm.getNguoiTaoId()==null) {
            Map<String, String> error = new HashMap<>();
            error.put("eror", "Dữ liệu k hợp lệ, không được để trống");
            return ResponseEntity.badRequest().body(error);
        }
        try{
            HoaDonSAdmDto create = hoaDonSAdmService.addHoaDon(hoaDonSDtoAdm);
            return ResponseEntity.ok(create);
        }catch (IllegalArgumentException e){
            Map<String, String> error = new HashMap<>();
            error.put("eror", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/ma/{ma}")
    public ResponseEntity<HoaDonSAdm> getByMa(@PathVariable String ma) {
        if (ma == null) {
               return ResponseEntity.badRequest().body(null);
        }
        HoaDonSAdm result = hoaDonSAdmService.findOne(ma);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/searchID/{id}")
    public ResponseEntity<HoaDonSAdm> getByID(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().body(null);
        }
        HoaDonSAdm result = hoaDonSAdmService.findHoaDonById(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/updateKH/{id}")
    public ResponseEntity<?> updateHDWithKH(@PathVariable Long id, @RequestBody HoaDonSAdmDto hoaDonSAdmDto) {
        if (hoaDonSAdmDto == null || hoaDonSAdmDto.getKhachHangId() == null) {
            return ResponseEntity.badRequest().body("Thiếu thông tin khách hàng");
        }

        HoaDonSAdm hoaDon = hoaDonSAdmRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy hoá đơn"));

        NguoiDungSAdm khachHang = nguoiDungSAdmRepo.findById(hoaDonSAdmDto.getKhachHangId()).orElse(null);

        hoaDon.setKhachHang(khachHang);
        hoaDon.setNguoiDatHang(khachHang.getHoTen());
        hoaDon.setNguoiNhanHang(khachHang.getHoTen());

        // Gán địa chỉ nhận hàng theo hình thức
        if (hoaDonSAdmDto.getHinhThucNhanHang() != null && hoaDonSAdmDto.getHinhThucNhanHang() == 0) {
            hoaDon.setDiaChiNhanHang("Tại cửa hàng");
        } else {
            hoaDon.setDiaChiNhanHang(khachHang.getDiaChi());
        }

        hoaDonSAdmRepo.save(hoaDon);
        return ResponseEntity.ok("Cập nhật khách hàng thành công");
    }

    @PutMapping("/updateHD/{id}")
    public ResponseEntity<?> updateHDWithNull(@PathVariable Long id, @RequestBody HoaDonSAdmDto hoaDonSAdmDto) {
        if (hoaDonSAdmDto == null || hoaDonSAdmDto.getPtThanhToanId() == null) {
            return ResponseEntity.badRequest().body("Thiếu thông tin thanh toán");
        }
        HoaDonSAdm hoaDon = hoaDonSAdmRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy hoá đơn"));

        PtThanhToanSAdm pttt = ptThanhToanSAdmRepo.findById(hoaDonSAdmDto.getPtThanhToanId()).orElse(null);

        hoaDon.setNguoiXuat(nguoiDungSAdmRepo.findById(1L).orElse(null));
        hoaDon.setThanhToan(pttt);
        hoaDon.setTongSoLuongSp(hoaDonSAdmDto.getTongSoLuongSp());
        hoaDon.setTongTien(hoaDonSAdmDto.getTongTien());
        hoaDon.setTrangThai(3);
        hoaDon.setMoTa(hoaDonSAdmDto.getMoTa());
        // Gán địa chỉ nhận hàng theo hình thức
        if (hoaDonSAdmDto.getHinhThucNhanHang() != null && hoaDonSAdmDto.getHinhThucNhanHang() == 0) {
            hoaDon.setTienThue(BigDecimal.ZERO);
        } else {
            hoaDon.setTienThue(BigDecimal.valueOf(51));
        }

        hoaDonSAdmRepo.save(hoaDon);
        return ResponseEntity.ok("Cập nhật khách hàng thành công");
    }

    @GetMapping("/theo-ngay")
    public List<HoaDonSAdm> getHoaDonTheoNgayVaTrangThai(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        return hoaDonSAdmService.findByDayAndTrangThai(start, end);
    }

    @GetMapping("/theo-ngayt")
    public List<HoaDonSAdm> getHoaDonTheoNgay(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        return hoaDonSAdmService.findByDay(start, end);
    }

    @GetMapping("/theo-thang")
    public List<HoaDonSAdm> getHoaDonTheoThangVaTrangThai(
            @RequestParam("months") int months
    ) {
        LocalDateTime fromDate = LocalDateTime.now().minusMonths(months);
        return hoaDonSAdmService.findByMonthsAndTrangThai(fromDate);
    }


    @PutMapping("/delete/{id}")
    public ResponseEntity<?> deleteHoaDonSAdm(@PathVariable Long id) {
        int rowsAffected = hoaDonSAdmService.deleteHoaDon(id);

        if (rowsAffected > 0) {
            return ResponseEntity.ok("Xóa hóa đơn thành công");
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy hóa đơn với id = " + id + " hoặc trạng thái khác 6");
        }
    }

}
