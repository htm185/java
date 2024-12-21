package entity;

public enum HinhThucThanhToan {
    PAYMENT( "Tiền mặt"),
    CREDIT( "Chuyển khoản");
	

    private final String name;

    HinhThucThanhToan(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static HinhThucThanhToan getByName(String name) {
        for (HinhThucThanhToan trangThai : values()) {
            if (trangThai.name.equals(name)) {
                return trangThai;
            }
        }
        return null;
    }
    

    
    
   
    
    
}
