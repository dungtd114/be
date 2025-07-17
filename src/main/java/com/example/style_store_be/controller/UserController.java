package com.example.style_store_be.controller;

import com.example.style_store_be.dto.UserDto;
import com.example.style_store_be.dto.request.ApiResponse;
import com.example.style_store_be.dto.request.UserCreationRequest;
import com.example.style_store_be.entity.User;
import com.example.style_store_be.service.website.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/nguoi-dung")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserController {
    UserService userService;
    @PostMapping("/dang-ky")
    ApiResponse<User> createUser(@RequestBody UserCreationRequest request){
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }

    @PostMapping("/them-nhan-vien")
    ApiResponse<User> createStaff(@RequestBody UserCreationRequest request){
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createStaff(request));
        return apiResponse;
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









}
