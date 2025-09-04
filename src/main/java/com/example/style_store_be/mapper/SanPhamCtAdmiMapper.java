package com.example.style_store_be.mapper;

import com.example.style_store_be.dto.request.GiamGiaUpdateRequest;
import com.example.style_store_be.dto.request.HoaDonChiTietRequest;
import com.example.style_store_be.dto.request.SanPhamAdminCrRequest;
import com.example.style_store_be.dto.request.SanPhamAdminUpdateReq;
import com.example.style_store_be.dto.response.SanPhamAdminResponse;
import com.example.style_store_be.dto.response.UserResponse;
import com.example.style_store_be.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SanPhamCtAdmiMapper {

    @Mapping(target = "sanPham", expression = "java(mapSanPham(request.getSanPhamId()))")
    @Mapping(target = "mauSac", expression = "java(mapMauSacSp(request.getMauSacId()))")
    @Mapping(target = "thuongHieu", expression = "java(mapThuongHieu(request.getThuongHieuId()))")
    @Mapping(target = "kichThuoc", expression = "java(mapKichThuoc(request.getKichThuocId()))")
    @Mapping(target = "xuatXu", expression = "java(mapXuatXu(request.getXuatXuId()))")
    @Mapping(target = "chatLieu", expression = "java(mapChatLieu(request.getChatLieuId()))")
    @Mapping(target = "hinhAnhSp", expression = "java(mapHinhAnh(request.getHinhAnhSpId()))")

    ChiTietSanPham toChiTietSanPham(SanPhamAdminCrRequest request);
        default SanPham mapSanPham(Long sanPhamId) {
        if (sanPhamId == null) return null;
        SanPham sanPham = new SanPham();
        sanPham.setId(sanPhamId);
        return sanPham;
    }

    default MauSacSp mapMauSacSp(Long mauSacId) {
        if (mauSacId == null) return null;
        MauSacSp mauSacSp = new MauSacSp();
        mauSacSp.setId(mauSacId);
        return mauSacSp;
    }
    default ThuongHieu mapThuongHieu(Long thuongHieuId) {
        if (thuongHieuId == null) return null;
        ThuongHieu thuongHieu = new ThuongHieu();
        thuongHieu.setId(thuongHieuId);
        return thuongHieu;
    }
    default KichThuoc mapKichThuoc(Long kichThuocId) {
        if (kichThuocId == null) return null;
        KichThuoc kichThuoc = new KichThuoc();
        kichThuoc.setId(kichThuocId);
        return kichThuoc;
    }
    default XuatXu mapXuatXu(Long xuatXuId) {
        if (xuatXuId == null) return null;
        XuatXu xuatXu = new XuatXu();
        xuatXu.setId(xuatXuId);
        return xuatXu;
    }
    default HinhAnh mapHinhAnh(Long hinhAnhId) {
        if (hinhAnhId == null) return null;
        HinhAnh hinhAnh = new HinhAnh();
        hinhAnh.setId(hinhAnhId);
        return hinhAnh;
    }
    default ChatLieu mapChatLieu(Long chatLieuId) {
        if (chatLieuId == null) return null;
        ChatLieu chatLieu = new ChatLieu();
        chatLieu.setId(chatLieuId);
        return chatLieu;
    }
    SanPhamAdminResponse toSanPhamAdminResponse (ChiTietSanPham chiTietSanPham);



    @Mapping(target = "sanPham", expression = "java(mapSanPham2(request.getIdSanPham()))")
    @Mapping(target = "mauSac", expression = "java(mapMauSacSp2(request.getIdMauSac()))")
    @Mapping(target = "thuongHieu", expression = "java(mapThuongHieu2(request.getIdThuongHieu()))")
    @Mapping(target = "kichThuoc", expression = "java(mapKichThuoc2(request.getIdKichThuoc()))")
    @Mapping(target = "xuatXu", expression = "java(mapXuatXu2(request.getIdXuatXu()))")
    @Mapping(target = "chatLieu", expression = "java(mapChatLieu2(request.getIdChatLieu()))")
    @Mapping(target = "hinhAnhSp", expression = "java(mapHinhAnh2(request.getIdHinhAnhSp()))")

    @Mapping(target = "ma", ignore = true)
    void sanPhamAdminUpdateRequest(@MappingTarget ChiTietSanPham chiTietSanPham, SanPhamAdminUpdateReq request);

    // Các default methods cho mapping ID -> Entity
    default SanPham mapSanPham2(Long sanPhamId) {
        if (sanPhamId == null) return null;
        SanPham sanPham = new SanPham();
        sanPham.setId(sanPhamId);
        return sanPham;
    }

    default MauSacSp mapMauSacSp2(Long mauSacId) {
        if (mauSacId == null) return null;
        MauSacSp mauSacSp = new MauSacSp();
        mauSacSp.setId(mauSacId);
        return mauSacSp;
    }

    default ThuongHieu mapThuongHieu2(Long thuongHieuId) {
        if (thuongHieuId == null) return null;
        ThuongHieu thuongHieu = new ThuongHieu();
        thuongHieu.setId(thuongHieuId);
        return thuongHieu;
    }

    default KichThuoc mapKichThuoc2(Long kichThuocId) {
        if (kichThuocId == null) return null;
        KichThuoc kichThuoc = new KichThuoc();
        kichThuoc.setId(kichThuocId);
        return kichThuoc;
    }

    default XuatXu mapXuatXu2(Long xuatXuId) {
        if (xuatXuId == null) return null;
        XuatXu xuatXu = new XuatXu();
        xuatXu.setId(xuatXuId);
        return xuatXu;
    }

    default ChatLieu mapChatLieu2(Long chatLieuId) {
        if (chatLieuId == null) return null;
        ChatLieu chatLieu = new ChatLieu();
        chatLieu.setId(chatLieuId);
        return chatLieu;
    }

    default HinhAnh mapHinhAnh2(Long hinhAnhId) {
        if (hinhAnhId == null) return null;
        HinhAnh hinhAnh = new HinhAnh();
        hinhAnh.setId(hinhAnhId);
        return hinhAnh;
    }
}



