package com.example.style_store_be_adminSP.service;

import com.example.style_store_be_adminSP.entity.HinhAnhMauSacAdm;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HinhAnhMauSacServiceAdm {
    Page<HinhAnhMauSacAdm> getAll(int page, int size);
    Page<HinhAnhMauSacAdm> searchByHinhAnh(String hinhAnh, int page, int size);
    Page<HinhAnhMauSacAdm> getActive(int page, int size);
    Page<HinhAnhMauSacAdm> getDeleted(int page, int size);
    HinhAnhMauSacAdm getOne(Long id);
    void add(HinhAnhMauSacAdm hinhAnhMauSac);
    void update(HinhAnhMauSacAdm hinhAnhMauSac);
    void delete(Long id);
    List<HinhAnhMauSacAdm> getByMauSacId(Long mauSacId);

    String uploadImage(MultipartFile file, Long mauSacId);
}