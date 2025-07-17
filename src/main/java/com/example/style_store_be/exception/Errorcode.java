package com.example.style_store_be.exception;

public enum Errorcode {
    UNCATEGORIZED_EXCEPTION(9999, "UNCATEGORIZED_EXCEPTION"),
    USER_EXISTED(1002, "User đã tồn tại , vui lòng nhập user name khác"),
    USERNAME_INVALID(1003,"User name phải >3"),
    PASSWORD_INVALID(1004,"Pass word khong hop le"),
    KEY_INVALID(1001,"Invalid message key"),
    USER_NOT_EXISTED(1005, "User not existed"),
    UNAUTHENTICATED(1006, "Unauthenticated"),
    ACCESS_DENIED(403, "Bạn không có quyền truy cập"),
    ROLE_NOT_FOUND(404, "Chức vụ không tồn tại"),

    INVALID_VOUCHER(400, "Vui lòng nhập giá trị từ 0 - 100 %"),
    INVALID_NAME_VOUCHER(400, "Vui lòng nhập tên voucher 0-100 ký tự"),
    ;

    private int code;
    private String message;

    Errorcode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
