package com.example.style_store_be.exception;

public enum Errorcode {

    UNCATEGORIZED_EXCEPTION(9999, "Lỗi không xác định"),
    MISSING_REQUIRED_FIELDS(1000, "Mời nhập đầy đủ thông tin"),
    KEY_INVALID(1001, "Invalid message key"),

    EMAIL_EXISTED(1002, "Email đã tồn tại, vui lòng nhập email khác"),
    PHONE_EXISTED(1002, "SDT đã tồn tại, vui lòng nhập SDT khác"),
    USER_NOT_EXISTED(1003, "User không tồn tại"),
    UNAUTHENTICATED(403, "Bạn chưa đăng nhập"),
    ACCESS_DENIED(401, "Bạn không có quyền truy cập"),
    ROLE_NOT_FOUND(1006, "Chức vụ không tồn tại"),

    USERNAME_INVALID(1010, "Tên đăng nhập phải lớn hơn 3 ký tự"),
    PASSWORD_INVALID(1011, "Mật khẩu không hợp lệ"),
    PASSWORD_WEAK(1012, "Mật khẩu phải có độ dài từ 6 đến 100 ký tự"),

    EMAIL_INVALID_FORMAT(1020, "Email không đúng định dạng"),
    EMAIL_TOO_LONG(1021, "Email quá dài (tối đa 100 ký tự)"),

    PHONE_INVALID_FORMAT(1030, "Số điện thoại không đúng định dạng"),
    PHONE_LENGTH_INVALID(1031, "Số điện thoại phải có đúng 10 chữ số"),

    CCCD_INVALID_FORMAT(1040, "CCCD phải gồm đúng 12 chữ số"),

    DOB_INVALID(1050, "Ngày sinh phải trước ngày hiện tại"),

    INVALID_VOUCHER(1100, "Vui lòng nhập giá trị từ 0 - 100%"),
    INVALID_NAME_VOUCHER(1101, "Vui lòng nhập tên voucher từ 0 - 100 ký tự"),
    INVALID_MAX_DISCOUNT(1102, "Vui lòng nhập giá trị giảm tối đa lớn hơn 0"),
    INVALID_CONDITION(1103, "Vui lòng nhập điều kiện giảm lớn hơn 0"),
    INVALID_DATE_RANGE(1104, "Ngày kết thúc phải sau ngày bắt đầu"),
    INVALID_END_DATE(1105, "Ngày kết thúc phải là trong tương lai"),
    INVALID_DESCRIPTION(1106, "Mô tả không được vượt quá 500 ký tự"),
    INVALID_PRODUCT_IDS(1107, "Phải chọn ít nhất 1 sản phẩm muốn giảm giá sản phẩm chi tiết"),
    INVALID_NAME_SIZE(1108, "Tên không được vượt quá 100 ký tự"),
    INVALID_DISCOUNT_AMOUNT(1109, "Số tiền giảm giá không được vượt quá số tiền của sản phẩm chi tiết"),
    INVALID_DISCOUNT_PERCENTAGE(1110, "Phần trăm giảm giá không được vượt quá 100%"),
    INVALID_DISCOUNT_AMOUNT_ZERO(1111, "Số tiền giảm giá phải lớn hơn 0"),
    INVALID_QUANTITY(1112, "Số lượng phải lớn hơn 0"),
    INVALID_PRICE(1113, "Giá phải lớn hơn 0"),
    INVALID_MIN_MAX_PRICE(1114, "Giá bán phải lớn hơn giá nhập"),
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
