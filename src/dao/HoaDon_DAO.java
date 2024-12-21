
package dao;

import entity.HoaDon;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import entity.KhachHang;
import entity.NhanVien;
import entity.Ban;
import dao.KhachHang_DAO;
import entity.TrangThaiThanhToan;
import entity.HinhThucThanhToan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;

public class HoaDon_DAO{
    KhachHang_DAO khachHangDao = new KhachHang_DAO();
    Connection con = ConnectDB.getInstance().getConnection();
    public HoaDon_DAO()
    {
    	
    }
    
    public ArrayList<HoaDon> getAll() throws SQLException {

        ArrayList<HoaDon> hoaDons = new ArrayList<>();
//        (Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Quanlyquancaphe", "sa", "sapassword");
        try {
        		Connection con = ConnectDB.getInstance().getConnection();
        		String query = "SELECT * FROM HoaDon";
        		Statement statement = (Statement) con.createStatement();
     			ResultSet rs = statement.executeQuery(query);
        	while (rs.next()) {
                HoaDon hoaDon = HoaDon.getFromResultSet(rs);
                hoaDons.add(hoaDon);
            }

           } catch (SQLException e) {
               e.printStackTrace();
           }

        return hoaDons;
    }
    

    
    public HoaDon get(String id) throws SQLException {
        String query = "SELECT * FROM HoaDon WHERE maHoaDon = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, id);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            return HoaDon.getFromResultSet(rs);
        }
        return null;
    }
    public void updateTrangThaiBan(String maBan, String trangThai) throws SQLException {
    	Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Quanlyquancaphe", "sa", "sapassword");
        String query = "UPDATE Ban SET trangThai = ? WHERE maBan = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, trangThai);
            stmt.setString(2, maBan);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public String save(HoaDon hoaDon) throws SQLException {
    	String maHoaDon = null;
//    	System.out.println(hoaDon.getKhachHang().getMaKhachHang());
        String sql = "INSERT INTO HoaDon (maHoaDon, maKhachHang, maNV, ngayVao, trangThai, hinhThucThanhToan, tongTien) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Quanlyquancaphe", "sa", "sapassword");
	        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        		ps.setString(1, hoaDon.getMaHoaDon().toString());
	            ps.setString(2, hoaDon.getKhachHang().getMaKhachHang());
	            ps.setString(3, hoaDon.getNhanVien().getMaNV());
	            ps.setDate(4, hoaDon.getNgayVao());  
	            ps.setString(5, hoaDon.getTrangThai().getName());
	            ps.setString(6, hoaDon.getHinhThucThanhToan().getName());
	            ps.setDouble(7, hoaDon.getTongTien());
	
	            int rowsAffected = ps.executeUpdate();
	
	           
	            if (rowsAffected > 0) {
	                // Lấy maHoaDon tự động sinh ra
	                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
	                    if (generatedKeys.next()) {
	                        maHoaDon = generatedKeys.getString(1);  // Lấy giá trị maHoaDon
	                    }
	                }
	            }
//	            System.out.println(hoaDon.getMaHoaDon());
//	            System.out.println("Đã lưu hóa đơn thành công với mã: " + hoaDon.getMaHoaDon());
	            return hoaDon.getMaHoaDon();
	        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


    }
    

    public HoaDon createNewHoaDon(Ban ban) throws SQLException {
        HoaDon hoaDon = new HoaDon();
        
      
        String maKhachHang = hoaDon.getKhachHang().getMaKhachHang();
        if (maKhachHang == null || maKhachHang.isEmpty()) {
            throw new IllegalArgumentException("Mã khách hàng không hợp lệ.");
        }

        String maNV = hoaDon.getNhanVien().getMaNV();
        if (maNV == null || maNV.isEmpty()) {
            throw new IllegalArgumentException("Mã nhân viên không hợp lệ.");
        }

        
        NhanVien nhanVien = NhanVien_DAO.getNhanVienByMaNV(maNV);  
        if (nhanVien == null) {
            throw new IllegalArgumentException("Nhân viên không hợp lệ.");
        }

        hoaDon.setNhanVien(nhanVien);

      
        hoaDon.setBan(ban);

        
        String maHoaDon = save(hoaDon);  
        hoaDon.setMaHoaDon(maHoaDon);

        return hoaDon;
    }


    public HoaDon getByMaBan(String maBan) throws SQLException
    {
       String query = "SELECT * FROM `HoaDon` WHERE maBan = ? AND trangThai = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, maBan);
        stmt.setString(2, "Chưa thanh toán");

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            HoaDon hoaDon = HoaDon.getFromResultSet(rs);
            hoaDon.setBan(new Ban(maBan));
            return hoaDon;
        }
        return new HoaDon();
    }
    public void update(HoaDon hoaDon) throws SQLException {
        String query = "UPDATE HoaDon SET ngayVao = ?, trangThai = ?, hinhThucThanhToan = ?, tongTien = ? WHERE maHoaDon = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setDate(1, hoaDon.getNgayVao());
        stmt.setString(2, hoaDon.getTrangThai().name());
        stmt.setString(3, hoaDon.getHinhThucThanhToan().name());
        stmt.setDouble(4, hoaDon.getTongTien());
        stmt.setString(5, hoaDon.getMaHoaDon());

        stmt.executeUpdate();
    }
    public void delete(HoaDon hoaDon) throws SQLException {
        String query = "DELETE FROM HoaDon WHERE maHoaDon = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, hoaDon.getMaHoaDon());
        stmt.executeUpdate();
    }
    public void deleteById(String id) throws SQLException {
        String query = "DELETE FROM HoaDon WHERE maHoaDon = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, id);
        stmt.executeUpdate();
    }
}