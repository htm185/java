package entity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HoaDon {
    private String maHoaDon;
    private KhachHang khachHang;
    private NhanVien nhanVien; 
    private Date ngayVao;
    private TrangThaiThanhToan trangThai;
    private HinhThucThanhToan hinhThucThanhToan;
    private Ban ban;
    private double tongTien;
    private ArrayList<ChiTietHoaDon> chiTietHoaDons;
    
   
    
    public HoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }
   
    public HoaDon(KhachHang khachHang, Ban ban) {
        this.khachHang = khachHang;
        this.ban = ban;
        trangThai = TrangThaiThanhToan.UNPAID;
        hinhThucThanhToan = hinhThucThanhToan.PAYMENT;
        tongTien = 0;
        chiTietHoaDons = new ArrayList<ChiTietHoaDon>();
    }
  
    public HoaDon() {
        khachHang = new KhachHang();
        trangThai = TrangThaiThanhToan.UNPAID;
        hinhThucThanhToan = hinhThucThanhToan.PAYMENT;
        tongTien = 0;
        chiTietHoaDons = new ArrayList<ChiTietHoaDon>();
    }
    
    public HoaDon(String maHoaDon, KhachHang khachHang, NhanVien nhanVien, Date ngayVao, TrangThaiThanhToan trangThai,
			HinhThucThanhToan hinhThucThanhToan, Ban ban, double tongTien) {
		
		this.maHoaDon = maHoaDon;
		this.khachHang = khachHang;
		this.ngayVao = ngayVao;
		this.trangThai = trangThai;
		this.hinhThucThanhToan = hinhThucThanhToan;
		this.ban = ban;
		this.tongTien = tongTien;
		this.nhanVien = nhanVien;
		
	}
    public Ban getBan() {
        return ban;
    }

    public void setBan(Ban ban) {
        this.ban = ban;
    }
    public void addChiTietHoaDon(ChiTietHoaDon chiTietHoaDon)
    {
        chiTietHoaDons.add(chiTietHoaDon);
    }
    
	public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public Date getNgayVao() {
        return ngayVao;
    }

    public void setNgayVao(Date ngayVao) {
        this.ngayVao = ngayVao;
    }

    public TrangThaiThanhToan getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(TrangThaiThanhToan trangThai) {
        this.trangThai = trangThai;
    }

    public HinhThucThanhToan getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }

    public void setHinhThucThanhToan(HinhThucThanhToan hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
    
    public ArrayList<ChiTietHoaDon> getChiTietHoaDons() {
        return chiTietHoaDons;
    }

    public void setChiTietHoaDons(ArrayList<ChiTietHoaDon> chiTietHoaDons) {
        this.chiTietHoaDons = chiTietHoaDons;
    }

    // Phương thức để lấy đối tượng từ ResultSet
    public static HoaDon getFromResultSet(ResultSet rs) throws SQLException {
        String maHoaDon = rs.getNString("maHoaDon");
        String maKhachHang = rs.getNString("maKhachHang");
        String maNV = rs.getNString("maNV");
        Date ngayVao = rs.getDate("ngayVao");
        String trangThaiStr = rs.getNString("trangThai");
        String hinhThucThanhToanStr = rs.getNString("hinhThucThanhToan");
        float tongTien = rs.getFloat("tongTien");

        KhachHang khachHang = new KhachHang();
        khachHang.setMaKhachHang(maKhachHang);

        NhanVien nhanVien = new NhanVien();
        nhanVien.setMaNV(maNV);

        TrangThaiThanhToan trangThai = TrangThaiThanhToan.getByName(trangThaiStr);
        HinhThucThanhToan hinhThucThanhToan = HinhThucThanhToan.getByName(hinhThucThanhToanStr);

        Ban ban = new Ban();
        HoaDon hoaDon = new HoaDon(maHoaDon, khachHang, nhanVien, ngayVao, trangThai, hinhThucThanhToan, ban, tongTien);
        hoaDon.setChiTietHoaDons(new ArrayList<ChiTietHoaDon>());
        
        return hoaDon;
    }
    


 
}
