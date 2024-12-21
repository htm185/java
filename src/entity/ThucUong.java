package entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import connectDB.ConnectDB;

public class ThucUong {
	private String maTU;
    private String tenTU;
    private double giaTU;
    private int soLuong;
    
    
	

	public ThucUong(String maTU, String tenTU, double giaTU, int soLuong) {
		this.maTU = maTU;
		this.tenTU = tenTU;
		this.giaTU = giaTU;
		this.soLuong = soLuong;
	}

	public ThucUong(String maTU) {
		
		this.maTU = maTU;
	}
	
	public ThucUong(String maTU, String tenTU) {
		
		this.maTU = maTU;
		this.tenTU = tenTU;
	}

	public ThucUong( double giaTU) {
		
		this.giaTU = giaTU;
		
	}
	
	public ThucUong() {
		
	}

	public String getMaTU() {
		return maTU;
	}

	public void setMaTU(String maTU) {
		this.maTU = maTU;
	}

	public String getTenTU() {
		return tenTU;
	}

	public void setTenTU(String tenTU) {
		this.tenTU = tenTU;
	}

	public double getGiaTU() {
		return giaTU;
	}

	public void setGiaTU(double giaTU) {
		this.giaTU = giaTU;
	}

	

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maTU);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ThucUong other = (ThucUong) obj;
		return Objects.equals(maTU, other.maTU);
	}
	public double getGiaThucUong(String maTU) throws SQLException {
		Connection con = ConnectDB.getInstance().getConnection();
		
	    String sql = "SELECT giaTU FROM ThucUong WHERE maTU = ?";
	    try (PreparedStatement stmt = con.prepareStatement(sql)) {
	        stmt.setString(1, maTU);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getDouble("giaTU");  // Lấy giá của thức uống
	        } 
	        
	        return 0;  // Nếu không tìm thấy thức uống, trả về 0
	        
	    }
	}

    
}
