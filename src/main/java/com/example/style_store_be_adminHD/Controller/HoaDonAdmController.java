//package com.example.style_store_be_adminHD.Controller;
//
//import com.example.style_store_be_adminHD.Dto.HoaDonAdmDto;
//import com.example.style_store_be_adminHD.Entity.HoaDonAdm;
//import com.example.style_store_be_adminHD.Service.HoaDonAdmService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/hoa-don")
//@RequiredArgsConstructor
//@CrossOrigin("*")
//public class HoaDonAdmController {
//    private final HoaDonAdmService hoaDonService;
//@GetMapping
//public ResponseEntity<List<HoaDonAdmDto>> getAllDto() {
//    return ResponseEntity.ok(hoaDonService.getAllDto());
//}
//
//    @GetMapping("/{id}")
//    public ResponseEntity<HoaDonAdmDto> getByIdDto(@PathVariable Long id) {
//        return hoaDonService.getByIdDto(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/search")
//    public ResponseEntity<Page<HoaDonAdmDto>> searchDto(
//            @RequestParam(defaultValue = "") String ma,
//            @RequestParam(defaultValue = "0") int page
//    ) {
//        Pageable pageable = PageRequest.of(page, 5);
//        return ResponseEntity.ok(hoaDonService.searchDto(ma, pageable));
//    }
//}
package com.example.style_store_be_adminHD.Controller;

import com.example.style_store_be_adminHD.Dto.HoaDonStatusUpdateDto; // Import DTO mới
import com.example.style_store_be_adminHD.Dto.HoaDonAdmDto;
import com.example.style_store_be_adminHD.Service.HoaDonAdmService;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.PutMapping; // Import PutMapping
import org.springframework.web.bind.annotation.RequestBody; // Import RequestBody
import org.springframework.http.HttpStatus; // Import HttpStatus

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hoa-don")
@RequiredArgsConstructor
@CrossOrigin("*")
public class HoaDonAdmController {
    private final HoaDonAdmService hoaDonService;

    // Lấy danh sách tất cả hóa đơn với phân trang
    @GetMapping
    public ResponseEntity<Page<HoaDonAdmDto>> getAllDto(
            @RequestParam(defaultValue = "0") int page, // Số trang, mặc định là 0
            @RequestParam(defaultValue = "10") int size  // Số bản ghi mỗi trang, mặc định là 5
    ) {
        Pageable pageable = PageRequest.of(page, size); // Tạo đối tượng Pageable
        return ResponseEntity.ok(hoaDonService.getAllDto(pageable)); // Trả về danh sách phân trang
    }

    // Lấy hóa đơn theo ID
    @GetMapping("/{id}")
    public ResponseEntity<HoaDonAdmDto> getByIdDto(@PathVariable Long id) {
        return hoaDonService.getByIdDto(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()); // Trả về 404 nếu không tìm thấy
    }

    // Tìm kiếm hóa đơn theo mã với phân trang
    @GetMapping("/search")
    public ResponseEntity<Page<HoaDonAdmDto>> searchDto(
            @RequestParam(defaultValue = "") String ma,
            @RequestParam(defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, 10); // Số bản ghi mỗi trang cố định là 5
        return ResponseEntity.ok(hoaDonService.searchDto(ma, pageable));
    }

    // Endpoint mới: Cập nhật trạng thái hóa đơn
    // Sử dụng @PutMapping cho thao tác cập nhật toàn bộ tài nguyên hoặc một phần lớn
    @PutMapping("/{id}/status")
    public ResponseEntity<HoaDonAdmDto> updateHoaDonStatus(
            @PathVariable Long id, // ID của hóa đơn cần cập nhật
            @RequestBody HoaDonStatusUpdateDto statusUpdateDto // Dữ liệu trạng thái mới từ request body
    ) {
        // Gọi service để cập nhật trạng thái
        Optional<HoaDonAdmDto> updatedHoaDon = hoaDonService.updateTrangThai(id, statusUpdateDto);

        // Kiểm tra kết quả cập nhật
        if (updatedHoaDon.isPresent()) {
            return ResponseEntity.ok(updatedHoaDon.get()); // Trả về hóa đơn đã cập nhật (200 OK)
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Trả về 404 Not Found nếu không tìm thấy hóa đơn
        }
    }

}