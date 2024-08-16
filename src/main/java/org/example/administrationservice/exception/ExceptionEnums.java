package org.example.administrationservice.exception;

public enum ExceptionEnums {
    EX_INTERNAL_SERVER("Đã có lỗi xảy ra, vui lòng thử lại sau ít phút"),
    EX_NOT_AUTHORIZATION("Not Authorization"),
    EX_FORBIDDEN("Forbidden"),
    DATA_NOT_VALID("Dữ liệu không hợp lệ"),
    EX_BILL_IS_PAID("Hóa đơn tháng %s/%s đã được thanh toán"),
    EX_CONTRACT_NOT_EXIST("Mã đồng không tồn tại"),
    EX_ROOM_NOT_EXIST("Mã phòng không tồn tại"),
    EX_BILL_NOT_EXIST(""),
    EX_ROOM_WAS_RENTED("Phòng đã được cho thuê, không thể tạo hợp đồng"),
    EX_CONTRACT_STATUS_NOT_VALID("Trạng thái hồ sơ không chính xác"),
    EX_FILE_NOT_FOUND("File không tồn tại"),
    EX_HOUSE_NOT_EXIST("Nhà không tồn tại"),
    EX_HOUSE_EXIST("Nhà đã tồn tại"),
    EX_HOUSE_NOT_EMPTY("Nhà đã đủ phòng"),
    EX_ROOM_UPDATE_ERROR_1(""),
    EX_ROOM_UPDATE_ERROR_2("Phòng đã cho thuê, không thể chỉnh sửa"),
    EX_ROOM_DELETE_ERROR_1(""),
    EX_ROOM_DELETE_ERROR_2("Phòng đã cho thuê, không thể xóa"),
    EX_UTILITY_CODE_EXIST("Dịch vụ đã tồn tại"),
    EX_UTILITY_CODE_NOT_EXIST("Dịch vụ không tồn tại"),
    ;

    private String message;

    ExceptionEnums(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
