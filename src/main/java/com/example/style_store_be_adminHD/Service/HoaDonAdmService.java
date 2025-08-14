package com.example.style_store_be_adminHD.Service;

import com.example.style_store_be_adminHD.Dto.HoaDonAdmDto;
import com.example.style_store_be_adminHD.Dto.HoaDonStatusUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface HoaDonAdmService {
//    List<HoaDonAdmDto> getAllDto();
//    Optional<HoaDonAdmDto> getByIdDto(Long id);
//    Page<HoaDonAdmDto> searchDto(String ma, Pageable pageable);
  Page<HoaDonAdmDto> getAllDto(Pageable pageable);
    Optional<HoaDonAdmDto> getByIdDto(Long id);
    Page<HoaDonAdmDto> searchDto(String ma, Pageable pageable);

  Optional<HoaDonAdmDto> updateTrangThai(Long id, HoaDonStatusUpdateDto statusUpdateDto);

}
