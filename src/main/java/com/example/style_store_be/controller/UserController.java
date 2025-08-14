package com.example.style_store_be.controller;

import com.example.style_store_be.dto.UserDto;
import com.example.style_store_be.dto.request.ApiResponse;
import com.example.style_store_be.dto.request.UserCreationRequest;
import com.example.style_store_be.dto.request.UserUpdateRequest;
import com.example.style_store_be.dto.response.UserResponse;
import com.example.style_store_be.entity.User;
import com.example.style_store_be.service.website.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/nguoi-dung")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserController {
    UserService userService;
    @PostMapping("/dang-ky")
    ApiResponse<User> createUser(@Valid @RequestBody UserCreationRequest request){
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }

    @PostMapping("/them-nhan-vien")
    public ApiResponse<User> createStaff(@Valid @RequestBody UserCreationRequest request) {
        try {
            ApiResponse<User> apiResponse = new ApiResponse<>();
            apiResponse.setResult(userService.createStaff(request));
            return apiResponse;
        } catch (Exception e) {
            log.error("Error when creating staff: ", e);
            throw e;
        }
    }

    @PostMapping("/them-khach-hang")
    public ApiResponse<User> createrCustomer( @Valid @RequestBody UserCreationRequest request) {
        try {
            ApiResponse<User> apiResponse = new ApiResponse<>();
            apiResponse.setResult(userService.createrCustomer(request));
            return apiResponse;
        } catch (Exception e) {
            log.error("Error when creating staff: ", e);
            throw e;
        }
    }

    @GetMapping("/danh-sach-nhan-vien")
    public Page<UserDto> pageStaff (@RequestParam(defaultValue = "0") int page , @RequestParam(defaultValue = "5") int size,
                                    @RequestParam (required = false) String hoTenOrSoDTOrEmail,
                                    @RequestParam(required = false) Integer gioiTinh,
                                    @RequestParam(required = false) Integer trangThai

                                 ){
        Pageable pageable = PageRequest.of(page,size);
        return userService.getPageStaff(hoTenOrSoDTOrEmail, gioiTinh, trangThai, pageable);
    }

    @GetMapping("/danh-sach-khach-hang")
    public Page<UserDto> pageUser (@RequestParam(defaultValue = "0") int page , @RequestParam(defaultValue = "5") int size,
                                    @RequestParam (required = false) String hoTenOrSoDTOrEmail,
                                    @RequestParam(required = false) Integer gioiTinh,
                                    @RequestParam(required = false) Integer trangThai

    ){
        Pageable pageable = PageRequest.of(page,size);
        return userService.getPageUser(hoTenOrSoDTOrEmail, gioiTinh, trangThai, pageable);
    }

    @GetMapping("/chi-tiet/{id}")
    public ApiResponse<UserResponse> getUserDetail(@PathVariable Long id) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getUserDetail(id));
        return apiResponse;
    }

    @GetMapping("/thong-tin-cua-toi")
    public ApiResponse<UserResponse> getMyInfo() {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getMyInfo());
        return apiResponse;
    }

    @PutMapping("/sua-thong-tin/{id}")
    UserResponse updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request) {
        try {
            return userService.updateUser(id, request);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PutMapping("/cap-nhat-thong-tin-cua-toi")
    UserResponse updateMyInfo(@Valid @RequestBody UserUpdateRequest request) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            return userService.updateMyInfo(email, request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/xoa-nguoi-dung/{id}")
    public String removeNguoiDung(@PathVariable Long id) {
        userService.removeUser(id);
        return "Xoá nhân viên thành công";
    }


}
