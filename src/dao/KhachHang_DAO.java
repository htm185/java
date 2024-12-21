package dao;

import entity.HoaDon;
import entity.KhachHang;
import connectDB.ConnectDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachHang_DAO {
    
    // Lấy tất cả khách hàng từ cơ sở dữ liệu
    public List<KhachHang> getAllKhachHang() {
        List<KhachHang> list = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Quanlyquancaphe", "sa", "sapassword");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
        	while (rs.next()) {
        		KhachHang kh = new KhachHang(rs.getString("maKhachHang"), rs.getString("tenKhachHang"), rs.getString("soDienThoai"));
        		list.add(kh);
            }

           } catch (SQLException e) {
               e.printStackTrace();
           }
  
        return list;
    }
    
    
    // Thêm khách hàng mới vào cơ sở dữ liệu
    public boolean themKhachHang(KhachHang khachHang) {
        
        String sql = "INSERT INTO KhachHang(maKhachHang, tenKhachHang, soDienThoai) VALUES (?, ?, ?)";
        try (Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Quanlyquancaphe", "sa", "sapassword");
    	    PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        	ps.setString(1, khachHang.getMaKhachHang());
            ps.setString(2, khachHang.getTenKhachHang());
            ps.setString(3, khachHang.getSoDienThoai());
            int result = ps.executeUpdate();
            return result > 0;
    	
    	            
    	     }
            catch (SQLException e) {
                e.printStackTrace();
            }
        return false;
    }
    

    // Cập nhật thông tin khách hàng
    public boolean update(KhachHang khachHang) {
        try {
            Connection con = ConnectDB.getInstance().getConnection();
            String sql = "UPDATE KhachHang SET tenKhachHang = ?, soDienThoai = ? WHERE maKhachHang = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, khachHang.getTenKhachHang());
            stmt.setString(2, khachHang.getSoDienThoai());
            stmt.setString(3, khachHang.getMaKhachHang());
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa khách hàng khỏi cơ sở dữ liệu
    public boolean xoaKhachHang(String maKhachHang) {
        try {
            Connection con = ConnectDB.getInstance().getConnection();
            String sql = "DELETE FROM KhachHang WHERE maKhachHang = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maKhachHang);
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public KhachHang findKhachHangByMaKhachHang(String maKhachHang) {
        KhachHang kh = null;
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM KhachHang WHERE maKhachHang = ?")) {
            stmt.setString(1, maKhachHang);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                kh = new KhachHang(rs.getString("maKhachHang"), rs.getString("tenKhachHang"), rs.getString("soDienThoai"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kh;
    }

    public boolean isCustomerExist(KhachHang khachHang) throws SQLException {
        Connection con = ConnectDB.getConnection();
        String query = "SELECT COUNT(*) FROM KhachHang WHERE maKhachHang = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, khachHang.getMaKhachHang());
        ResultSet rs = stmt.executeQuery();

        rs.next();
        return rs.getInt(1) > 0; // Nếu có khách hàng, trả về true
    }

}
