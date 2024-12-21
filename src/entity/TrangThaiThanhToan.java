package entity;

public enum TrangThaiThanhToan {
    PAID("Đã thanh toán"), 
    UNPAID("Chưa thanh toán");

    private final String name;

    TrangThaiThanhToan(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static TrangThaiThanhToan getByName(String name) {
        for (TrangThaiThanhToan trangThai : values()) {
            if (trangThai.name.equals(name)) {
                return trangThai;
            }
        }
        return null;
    }
}
