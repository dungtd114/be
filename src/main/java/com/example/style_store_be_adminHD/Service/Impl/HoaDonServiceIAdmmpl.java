//package com.example.style_store_be_adminHD.Service.Impl;
//
//import com.example.style_store_be_adminHD.Dto.HoaDonAdmDto;
//import com.example.style_store_be_adminHD.Dto.HoaDonAdmDtoConverter;
//import com.example.style_store_be_adminHD.Entity.HoaDonAdm;
//import com.example.style_store_be_adminHD.Repository.HoaDonAdmRepository;
//import com.example.style_store_be_adminHD.Service.HoaDonAdmService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class HoaDonServiceIAdmmpl implements HoaDonAdmService {
//    private final HoaDonAdmRepository hoaDonRepository;
//    private final HoaDonAdmDtoConverter hoaDonAdmDtoConverter;
//    // DTO-based methods
//    @Override
//    public List<HoaDonAdmDto> getAllDto() {
//        return hoaDonRepository.findAll().stream().map(hoaDonAdmDtoConverter::toDto).toList();
//    }
//
//    @Override
//    public Optional<HoaDonAdmDto> getByIdDto(Long id) {
//        return hoaDonRepository.findById(id).map(hoaDonAdmDtoConverter::toDto);
//    }
//
//    @Override
//    public Page<HoaDonAdmDto> searchDto(String ma, Pageable pageable) {
//        return hoaDonRepository.findByMaContaining(ma, pageable)
//                .map(hoaDonAdmDtoConverter::toDto);
//    }
//}
package com.example.style_store_be_adminHD.Service.Impl;

import com.example.style_store_be_adminHD.Dto.HoaDonAdmDto;
import com.example.style_store_be_adminHD.Dto.HoaDonAdmDtoConverter;
import com.example.style_store_be_adminHD.Entity.HoaDonAdm;
import com.example.style_store_be_adminHD.Repository.HoaDonAdmRepository;
import com.example.style_store_be_adminHD.Service.HoaDonAdmService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HoaDonServiceIAdmmpl implements HoaDonAdmService {
    private final HoaDonAdmRepository hoaDonRepository;
    private final HoaDonAdmDtoConverter hoaDonAdmDtoConverter;

    // Lấy tất cả hóa đơn với phân trang
    @Override
    public Page<HoaDonAdmDto> getAllDto(Pageable pageable) {
        return hoaDonRepository.findAll(pageable) // Sử dụng findAll với Pageable
                .map(hoaDonAdmDtoConverter::toDto); // Chuyển đổi sang DTO
    }

    // Lấy hóa đơn theo ID
    @Override
    public Optional<HoaDonAdmDto> getByIdDto(Long id) {
        return hoaDonRepository.findById(id).map(hoaDonAdmDtoConverter::toDto);
    }

    // Tìm kiếm hóa đơn theo mã với phân trang
    @Override
    public Page<HoaDonAdmDto> searchDto(String ma, Pageable pageable) {
        return hoaDonRepository.findByMaContaining(ma, pageable)
                .map(hoaDonAdmDtoConverter::toDto);
    }
}