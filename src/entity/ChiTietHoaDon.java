package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChiTietHoaDon {
    private HoaDon hoaDon;
    private ThucUong thucUong;
    private int soLuong;
    private double giaTien;

    public ChiTietHoaDon(HoaDon hoaDon, ThucUong thucUong, int soLuong) {
        this.hoaDon = hoaDon;
        this.thucUong = thucUong;
        this.soLuong = soLuong;
        
    }
    public ChiTietHoaDon() {
       
    }
    public ChiTietHoaDon(HoaDon hoaDon, ThucUong thucUong) {
        this.hoaDon = hoaDon;
        this.thucUong = thucUong;
    }

    public ChiTietHoaDon(ThucUong thucUong,  double giaTien, int soLuong) {
        this.thucUong = thucUong;
        this.giaTien = giaTien;
        this.soLuong = soLuong;
    }
    
    
    public double getGiaTien()
    {
        return giaTien;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    public ThucUong getThucUong() {
        return thucUong;
    }

    public void setThucUong(ThucUong thucUong) {
        this.thucUong = thucUong;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    
    
    public static ChiTietHoaDon getFromResultSetByMaHoaDon(ResultSet rs)  throws  SQLException {
        String maNuoc = rs.getNString("maThucUong");
        String tenNuoc = rs.getNString("tenNuoc");
        double giaTien = rs.getDouble("giaTien");
        int soLuong = rs.getInt("soLuong");
        ThucUong thucUong = new ThucUong(maNuoc, tenNuoc);
        ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(thucUong, giaTien, soLuong);
   
        
        return chiTietHoaDon;
    }
    
    
    
  
    
    
}
