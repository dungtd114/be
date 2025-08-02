package com.example.style_store_be_adminSP.entity;

import java.io.Serializable;
import java.util.Objects;

public class GiamGiaSanPhamCtId implements Serializable {
    private Long idGiamGia;
    private Long idSanPhamCt;

    public GiamGiaSanPhamCtId() {}

    public GiamGiaSanPhamCtId(Long idGiamGia, Long idSanPhamCt) {
        this.idGiamGia = idGiamGia;
        this.idSanPhamCt = idSanPhamCt;
    }

    // Phải override equals() và hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GiamGiaSanPhamCtId)) return false;
        GiamGiaSanPhamCtId that = (GiamGiaSanPhamCtId) o;
        return Objects.equals(idGiamGia, that.idGiamGia) &&
                Objects.equals(idSanPhamCt, that.idSanPhamCt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGiamGia, idSanPhamCt);
    }
}
