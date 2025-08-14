package com.example.style_store_be_adminHD.Service.Impl;

import com.example.style_store_be_adminHD.Dto.HoaDonStatusUpdateDto; // Import DTO mới
import com.example.style_store_be_adminHD.Entity.HoaDonAdm; // Import Entity
import com.example.style_store_be_adminHD.Dto.HoaDonAdmDto;
import com.example.style_store_be_adminHD.Dto.HoaDonAdmDtoConverter;
import com.example.style_store_be_adminHD.Repository.HoaDonAdmRepository;
import com.example.style_store_be_adminHD.Service.HoaDonAdmService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import Transactional
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HoaDonServiceIAdmmpl implements HoaDonAdmService {
//    private final HoaDonAdmRepository hoaDonRepository;
//    private final HoaDonAdmDtoConverter hoaDonAdmDtoConverter;
//
//    // Lấy tất cả hóa đơn với phân trang
//    @Override
//    public Page<HoaDonAdmDto> getAllDto(Pageable pageable) {
//        return hoaDonRepository.findAll(pageable) // Sử dụng findAll với Pageable
//                .map(hoaDonAdmDtoConverter::toDto); // Chuyển đổi sang DTO
//    }
//
//    // Lấy hóa đơn theo ID
//    @Override
//    public Optional<HoaDonAdmDto> getByIdDto(Long id) {
//        return hoaDonRepository.findById(id).map(hoaDonAdmDtoConverter::toDto);
//    }
//
//    // Tìm kiếm hóa đơn theo mã với phân trang
//    @Override
//    public Page<HoaDonAdmDto> searchDto(String ma, Pageable pageable) {
//        return hoaDonRepository.findByMaContaining(ma, pageable)
//                .map(hoaDonAdmDtoConverter::toDto);
//    }
//
//    // Phương thức cập nhật trạng thái hóa đơn với logic kiểm tra hợp lệ
//    @Override
//    @Transactional
//    public Optional<HoaDonAdmDto> updateTrangThai(Long id, HoaDonStatusUpdateDto statusUpdateDto) {
//        Optional<HoaDonAdm> hoaDonOptional = hoaDonRepository.findById(id);
//
//        if (hoaDonOptional.isPresent()) {
//            HoaDonAdm hoaDon = hoaDonOptional.get();
//            int currentStatus = hoaDon.getTrangThai(); // Trạng thái hiện tại của hóa đơn
//            int newStatus = statusUpdateDto.getTrangThai(); // Trạng thái mới muốn chuyển đến
//
//            // --- BẮT ĐẦU LOGIC KIỂM TRA CHUYỂN ĐỔI TRẠNG THÁI HỢP LỆ ---
//
//            // Điều kiện chuyển đổi trạng thái:
//            // 0: Chờ xác nhận
//            // 1: Chờ vận chuyển
//            // 2: Đang vận chuyển
//            // 3: Đã hoàn thành (Đã giao hàng)
//            // 4: Đã hủy
//            // 5: Hoàn tiền / Trả hàng
//            // 6: Chờ xác nhận tại quầy
//
//            boolean isValidTransition = false;
//
//            switch (currentStatus) {
//                case 0: // Từ "Chờ xác nhận"
//                    if (newStatus == 1 || newStatus == 4 || newStatus == 6) { // Có thể chuyển sang "Chờ vận chuyển", "Đã hủy", "Chờ xác nhận tại quầy"
//                        isValidTransition = true;
//                    }
//                    break;
//                case 1: // Từ "Chờ vận chuyển"
//                    if (newStatus == 2 || newStatus == 4) { // Có thể chuyển sang "Đang vận chuyển", "Đã hủy"
//                        isValidTransition = true;
//                    }
//                    break;
//                case 2: // Từ "Đang vận chuyển"
//                    if (newStatus == 3 || newStatus == 4 || newStatus == 5) { // Có thể chuyển sang "Đã hoàn thành", "Đã hủy", "Hoàn tiền / Trả hàng"
//                        isValidTransition = true;
//                    }
//                    break;
//                case 3: // Từ "Đã hoàn thành"
//                    // Sau khi đã hoàn thành, thường chỉ có thể chuyển sang "Hoàn tiền / Trả hàng"
//                    if (newStatus == 5) {
//                        isValidTransition = true;
//                    }
//                    break;
//                case 4: // Từ "Đã hủy"
//                    // Đơn hàng đã hủy không thể chuyển sang trạng thái khác (trừ khi có nghiệp vụ đặc biệt cho phép hoàn tác)
//                    // Hiện tại, không cho phép chuyển từ "Đã hủy" sang bất kỳ trạng thái nào khác.
//                    isValidTransition = false;
//                    break;
//                case 5: // Từ "Hoàn tiền / Trả hàng"
//                    // Trạng thái cuối, không thể chuyển sang trạng thái khác
//                    isValidTransition = false;
//                    break;
//                case 6: // Từ "Chờ xác nhận tại quầy"
//                    if (newStatus == 3 || newStatus == 4) { // Có thể chuyển sang "Đã hoàn thành" (nếu khách đến lấy), "Đã hủy"
//                        isValidTransition = true;
//                    }
//                    break;
//                default:
//                    // Trạng thái không xác định hoặc không cho phép chuyển đổi
//                    isValidTransition = false;
//                    break;
//            }
//
//            // Nếu trạng thái mới giống trạng thái hiện tại, coi là hợp lệ (không có gì thay đổi)
//            if (currentStatus == newStatus) {
//                isValidTransition = true;
//            }
//
//            // --- KẾT THÚC LOGIC KIỂM TRA CHUYỂN ĐỔI TRẠNG THÁI HỢP LỆ ---
//
//            if (isValidTransition) {
//                hoaDon.setTrangThai(newStatus);
//                hoaDon.setNgaySua(new Date()); // Cập nhật ngày sửa khi trạng thái thay đổi
//
//                // Lưu hóa đơn đã cập nhật vào cơ sở dữ liệu
//                HoaDonAdm updatedHoaDon = hoaDonRepository.save(hoaDon);
//
//                // Chuyển đổi và trả về DTO của hóa đơn đã cập nhật
//                return Optional.of(hoaDonAdmDtoConverter.toDto(updatedHoaDon));
//            } else {
//                // Nếu chuyển đổi trạng thái không hợp lệ, trả về Optional.empty()
//                // hoặc bạn có thể ném một ngoại lệ tùy chỉnh để Controller bắt và trả về lỗi cụ thể hơn
//                System.out.println("Chuyển đổi trạng thái không hợp lệ: Từ " + currentStatus + " sang " + newStatus);
//                return Optional.empty();
//            }
//        }
//        // Trả về Optional.empty() nếu không tìm thấy hóa đơn
//        return Optional.empty();
//    }

    private final HoaDonAdmRepository hoaDonRepository;
    private final HoaDonAdmDtoConverter hoaDonAdmDtoConverter;

    @Override
    public Page<HoaDonAdmDto> getAllDto(Pageable pageable) {
        return hoaDonRepository.findAll(pageable)
                .map(hoaDonAdmDtoConverter::toDto);
    }

    @Override
    public Optional<HoaDonAdmDto> getByIdDto(Long id) {
        return hoaDonRepository.findByIdWithChiTietHoaDon(id).map(hoaDonAdmDtoConverter::toDto);
    }

    @Override
    public Page<HoaDonAdmDto> searchDto(String ma, Pageable pageable) {
        return hoaDonRepository.findByMaContaining(ma, pageable)
                .map(hoaDonAdmDtoConverter::toDto);
    }

    @Override
    @Transactional
    public Optional<HoaDonAdmDto> updateTrangThai(Long id, HoaDonStatusUpdateDto statusUpdateDto) {
        Optional<HoaDonAdm> hoaDonOptional = hoaDonRepository.findById(id);

        if (hoaDonOptional.isPresent()) {
            HoaDonAdm hoaDon = hoaDonOptional.get();
            int currentStatus = hoaDon.getTrangThai();
            int newStatus = statusUpdateDto.getTrangThai();

            boolean isValidTransition = false;

            switch (currentStatus) {
                case 0: // Chờ xác nhận
                    if (newStatus == 1 || newStatus == 4 || newStatus == 6) {
                        isValidTransition = true;
                    }
                    break;
                case 1: // Chờ vận chuyển
                    if (newStatus == 2 || newStatus == 4) {
                        isValidTransition = true;
                    }
                    break;
                case 2: // Đang vận chuyển
                    if (newStatus == 3 || newStatus == 4 || newStatus == 5) {
                        isValidTransition = true;
                    }
                    break;
                case 3: // Đã hoàn thành
                    if (newStatus == 5) {
                        isValidTransition = true;
                    }
                    break;
                case 4: // Đã hủy
                    isValidTransition = false; // Đã hủy không thể chuyển tiếp
                    break;
                case 5: // Hoàn tiền / Trả hàng
                    isValidTransition = false; // Trạng thái cuối
                    break;
                case 6: // Chờ xác nhận tại quầy
                    if (newStatus == 3 || newStatus == 4) {
                        isValidTransition = true;
                    }
                    break;
                default:
                    isValidTransition = false;
                    break;
            }

            if (currentStatus == newStatus) { // Cho phép cập nhật nếu trạng thái không đổi
                isValidTransition = true;
            }

            if (isValidTransition) {
                hoaDon.setTrangThai(newStatus);
                hoaDon.setNgaySua(new Date());
                HoaDonAdm updatedHoaDon = hoaDonRepository.save(hoaDon);

                return Optional.of(hoaDonAdmDtoConverter.toDto(updatedHoaDon));
            } else {
                System.out.println("Chuyển đổi trạng thái không hợp lệ: Từ " + currentStatus + " sang " + newStatus);
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

}