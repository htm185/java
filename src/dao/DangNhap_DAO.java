package dao;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.DangNhap;
import entity.NhanVien;
public class DangNhap_DAO {
	
	public DangNhap findByUsernameAndPassword1(String userName, String password) throws SQLException {
	    ConnectDB.getInstance().connect();
	    Connection con = ConnectDB.getInstance().getConnection();
	    
	    if (con == null) {
	        throw new SQLException("Kết nối database thất bại.");
	    }

	    String query = "SELECT * FROM Admin WHERE username = ? AND password = ?";
	    PreparedStatement ps = con.prepareStatement(query);
	    ps.setString(1, userName);
	    ps.setString(2, password);

	    ResultSet rs = ps.executeQuery();
	    if (rs.next()) {
	        DangNhap admin = DangNhap.getFromResultSet1(rs); 
	        return admin;
	    }
	    return null;
	}
	
	
}
