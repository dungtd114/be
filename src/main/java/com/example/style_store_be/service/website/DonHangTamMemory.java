package com.example.style_store_be.service.website;

import com.example.style_store_be.dto.request.DonHangRequest;
import com.example.style_store_be.dto.request.DonHangTam;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DonHangTamMemory {
    private final Map<String, DonHangTam> map = new ConcurrentHashMap<>();

    public void save(String txnRef, DonHangRequest request, String token) {
        map.put(txnRef, new DonHangTam(request, token));
    }

    public DonHangTam get(String txnRef) {
        return map.get(txnRef);
    }

    public void remove(String txnRef) {
        map.remove(txnRef);
    }
}
