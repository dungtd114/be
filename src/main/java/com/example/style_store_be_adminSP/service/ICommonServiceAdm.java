package com.example.style_store_be_adminSP.service;

import com.example.style_store_be_adminSP.entity.SanPhamAdm;
import org.springframework.data.domain.Page;

public interface ICommonServiceAdm<T>{
    // cac ham chung CRUD , chi khac nhau Thuc the truy van
    Page<T> getAll(int page, int size);
    T getOne(Long id);

    T add(T object);

    T update(T object);

    void delete(Long id);
}
