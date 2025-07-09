package com.example.style_store_be.service.website;

import com.example.style_store_be.dto.request.DonHangRequest;
import com.example.style_store_be.entity.HoaDon;
import com.example.style_store_be.entity.HoaDonCt;
import com.example.style_store_be.entity.PtThanhToan;
import com.example.style_store_be.entity.User;
import com.example.style_store_be.mapper.DonHangChiTietMapper;
import com.example.style_store_be.mapper.DonHangMapper;
import com.example.style_store_be.repository.PhuongThucTTRepo;
import com.example.style_store_be.repository.website.DonHangChiTietRepo;
import com.example.style_store_be.repository.website.DonHangRepoSitory;
import com.example.style_store_be.repository.website.UserRepoSitory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
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

    public HoaDon createrDonHang(DonHangRequest request) {
        HoaDon hoaDon = donHangMapper.toHoaDon(request);
        hoaDon.setNgayDat(new Date());
        hoaDon.setNgayTao(new Date());
        hoaDon.setTrangThai(1);
        if (hoaDon.getMa() == null || hoaDon.getMa().isEmpty()) {
            hoaDon.setMa("HD" + UUID.randomUUID().toString().substring(0, 10));
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepoSitory.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
        hoaDon.setKhachHang(user);
        hoaDon.setNguoiTao(user);

        PtThanhToan ptThanhToan = phuongThucTTRepo.findById(1L).orElseThrow(() -> new RuntimeException("Phương thức thanh toán không tồn tại"));
        hoaDon.setThanhToan(ptThanhToan);

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

        sendInvoiceEmail(savedHoaDon);
        return savedHoaDon;
    }

    private void sendInvoiceEmail(HoaDon hoaDon) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo("quyetvan0803@gmail.com");
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
                document.setFontSize(11);
            }

            // Tiêu đề
            Paragraph title = new Paragraph("HÓA ĐƠN ĐẶT HÀNG")
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(title);

            // Thông tin đơn
            document.add(new Paragraph("Mã hóa đơn: " + hoaDon.getMa()));
            document.add(new Paragraph("Khách hàng: " + hoaDon.getKhachHang().getHoTen()));
            document.add(new Paragraph("Ngày đặt: " + hoaDon.getNgayDat()));
            document.add(new Paragraph("Địa chỉ: " + hoaDon.getDiaChiNhanHang()));
            document.add(new Paragraph("Tổng tiền: " + hoaDon.getTongTien() + " VNĐ"));
            document.add(new Paragraph("Phí vận chuyển: " + hoaDon.getTienThue() + " VNĐ"));

            // Bảng chi tiết
            float[] columnWidths = {200F, 60F, 100F};
            Table table = new Table(columnWidths);
            table.setMarginTop(15).setMarginBottom(20);

            table.addHeaderCell(new Cell().add(new Paragraph("Sản phẩm").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Số lượng").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Giá (VNĐ)").setBold()));

            List<HoaDonCt> chiTietList = donHangChiTietRepo.findByHoaDon(hoaDon);
            for (HoaDonCt ct : chiTietList) {
                table.addCell(ct.getTenSanPham());
                table.addCell(String.valueOf(ct.getSoLuong()));
                table.addCell(String.format("%,.0f", ct.getGiaTien()));
            }

            document.add(table);
            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi tạo PDF: " + e.getMessage(), e);
        }
    }
}
