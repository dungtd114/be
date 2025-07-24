package com.example.style_store_be.service.website;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.example.style_store_be.dto.request.DonHangRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VNPayService {
    @Value("${vnpay.tmnCode}")
    private String vnpTmnCode;

    @Value("${vnpay.hashSecret}")
    private String vnpHashSecret;

    @Value("${vnpay.url}")
    private String vnpUrl;

    @Value("${vnpay.returnUrl}")
    private String vnpReturnUrl;

    public String createOrder(HttpServletRequest request, int orderTotal, String orderInfo, String txnRef) {
        String vnpVersion = "2.1.0";
        String vnpCommand = "pay";
        String vnpOrderType = "billpayment";
        String vnpCurrCode = "VND";
        String vnpLocale = "vn";
        String vnpIpAddr = request.getRemoteAddr();
        String vnpCreateDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        Map<String, String> vnpParams = new TreeMap<>();
        vnpParams.put("vnp_Version", vnpVersion);
        vnpParams.put("vnp_Command", vnpCommand);
        vnpParams.put("vnp_TmnCode", vnpTmnCode);
        vnpParams.put("vnp_Amount", String.valueOf(orderTotal * 100)); // *100 vì đơn vị là xu
        vnpParams.put("vnp_CurrCode", vnpCurrCode);
        vnpParams.put("vnp_TxnRef", txnRef); // dùng mã riêng để tra đơn sau
        vnpParams.put("vnp_OrderInfo", orderInfo);
        vnpParams.put("vnp_OrderType", vnpOrderType);
        vnpParams.put("vnp_Locale", vnpLocale);
        vnpParams.put("vnp_ReturnUrl", vnpReturnUrl); // Không thêm token vào URL
        vnpParams.put("vnp_IpAddr", vnpIpAddr);
        vnpParams.put("vnp_CreateDate", vnpCreateDate);

        // Tạo chuỗi query và hash
        StringBuilder query = new StringBuilder();
        StringBuilder hashData = new StringBuilder();
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            try {
                String value = URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString());
                query.append(entry.getKey()).append("=").append(value).append("&");
                hashData.append(entry.getKey()).append("=").append(value).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        query.setLength(query.length() - 1);
        hashData.setLength(hashData.length() - 1);

        String secureHash = hmacSHA512(vnpHashSecret, hashData.toString());
        return vnpUrl + "?" + query + "&vnp_SecureHash=" + secureHash;
    }


    public int orderReturn(HttpServletRequest request) {
        Map<String, String> vnpParams = new TreeMap<>();
        String vnpSecureHash = request.getParameter("vnp_SecureHash");

        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String paramName = params.nextElement();
            if (!paramName.equals("vnp_SecureHash")) {
                vnpParams.put(paramName, request.getParameter(paramName));
            }
        }

        StringBuilder hashData = new StringBuilder();
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            try {
                hashData.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString())).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        hashData.setLength(hashData.length() - 1);

        String signData = hmacSHA512(vnpHashSecret, hashData.toString());
        if (signData.equals(vnpSecureHash)) {
            return request.getParameter("vnp_ResponseCode").equals("00") ? 1 : 0; // 1: Thành công, 0: Thất bại
        }
        return 0; // Checksum không hợp lệ
    }

    private String hmacSHA512(String key, String data) {
        try {
            Mac sha512Hmac = Mac.getInstance("HmacSHA512");
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            sha512Hmac.init(keySpec);
            byte[] macData = sha512Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder result = new StringBuilder();
            for (byte b : macData) {
                result.append(String.format("%02x", b));
            }
            return result.toString();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }
}
