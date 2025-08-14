package com.example.style_store_be_adminSell.service;

import com.example.style_store_be_adminSP.dto.SanPhamCtDTOAdm;
import com.example.style_store_be_adminSell.dto.HoaDonSAdmDto;
import com.example.style_store_be_adminSell.entity.HoaDonSAdm;
import com.example.style_store_be_adminSell.entity.NguoiDungSAdm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDateTime;
import java.util.List;

public interface HoaDonSAdmService {

    Page<HoaDonSAdmDto> findAll(Pageable pageable);

    Page<HoaDonSAdmDto> findByTrangThai(Integer trangThai, Pageable pageable);

    HoaDonSAdm findOne(String ma);

    HoaDonSAdmDto addHoaDon(HoaDonSAdmDto hoaDon);

    HoaDonSAdm findHoaDonById(Long id);

    int deleteHoaDon(Long id);

    List<HoaDonSAdm> findByMonthsAndTrangThai(LocalDateTime fromDate);

    List<HoaDonSAdm> findByDayAndTrangThai(LocalDateTime startOfDay,LocalDateTime endOfDay);

    List<HoaDonSAdm> findByDay(LocalDateTime startOfDay,LocalDateTime endOfDay);

    String updateKhachHangChoHoaDon (Long id,HoaDonSAdmDto hoaDonSAdmDto);
    String capNhatThanhToanVaThongTinHoaDon(Long hoaDonId, HoaDonSAdmDto hoaDonSAdmDto);
}
