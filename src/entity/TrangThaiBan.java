
package entity;

public enum TrangThaiBan {
    FREE("Trống"), 
    RESERVED("Đã đặt");

    private final String name;

    TrangThaiBan(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static TrangThaiBan getByName(String name) {
        for (TrangThaiBan trangThai : values()) {
            if (trangThai.name.equals(name)) {
                return trangThai;
            }
        }
        return null;
    }
}


