package com.example.style_store_be.service.website;

import com.example.style_store_be.dto.GiamGiaDto;
import com.example.style_store_be.dto.HoaDonCtDto;
import com.example.style_store_be.dto.LichSuDonHangDto;
import com.example.style_store_be.dto.request.DonHangRequest;
import com.example.style_store_be.entity.*;
import com.example.style_store_be.mapper.DonHangChiTietMapper;
import com.example.style_store_be.mapper.DonHangMapper;
import com.example.style_store_be.repository.PhuongThucTTRepo;
import com.example.style_store_be.repository.SanPhamWebRepo;
import com.example.style_store_be.repository.website.DonHangChiTietRepo;
import com.example.style_store_be.repository.website.DonHangRepoSitory;
import com.example.style_store_be.repository.website.UserRepoSitory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DonHangService {
    DonHangRepoSitory donHangRepoSitory;
    DonHangMapper donHangMapper;
    DonHangChiTietMapper donHangChiTietMapper;
    DonHangChiTietRepo donHangChiTietRepo;
    UserRepoSitory userRepoSitory;
    PhuongThucTTRepo phuongThucTTRepo;
    JavaMailSender javaMailSender;
    SanPhamWebRepo sanPhamWebRepo;

    public HoaDon createrDonHang(DonHangRequest request) {
        HoaDon hoaDon = donHangMapper.toHoaDon(request);
        hoaDon.setNgayDat(new Date());
        hoaDon.setNgayTao(new Date());
        hoaDon.setTrangThai(0);
        hoaDon.setTrangThaiThanhToan(0);

        if (hoaDon.getMa() == null || hoaDon.getMa().isEmpty()) {
            hoaDon.setMa("HD" + UUID.randomUUID().toString().substring(0, 10));
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasValidToken = false;

        if (authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal())) {
            String email = authentication.getName();
            userRepoSitory.findByEmail(email).ifPresent(user -> {
                hoaDon.setNguoiDatHang(user.getHoTen());
                hoaDon.setKhachHang(user);
                hoaDon.setNguoiTao(user);
            });
            hasValidToken = true; // ✅ token hợp lệ
        } else {
            hoaDon.setNguoiDatHang(null);
            hoaDon.setKhachHang(null);
            hoaDon.setNguoiTao(null);
        }

        PtThanhToan ptThanhToan = phuongThucTTRepo.findById(1L)
                .orElseThrow(() -> new RuntimeException("Phương thức thanh toán không tồn tại"));
        hoaDon.setThanhToan(ptThanhToan);

        Double tongTienHoaDon = request.getTongTien() - request.getTienThue();
        hoaDon.setTongTien(tongTienHoaDon);

        HoaDon savedHoaDon = donHangRepoSitory.save(hoaDon);

        if (request.getChiTietDonHang() != null && !request.getChiTietDonHang().isEmpty()) {
            List<HoaDonCt> hoaDonCts = request.getChiTietDonHang().stream()
                    .map(chiTietRequest -> {
                        HoaDonCt hoaDonCt = donHangChiTietMapper.toDonHangCt(chiTietRequest);
                        hoaDonCt.setHoaDon(savedHoaDon);
                        return hoaDonCt;
                    })
                    .collect(Collectors.toList());
            donHangChiTietRepo.saveAll(hoaDonCts);
        }

        if (hasValidToken) {
            sendInvoiceEmail(savedHoaDon);
        }

        return savedHoaDon;
    }


    public HoaDon createrDonHangVNPay(DonHangRequest request, User user) {
        HoaDon hoaDon = donHangMapper.toHoaDon(request);
        hoaDon.setNgayDat(new Date());
        hoaDon.setNgayTao(new Date());
        hoaDon.setTrangThai(0); // Trạng thái đơn hàng
        hoaDon.setTrangThaiThanhToan(1); // Đã thanh toán (VNPay thành công)

        if (hoaDon.getMa() == null || hoaDon.getMa().isEmpty()) {
            hoaDon.setMa("HD" + UUID.randomUUID().toString().substring(0, 10));
        }
        boolean hasValidToken = false;
        // ✅ Nếu có user thì set, còn không thì bỏ qua
        if (user != null) {
            hoaDon.setKhachHang(user);
            hoaDon.setNguoiTao(user);
            hoaDon.setNguoiDatHang(user.getHoTen());
            hasValidToken = true; // ✅ token hợp lệ

        } else {
            hoaDon.setKhachHang(null);
            hoaDon.setNguoiTao(null);
            hoaDon.setNguoiDatHang(null);
        }

        PtThanhToan ptThanhToan = phuongThucTTRepo.findById(2L)
                .orElseThrow(() -> new RuntimeException("Phương thức thanh toán VNPay không tồn tại"));
        hoaDon.setThanhToan(ptThanhToan);

        Double tongTienHoaDon = request.getTongTien() - request.getTienThue();
        hoaDon.setTongTien(tongTienHoaDon);

        HoaDon savedHoaDon = donHangRepoSitory.save(hoaDon);

        if (request.getChiTietDonHang() != null && !request.getChiTietDonHang().isEmpty()) {
            List<HoaDonCt> hoaDonCts = request.getChiTietDonHang().stream()
                    .map(chiTietRequest -> {
                        HoaDonCt hoaDonCt = donHangChiTietMapper.toDonHangCt(chiTietRequest);
                        hoaDonCt.setHoaDon(savedHoaDon);

                        // ✅ Trừ số lượng sản phẩm
                        ChiTietSanPham sanPhamCt = sanPhamWebRepo.findById(chiTietRequest.getSanPhamctId())
                                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

                        int soLuongConLai = sanPhamCt.getSoLuong() - chiTietRequest.getSoLuong();
                        if (soLuongConLai < 0) {
                            throw new RuntimeException("Sản phẩm " + sanPhamCt.getMa() + " không đủ số lượng trong kho");
                        }
                        sanPhamCt.setSoLuong(soLuongConLai);
                        sanPhamWebRepo.save(sanPhamCt);

                        return hoaDonCt;
                    })
                    .collect(Collectors.toList());

            donHangChiTietRepo.saveAll(hoaDonCts);
        }


        if (hasValidToken){
        sendInvoiceEmail(savedHoaDon);}
        return savedHoaDon;
    }


    private void sendInvoiceEmail(HoaDon hoaDon) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject("Hóa đơn đặt hàng #" + hoaDon.getMa());
            helper.setText("Cảm ơn bạn đã đặt hàng. Vui lòng xem file đính kèm để xem chi tiết hóa đơn.");

            byte[] pdfBytes = generateInvoicePdf(hoaDon);
            helper.addAttachment("HoaDon_" + hoaDon.getMa() + ".pdf", new ByteArrayResource(pdfBytes));

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Lỗi khi gửi email: " + e.getMessage(), e);
        }
    }


    private byte[] generateInvoicePdf(HoaDon hoaDon) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Load font hỗ trợ tiếng Việt
            try (InputStream fontStream = getClass().getResourceAsStream("/fonts/DejaVuSans.ttf")) {
                if (fontStream == null) {
                    throw new RuntimeException("Không tìm thấy file font trong resources/fonts/DejaVuSans.ttf");
                }
                PdfFont font = PdfFontFactory.createFont(
                        fontStream.readAllBytes(),
                        PdfEncodings.IDENTITY_H
                );
                document.setFont(font);
            }

            // Thiết lập lề và kích thước trang
            pdfDoc.setDefaultPageSize(PageSize.A4);
            document.setMargins(50, 50, 50, 50);

            // Tiêu đề với màu sắc và logo (giả định logo là hình ảnh)
            Paragraph title = new Paragraph("HÓA ĐƠN ĐẶT HÀNG")
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.ORANGE) // Màu cam cho tiêu đề
                    .setMarginBottom(20);
            document.add(title);

            // Thông tin công ty (giả định)
            Paragraph companyInfo = new Paragraph("CÔNG TY TNHH SD-02\nĐịa chỉ: Cao Đẳng FPT ,Phố Trịnh Văn Bô,Quan Nam Tu Liem,Ha Noi\nSĐT: 0909 123 456\nEmail: hoa123@gmail.com")
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(companyInfo);

            // Thông tin đơn hàng
            Table infoTable = new Table(UnitValue.createPercentArray(new float[]{1, 2})).useAllAvailableWidth();
            infoTable.setMarginBottom(20);
            infoTable.addCell(new Cell().add(new Paragraph("Mã hóa đơn:").setBold()).setPadding(5));
            infoTable.addCell(new Cell().add(new Paragraph(hoaDon.getMa())).setPadding(5));
            infoTable.addCell(new Cell().add(new Paragraph("Khách hàng:").setBold()).setPadding(5));
            infoTable.addCell(new Cell().add(new Paragraph(hoaDon.getKhachHang().getHoTen())).setPadding(5));
            infoTable.addCell(new Cell().add(new Paragraph("Ngày đặt:").setBold()).setPadding(5));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            String ngayDatStr = hoaDon.getNgayDat().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
                    .format(formatter);
            infoTable.addCell(new Cell().add(new Paragraph(ngayDatStr).setPadding(5)));
            infoTable.addCell(new Cell().add(new Paragraph("Địa chỉ:").setBold()).setPadding(5));
            infoTable.addCell(new Cell().add(new Paragraph(hoaDon.getDiaChiNhanHang())).setPadding(5));
            document.add(infoTable);

            // Bảng chi tiết sản phẩm với viền và màu sắc
            float[] columnWidths = {200F, 60F, 100F};
            Table table = new Table(UnitValue.createPointArray(columnWidths));
            table.setWidth(UnitValue.createPercentValue(100));
            table.setMarginTop(15).setMarginBottom(20);
            table.setBorder(new SolidBorder(1)); // Thêm viền cho bảng

            // Header bảng
            Cell headerCell1 = new Cell().add(new Paragraph("Sản phẩm").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY).setPadding(5);
            Cell headerCell2 = new Cell().add(new Paragraph("Số lượng").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY).setPadding(5);
            Cell headerCell3 = new Cell().add(new Paragraph("Giá (VNĐ)").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY).setPadding(5);
            table.addHeaderCell(headerCell1);
            table.addHeaderCell(headerCell2);
            table.addHeaderCell(headerCell3);

            // Dữ liệu chi tiết
            List<HoaDonCt> chiTietList = donHangChiTietRepo.findByHoaDon(hoaDon);
            for (HoaDonCt ct : chiTietList) {
                table.addCell(new Cell().add(new Paragraph(ct.getTenSanPham())).setPadding(5));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(ct.getSoLuong()))).setPadding(5));
                table.addCell(new Cell().add(new Paragraph(String.format("%,.0f", ct.getGiaTien()))).setPadding(5));
            }
            document.add(table);

            // Tổng cộng
            Paragraph totalLabel = new Paragraph("Tổng tiền sản phẩm: " + String.format("%,.0f", hoaDon.getTongTien()) + " VNĐ")
                    .setFontSize(12)
                    .setBold()
                    .setMarginTop(10);
            document.add(totalLabel);

            Paragraph shippingFee = new Paragraph("Phí vận chuyển: " + String.format("%,.0f", hoaDon.getTienThue()) + " VNĐ")
                    .setFontSize(12)
                    .setMarginTop(5);
            document.add(shippingFee);

            Double tongCong = hoaDon.getTongTien() + hoaDon.getTienThue(); // Tổng cộng bao gồm ship
            Paragraph totalAmount = new Paragraph("Tổng cộng: " + String.format("%,.0f", tongCong) + " VNĐ")
                    .setFontSize(14)
                    .setBold()
                    .setFontColor(ColorConstants.RED) // Màu đỏ cho tổng cộng
                    .setMarginTop(10);
            document.add(totalAmount);

            // Chữ ký hoặc ghi chú (tuỳ chọn)
            Paragraph note = new Paragraph("Cảm ơn quý khách đã mua hàng! Vui lòng kiểm tra kỹ thông tin trước khi rời đi.")
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(20);
            document.add(note);

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi tạo PDF: " + e.getMessage(), e);
        }
    }

    public Page<LichSuDonHangDto> getLichSuDatHang(
            Integer trangThaiDonHang,
            Integer trangThaiThanhToan,
            Integer phuongThucThanhToan,
            String maDonHang,
            String tenSanPham,
            Date tuNgay,
            Date denNgay,
            Pageable pageable
    ) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepoSitory.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
        Long userId = user.getId();
        Page<HoaDon> hoaDonPage = donHangRepoSitory.filterLichSuHoaDon(
                userId, trangThaiDonHang, trangThaiThanhToan, phuongThucThanhToan,
                maDonHang, tenSanPham, tuNgay, denNgay, pageable
        );

        return hoaDonPage.map(hd -> new LichSuDonHangDto(
                hd.getId(),
                hd.getMa(),
                hd.getKhachHang().getHoTen(),
                hd.getTongSoLuongSp(),
                hd.getDiaChiNhanHang(),
                hd.getThanhToan().getTen(),
                hd.getTongTien(),
                hd.getTrangThai(),
                hd.getTrangThaiThanhToan(),
                hd.getNgayDat(),
                hd.getNgayNhan(),
                hd.getTienThue()
        ));
    }

    public List<HoaDonCtDto> getChiTietDonHang(Long hoaDonId, String tenSanPham) {
        return donHangRepoSitory.getChiTietByHoaDonIdAndTenSanPham(hoaDonId, tenSanPham);    }
}
