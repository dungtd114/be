package com.example.style_store_be.mapper;

import com.example.style_store_be.dto.request.DonHangChiTietRequest;
import com.example.style_store_be.entity.ChiTietSanPham;
import com.example.style_store_be.entity.HoaDonCt;
import com.example.style_store_be.repository.SanPhamWebRepo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class DonHangChiTietMapper {

    @Autowired
    private SanPhamWebRepo sanPhamWebRepo;

    @Mapping(target = "hoaDon", ignore = true)
    @Mapping(target = "sanPhamCt", expression = "java(findChiTietSanPhamById(request.getSanPhamctId()))")
    public abstract HoaDonCt toDonHangCt(DonHangChiTietRequest request);

    protected ChiTietSanPham findChiTietSanPhamById(Long idSanPhamCt) {
        return sanPhamWebRepo.findById(idSanPhamCt)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy chi tiết sản phẩm với ID: " + idSanPhamCt));
    }
}
