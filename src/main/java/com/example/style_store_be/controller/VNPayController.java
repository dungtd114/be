package com.example.style_store_be.controller;

import com.example.style_store_be.dto.request.DonHangRequest;
import com.example.style_store_be.dto.request.DonHangTam;
import com.example.style_store_be.entity.User;
import com.example.style_store_be.repository.website.UserRepoSitory;
import com.example.style_store_be.service.JwtService;
import com.example.style_store_be.service.website.DonHangService;
import com.example.style_store_be.service.website.DonHangTamMemory;
import com.example.style_store_be.service.website.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;
@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/api/vnpay")
public class VNPayController {
    @Autowired
    private VNPayService vnpayService;
    @Autowired
    private DonHangService donHangService;
    @Autowired
    private DonHangTamMemory donHangTamMemory;

    @Autowired
    private UserRepoSitory userRepoSitory;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/submitOrder")
    public ResponseEntity<String> submitOrder(
            @RequestBody DonHangRequest request,
            @RequestHeader("Authorization") String bearerToken,
            HttpServletRequest httpRequest
    ) {
        String txnRef = String.valueOf(System.currentTimeMillis());
        String orderInfo = "Thanh toan don hang #" + txnRef;

        String token = bearerToken.replace("Bearer ", "");

        // Lưu đơn hàng + token vào RAM
        donHangTamMemory.save(txnRef, request, token);

        String vnpayUrl = vnpayService.createOrder(httpRequest, request.getTongTien().intValue(), orderInfo, txnRef);

        return ResponseEntity.ok(vnpayUrl);
    }

    @GetMapping("/vnpay-payment-return")
    public void paymentReturn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int status = vnpayService.orderReturn(request);
        String txnRef = request.getParameter("vnp_TxnRef");

        DonHangTam donHangTam = donHangTamMemory.get(txnRef);

        if (status == 1 && donHangTam != null) {
            DonHangRequest donHangRequest = donHangTam.getRequest();
            String token = donHangTam.getToken();

            // ✅ Dùng token để lấy email (không dùng SecurityContext nữa!)
            String email = jwtService.extractUsername(token);
            User user = userRepoSitory.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

            donHangService.createrDonHangVNPay(donHangRequest, user);
            response.sendRedirect("http://localhost:5173/thanh-toan-thanh-cong");
        } else {
            response.sendRedirect("http://localhost:5173/thanh-toan-that-bai");
        }

    }




}
