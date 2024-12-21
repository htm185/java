package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import connectDB.ConnectDB;
import entity.ThucUong;
public class ThucDon_DAO {
	ArrayList<ThucUong> dstu;
	ThucUong tu;
	
	public ThucDon_DAO() {
		dstu = new ArrayList<ThucUong>();
		tu = new ThucUong();
	}
	public ArrayList<ThucUong> getallThucUong() {
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			String sql ="select * from ThucUong";
			Statement statement = (Statement) con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				String ma = rs.getString(1);
				String ten = rs.getString(2);
				int gia = rs.getInt(3);
				int soLuong = rs.getInt(4);
				ThucUong v = new ThucUong(ma, ten, gia, soLuong);
				dstu.add(v);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return dstu;
	}
	public boolean create(ThucUong tu) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("INSERT INTO ThucUong (maTU,tenTU, giaTU,soLuong) VALUES (?, ?, ?, ?)");
			stmt.setString(1, tu.getMaTU());
			stmt.setString(2, tu.getTenTU());
			stmt.setDouble(3, tu.getGiaTU());
			stmt.setInt(4, tu.getSoLuong());
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return n > 0;	
	}
	public boolean delete(String maTU) {
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;
	    try {
	        stmt = con.prepareStatement("DELETE FROM ThucUong WHERE maTU = ?");
	        stmt.setString(1, maTU);
	        n = stmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return n > 0;
	}
	public boolean update(ThucUong tu) {
	    ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;
	    try {
	        stmt = con.prepareStatement("update ThucUong set tenTU = ?, giaTU = ?, soLuong = ? where maTU = ?");
	        stmt.setString(1, tu.getTenTU());
	        stmt.setDouble(2, tu.getGiaTU());
	        stmt.setInt(3, tu.getSoLuong());
	        stmt.setString(4, tu.getMaTU()); 
	        n = stmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (stmt != null) stmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    return n > 0;
	}






}
