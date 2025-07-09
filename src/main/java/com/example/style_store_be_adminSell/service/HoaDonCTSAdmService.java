package com.example.style_store_be_adminSell.service;

import com.example.style_store_be_adminSell.dto.HoaDonCTSAdmDto;
import com.example.style_store_be_adminSell.dto.HoaDonSAdmDto;
import com.example.style_store_be_adminSell.entity.HoaDonCTSAdm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HoaDonCTSAdmService {

    Page<HoaDonCTSAdmDto> getAllHoaDonCTS(Pageable pageable);
    HoaDonCTSAdmDto addHDCTS(HoaDonCTSAdmDto hoaDonCTSAdm);
    Page<HoaDonCTSAdmDto> findByIdHoaDon(Long idHoaDon, Pageable pageable);
    HoaDonCTSAdmDto updateHDCTS(Long id,HoaDonCTSAdmDto hoaDonCTSAdm);
    void deleteHDCTS(Long id);
}
