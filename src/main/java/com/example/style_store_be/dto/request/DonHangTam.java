package com.example.style_store_be.dto.request;

public class DonHangTam {
    private DonHangRequest request;
    private String token;

    public DonHangTam(DonHangRequest request, String token) {
        this.request = request;
        this.token = token;
    }

    public DonHangRequest getRequest() {
        return request;
    }

    public String getToken() {
        return token;
    }
}
