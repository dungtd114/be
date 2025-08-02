package com.example.style_store_be_adminThongKe.Controller;

import com.example.style_store_be_adminThongKe.DTO.SanPhamBanChayDTO;
import com.example.style_store_be_adminThongKe.service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/thong-ke")
@CrossOrigin("*")
public class ThongKeController {

    @Autowired
    private ThongKeService thongKeService;

    // Thống kê theo tháng
    @GetMapping("/san-pham/thang")
    public List<SanPhamBanChayDTO> thongKeTheoThang(
            @RequestParam int thang,
            @RequestParam int nam) {
        return thongKeService.laySanPhamBanChayTheoThang(thang, nam);
    }

    // Thống kê theo năm
    @GetMapping("/san-pham/nam")
    public List<SanPhamBanChayDTO> thongKeTheoNam(
            @RequestParam int nam) {
        return thongKeService.laySanPhamBanChayTheoNam(nam);
    }

    // Thống kê theo tuần
    @GetMapping("/san-pham/tuan")
    public List<SanPhamBanChayDTO> thongKeTheoTuan(
            @RequestParam int tuan,
            @RequestParam int nam) {
        return thongKeService.laySanPhamBanChayTheoTuan(tuan, nam);
    }
}