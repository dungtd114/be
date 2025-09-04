package com.example.style_store_be.service;

import com.example.style_store_be.dto.SanPhamWebDto;
import com.example.style_store_be.dto.request.HoaDonUpdateRequest;
import com.example.style_store_be.dto.request.SanPhamAdminCrRequest;
import com.example.style_store_be.dto.request.SanPhamAdminUpdateReq;
import com.example.style_store_be.dto.response.SanPhamAdminResponse;
import com.example.style_store_be.dto.response.SanPhamWebResponse;
import com.example.style_store_be.entity.*;
import com.example.style_store_be.exception.AppException;
import com.example.style_store_be.exception.Errorcode;
import com.example.style_store_be.mapper.SanPhamCtAdmiMapper;
import com.example.style_store_be.repository.SanPhamWebRepo;
import com.example.style_store_be.repository.website.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SanPhamWebService {
    private final SanPhamWebRepo sanPhamWebRepo;
    private final ThuongHieuWebRepo thuongHieuWebRepo;
    private final KichKoWebRepo kichKoWebRepo;
    private final MauSacWebRepo mauSacWebRepo;
    private final ChatLieuWebRepo chatLieuWebRepo;
    private final TTSanPhamWebRepo ttSanPhamWebRepo;
    private final XuatXuRepository xuatXuRepository;
    private final HinhAnhSpRepo hinhAnhSpRepo;
    private final SanPhamCtAdmiMapper sanPhamCtAdmiMapper;
    public SanPhamWebService(SanPhamWebRepo sanPhamWebRepo, ThuongHieuWebRepo thuongHieuWebRepo, KichKoWebRepo kichKoWebRepo, MauSacWebRepo mauSacWebRepo, ChatLieuWebRepo chatLieuWebRepo, TTSanPhamWebRepo ttSanPhamWebRepo, XuatXuRepository xuatXuRepository, HinhAnhSpRepo hinhAnhSpRepo, SanPhamCtAdmiMapper sanPhamCtAdmiMapper) {
        this.sanPhamWebRepo = sanPhamWebRepo;
        this.thuongHieuWebRepo = thuongHieuWebRepo;
        this.kichKoWebRepo = kichKoWebRepo;
        this.mauSacWebRepo = mauSacWebRepo;
        this.chatLieuWebRepo = chatLieuWebRepo;
        this.ttSanPhamWebRepo = ttSanPhamWebRepo;
        this.xuatXuRepository = xuatXuRepository;
        this.hinhAnhSpRepo = hinhAnhSpRepo;
        this.sanPhamCtAdmiMapper = sanPhamCtAdmiMapper;
    }

 //   @PreAuthorize("hasAuthority('VIEW_PRODUCT')")
    public Page<SanPhamWebDto> getPageChiTietSanPham(
            String tenSanPham, Long thuongHieuId, Long mauSacId, Long chatLieuId, Long kichThuocId,
            Double minPrice, Double maxPrice,Long sanPhamId, Pageable pageable) {
        return sanPhamWebRepo.findByFilters(tenSanPham, thuongHieuId, mauSacId, chatLieuId, kichThuocId, minPrice, maxPrice,sanPhamId, pageable);
    }

    public List<ThuongHieu> getListThuongHieu() {
        return thuongHieuWebRepo.findByTrangThai(1, Sort.by("ngayTao").descending());
    }

    public List<MauSacSp> getListMauSac() {
        return mauSacWebRepo.findByTrangThai(1, Sort.by("ngayTao").descending());
    }

    public List<KichThuoc> getListKichThuoc() {
        return kichKoWebRepo.findByTrangThai(1, Sort.by("ngayTao").descending());
    }

    public List<ChatLieu> getListChatLieu() {
        return chatLieuWebRepo.findByTrangThai(1, Sort.by("ngayTao").descending());
    }


    public SanPhamWebResponse detailSanPhamCt(Long id) {
        ChiTietSanPham chiTietSanPham = sanPhamWebRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Chi tiết sản phẩm không tồn tại với id: " + id));

        return SanPhamWebResponse.builder()
                .id(chiTietSanPham.getId())
                .tenSanPham(chiTietSanPham.getSanPham() != null ? chiTietSanPham.getSanPham().getTen() : null)
                .tenMauSac(chiTietSanPham.getMauSac() != null ? chiTietSanPham.getMauSac().getTen() : null)
                .maMauSac(chiTietSanPham.getMauSac() != null ? chiTietSanPham.getMauSac().getMa() : null)
                .tenThuongHieu(chiTietSanPham.getThuongHieu() != null ? chiTietSanPham.getThuongHieu().getTen() : null)
                .tenKichThuoc(chiTietSanPham.getKichThuoc() != null ? chiTietSanPham.getKichThuoc().getTen(): null)
                .tenXuatXu(chiTietSanPham.getXuatXu() != null ? chiTietSanPham.getXuatXu().getTen() : null)
                .tenChatLieu(chiTietSanPham.getChatLieu() != null ? chiTietSanPham.getChatLieu().getTen(): null)
                .hinhAnhSp(chiTietSanPham.getHinhAnhSp() != null ? chiTietSanPham.getHinhAnhSp().getHinhAnh() : null)
                .ma(chiTietSanPham.getMa())
                .giaNhap(chiTietSanPham.getGiaNhap())
                .giaBan(chiTietSanPham.getGiaBan())
                .soLuong(chiTietSanPham.getSoLuong())
                .trangThai(chiTietSanPham.getTrangThai())
                .moTa(chiTietSanPham.getMoTa())
                .giaBanGoc(chiTietSanPham.getGiaBanGoc())
                .build();
    }

    public List<SanPham> getListSanPham() {
        return ttSanPhamWebRepo.findByTrangThai(1,Sort.by("ngayTao").descending());
    }


    public Page<SanPham> getPageSanPham(Pageable pageable) {
        return ttSanPhamWebRepo.findAll(pageable);
    }

    public List<SanPhamWebDto> getListChiTietSanPham(
            String tenSanPham,
            Long thuongHieuId,
            Long mauSacId,
            Long chatLieuId,
            Long kichThuocId,
            Double minPrice,
            Double maxPrice,
            Sort sort,
            Long sanPhamId
    ) {
        return sanPhamWebRepo.findByFiltersNoPaging(tenSanPham, thuongHieuId, mauSacId,
                chatLieuId, kichThuocId, minPrice, maxPrice,
                sanPhamId, sort);
    }



    public Page<SanPhamWebDto> getPageChiTietSanPhamBySanPhamIdAndFilters(
            String tenSanPham,
            Long thuongHieuId,
            Long mauSacId,
            Long chatLieuId,
            Long kichThuocId,
            Double minPrice,
            Double maxPrice,
            Long sanPhamId, // bắt buộc
            Pageable pageable
    ) {
        return sanPhamWebRepo.findByFilterAdmin(
                tenSanPham,
                thuongHieuId,
                mauSacId,
                chatLieuId,
                kichThuocId,
                minPrice,
                maxPrice,
                sanPhamId,
                pageable
        );
    }

    public List<XuatXu> getListXuatXu() {
        return xuatXuRepository.findByTrangThai(1,Sort.by("ngayTao").descending());

    }

    public List<HinhAnh> getListHinhAnh() {
        return hinhAnhSpRepo.findByTrangThai(1,Sort.by("ngayTao").descending());

    }

    public ChiTietSanPham addSanPhamChiTiet(SanPhamAdminCrRequest request) {
        Optional<ChiTietSanPham> existingProduct = sanPhamWebRepo.findByAttributes(
                request.getSanPhamId(),
                request.getMauSacId(),
                request.getThuongHieuId(),
                request.getKichThuocId(),
                request.getXuatXuId(),
                request.getChatLieuId()
        );

        ChiTietSanPham chiTietSanPham;

        if (existingProduct.isPresent()) {
            // Nếu đã tồn tại, sử dụng sản phẩm đã có và cập nhật số lượng, giá cả
            chiTietSanPham = existingProduct.get();
            chiTietSanPham.setSoLuong(chiTietSanPham.getSoLuong() + request.getSoLuong());
            chiTietSanPham.setGiaNhap(request.getGiaNhap());
            chiTietSanPham.setGiaBan(request.getGiaBan());
            chiTietSanPham.setGiaBanGoc(request.getGiaBan()); // Giữ giá gốc
            chiTietSanPham.setMoTa(request.getMoTa());
            chiTietSanPham.setNgaySua(new Date());
        } else {
            chiTietSanPham = sanPhamCtAdmiMapper.toChiTietSanPham(request);
            chiTietSanPham.setMa("CTSP-" + UUID.randomUUID().toString().substring(0, 8));
            chiTietSanPham.setNgayTao(new Date());
            chiTietSanPham.setTrangThai(1);
            if (chiTietSanPham.getHinhAnhSp() == null || chiTietSanPham.getHinhAnhSp().toString().isEmpty()){
                HinhAnh hinhAnh = hinhAnhSpRepo.findById(1L)
                        .orElseThrow(() -> new RuntimeException("Hình ảnh mặc định không tồn tại"));
                chiTietSanPham.setHinhAnhSp(hinhAnh);
            }
            chiTietSanPham.setGiaBanGoc(request.getGiaBan());
        }

        return sanPhamWebRepo.save(chiTietSanPham);
    }

    public SanPhamAdminResponse detailSanPhamCtAdmin(Long id) {
        ChiTietSanPham chiTietSanPham = sanPhamWebRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Chi tiết sản phẩm không tồn tại với id: " + id));

        return SanPhamAdminResponse.builder()
                .id(chiTietSanPham.getId())
                .idSanPham(chiTietSanPham.getSanPham() != null ? chiTietSanPham.getSanPham().getId() : null)
                .idMauSac(chiTietSanPham.getMauSac() != null ? chiTietSanPham.getMauSac().getId() : null)
                .maMauSac(chiTietSanPham.getMauSac() != null ? chiTietSanPham.getMauSac().getMa() : null)
                .idThuongHieu(chiTietSanPham.getThuongHieu() != null ? chiTietSanPham.getThuongHieu().getId() : null)
                .idKichThuoc(chiTietSanPham.getKichThuoc() != null ? chiTietSanPham.getKichThuoc().getId(): null)
                .idXuatXu(chiTietSanPham.getXuatXu() != null ? chiTietSanPham.getXuatXu().getId(): null)
                .idChatLieu(chiTietSanPham.getChatLieu() != null ? chiTietSanPham.getChatLieu().getId(): null)
                .idHinhAnhSp(chiTietSanPham.getHinhAnhSp() != null ? chiTietSanPham.getHinhAnhSp().getId() : null)
                .ma(chiTietSanPham.getMa())
                .giaNhap(chiTietSanPham.getGiaNhap())
                .giaBan(chiTietSanPham.getGiaBan())
                .soLuong(chiTietSanPham.getSoLuong())
                .trangThai(chiTietSanPham.getTrangThai())
                .moTa(chiTietSanPham.getMoTa())
                .giaBanGoc(chiTietSanPham.getGiaBanGoc())
                .build();
    }

    public ChiTietSanPham updateSanPhamCTAdmin(Long id, SanPhamAdminUpdateReq request) {

        if (request.getGiaNhap() >= request.getGiaBan()) {
            throw new AppException(Errorcode.INVALID_MIN_MAX_PRICE);
        }
        // Lấy sản phẩm cần cập nhật
        ChiTietSanPham currentProduct = sanPhamWebRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        // Kiểm tra xem có sản phẩm khác có cùng thuộc tính không (trừ id hiện tại)
        Optional<ChiTietSanPham> duplicateProduct = sanPhamWebRepo.findByAttributesExcludeId(
                currentProduct.getSanPham().getId(),
                request.getIdMauSac(),
                request.getIdThuongHieu(),
                request.getIdKichThuoc(),
                request.getIdXuatXu(),
                request.getIdChatLieu(),
                id // exclude current product id
        );

        if (duplicateProduct.isPresent()) {
            ChiTietSanPham existingProduct = duplicateProduct.get();

            // Cộng dồn số lượng
            existingProduct.setSoLuong(existingProduct.getSoLuong() + request.getSoLuong());

            // Cập nhật thông tin khác
            existingProduct.setGiaNhap(request.getGiaNhap());
            existingProduct.setGiaBan(request.getGiaBan());
            existingProduct.setGiaBanGoc(request.getGiaBan());
            existingProduct.setMoTa(request.getMoTa());
            existingProduct.setNgaySua(new Date());


            // Lưu sản phẩm trùng
            sanPhamWebRepo.save(existingProduct);

            // Xóa sản phẩm hiện tại (vì đã gộp vào sản phẩm trùng)
            sanPhamWebRepo.delete(currentProduct);

            return currentProduct;
        } else {
            // Nếu không có sản phẩm trùng, cập nhật bình thường
            sanPhamCtAdmiMapper.sanPhamAdminUpdateRequest(currentProduct, request);
            currentProduct.setNgaySua(new Date());
            currentProduct.setGiaBanGoc(request.getGiaBan());
            sanPhamWebRepo.save(currentProduct);
            return currentProduct;
        }
    }


    public Page<SanPhamWebDto> getPageChiTietSanPhamBySanPhamIdAndFilters2(
            String tenSanPham,
            Long thuongHieuId,
            Long mauSacId,
            Long chatLieuId,
            Long kichThuocId,
            Long xuatXuId, // ✅ Thêm tham số xuất xứ
            Double minPrice,
            Double maxPrice,
            Long sanPhamId,
            Pageable pageable
    ) {
        return sanPhamWebRepo.findByFilterAdmin2(
                tenSanPham,
                thuongHieuId,
                mauSacId,
                chatLieuId,
                kichThuocId,
                xuatXuId, // ✅ truyền vào
                minPrice,
                maxPrice,
                sanPhamId,
                pageable
        );
    }

    public void chuyenTrangThaiSPCT(Long id) {
        Optional<ChiTietSanPham> optionalSPCT = sanPhamWebRepo.findById(id);

        if (optionalSPCT.isPresent()) {
            ChiTietSanPham spct = optionalSPCT.get();

            int trangThaiHienTai = spct.getTrangThai();
            int trangThaiMoi = (trangThaiHienTai == 1) ? 0 : 1;

            spct.setTrangThai(trangThaiMoi);
            sanPhamWebRepo.save(spct);
        } else {
            throw new RuntimeException("Không tìm thấy sản phẩm chi tiết với ID: " + id);
        }
    }

    public void chuyenTrangThaiSP(Long id) {
        Optional<SanPham> optionalSPCT = ttSanPhamWebRepo.findById(id);

        if (optionalSPCT.isPresent()) {
            SanPham sp = optionalSPCT.get();

            int trangThaiHienTai = sp.getTrangThai();
            int trangThaiMoi = (trangThaiHienTai == 1) ? 0 : 1;

            sp.setTrangThai(trangThaiMoi);
            ttSanPhamWebRepo.save(sp);
        } else {
            throw new RuntimeException("Không tìm thấy sản phẩm chi tiết với ID: " + id);
        }
    }

    public MauSacSp getMauSacById() {
        return mauSacWebRepo.findById(1L)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy màu sắc!"));
    }

    public HinhAnh saveHinhAnh(HinhAnh hinhAnh) {
        return hinhAnhSpRepo.save(hinhAnh);
    }

}
