package dao;

import entity.NhanVien;
import entity.ThucUong;

import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import connectDB.ConnectDB;

public class NhanVien_DAO {
    
	ArrayList<NhanVien> dsnv;
	NhanVien nv;
	
	public NhanVien_DAO() {
		// TODO Auto-generated constructor stub
		dsnv = new ArrayList<NhanVien>();
		nv = new NhanVien();
	}
	
	public NhanVien findByUsernameAndPassword(String userName, String password) throws SQLException {
	   
	    ConnectDB.getInstance().connect();
	    Connection con = ConnectDB.getInstance().getConnection();
	    
	    if (con == null) {
	        throw new SQLException("Connection to the database failed.");
	    }

	   
	    String query = "SELECT * FROM NhanVien WHERE username = ? AND password = ?";
	    PreparedStatement ps = con.prepareStatement(query);
	    ps.setString(1, userName);
	    ps.setString(2, password);

	    ResultSet rs = ps.executeQuery();
	    if (rs.next()) {
	        NhanVien employee = NhanVien.getFromResultSet(rs); // Chuyển ResultSet thành đối tượng NhanVien
	        return employee;
	    }
	    return null;
	}
	
	public ArrayList<NhanVien> getallNhanVien() {
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			String sql ="select * from NhanVien";
			Statement statement = (Statement) con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				String maNV = rs.getString(1);
				String hoten = rs.getString(2);
				String username = rs.getString(3);
				String password = rs.getString(4);
				String diaChi = rs.getString(5);
				String sdt = rs.getString(6);
				NhanVien v = new NhanVien(maNV, hoten, username, password, diaChi, sdt);
				dsnv.add(v);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return dsnv;
	}
	
	
	public static NhanVien getNhanVienByMaNV(String maNV) throws SQLException {
		Connection con = ConnectDB.getInstance().getConnection();
	    String sql = "SELECT * FROM NhanVien WHERE maNV = ?";
	    try (PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setString(1, maNV);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            String hoTen = rs.getString("hoTen");
	            String username = rs.getString("username");
	            String password = rs.getString("password");
	            String diaChi = rs.getString("diaChi");
	            String sdt = rs.getString("sdt");

	            // Tạo và trả về đối tượng NhanVien
	            return new NhanVien(maNV, hoTen, username, password, diaChi, sdt);
	        } else {
	            throw new SQLException("Không tìm thấy nhân viên với mã " + maNV);
	        }
	    }
	}

	
	public boolean create(NhanVien v) {
	    ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;
	    try {
	        stmt = con.prepareStatement("INSERT INTO NhanVien (maNV, hoTen, username, password, diaChi, sdt) VALUES (?, ?, ?, ?, ?, ?)");
	        stmt.setString(1, v.getMaNV());
	        stmt.setString(2, v.getHoTen());
	        stmt.setString(3, v.getUsername());
	        stmt.setString(4, v.getPassword());
	        stmt.setString(5, v.getDiaChi());
	        stmt.setString(6, v.getSdt());
	        n = stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();  // In ra lỗi chi tiết
	    }
	    return n > 0;
	}

	public boolean delete(String maNV) {
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;
	    try {
	        stmt = con.prepareStatement("DELETE FROM NhanVien WHERE maNV = ?");
	        stmt.setString(1, maNV);
	        n = stmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return n > 0;
	}
    
	public boolean update(NhanVien v) {
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;
	    try {
	        stmt = con.prepareStatement("UPDATE NhanVien SET hoTen = ?, username = ?, password = ?, diaChi = ?, sdt = ? WHERE maNV = ?");
	        stmt.setString(1, v.getHoTen());
	        stmt.setString(2, v.getUsername());
	        stmt.setString(3, v.getPassword());
	        stmt.setString(4, v.getDiaChi());
	        stmt.setString(5, v.getSdt());
	        stmt.setString(6, v.getMaNV());
	        n = stmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return n > 0;
	}

	public ArrayList<NhanVien> searchByMaNV(String maNV) {
	    ArrayList<NhanVien> result = new ArrayList<>();
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        stmt = con.prepareStatement("SELECT * FROM NhanVien WHERE maNV LIKE ?");
	        stmt.setString(1, "%" + maNV + "%");
	        rs = stmt.executeQuery();
	        while (rs.next()) {
	            String maNV_ = rs.getString(1);
	            String hoTen = rs.getString(2);
	            String username = rs.getString(3);
	            String password = rs.getString(4);
	            String diaChi = rs.getString(5);
	            String sdt = rs.getString(6);
	            NhanVien nv = new NhanVien(maNV_, hoTen, username, password, diaChi, sdt);
	            result.add(nv);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}

  
    
}
