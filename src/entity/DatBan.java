package entity;

public class DatBan {
    private String maBan;
    private KhachHang khachHang;
    private String ghiChu;


    public DatBan(String maBan, KhachHang khachHang, String ghiChu) {
        this.maBan = maBan;
        this.khachHang = khachHang;
        this.ghiChu = ghiChu;
    }
    
    public DatBan(String maBan, KhachHang khachHang) {
        this.maBan = maBan;
        this.khachHang = khachHang;
    }
    
   
    public DatBan() {
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }

   

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
