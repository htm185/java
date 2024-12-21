package entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connectDB.ConnectDB;

public class Ban {
	private  String maBan;
    private int soChoNgoi;
    private TrangThaiBan trangThai;
   
    public Ban() {
    	
        trangThai = TrangThaiBan.FREE;
	}
    
	public Ban(String maBan) {
		
		this.maBan = maBan;
	}

	public Ban(String maBan, int soChoNgoi, TrangThaiBan trangThai) {
		
		this.maBan = maBan;
		this.soChoNgoi = soChoNgoi;
		this.trangThai = trangThai;
	}

	public String getMaBan() {
		return maBan;
	}

	public void setMaBan(String maBan) {
		this.maBan = maBan;
	}

	public int getSoChoNgoi() {
		return soChoNgoi;
	}

	public void setSoChoNgoi(int soChoNgoi) {
		this.soChoNgoi = soChoNgoi;
	}

	public TrangThaiBan getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(TrangThaiBan trangThai) {
		this.trangThai = trangThai;
	}
	
	public static Ban getBanByMa(String maBan) throws SQLException {
	    
		Connection con = ConnectDB.getInstance().getConnection();
	    String sql = "SELECT * FROM Ban WHERE maBan = ?";
	    try (PreparedStatement stmt = con.prepareStatement(sql)) {
	        stmt.setString(1, maBan);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return getFromResultSet(rs);  // Sử dụng phương thức đã có để tạo đối tượng Ban
	        } else {
	            return null;  // Không tìm thấy bàn
	        }
	    }
	}

	
	public static Ban getFromResultSet(ResultSet rs) throws SQLException  {
	    String maBan = rs.getNString("maBan");
	    int soChoNgoi = rs.getInt("soChoNgoi");
	    String trangThai = rs.getNString("trangThai");
	    TrangThaiBan status = TrangThaiBan.getByName(trangThai);

	    // In ra để kiểm tra
//	    System.out.println("Mã bàn: " + maBan + ", Số chỗ ngồi: " + soChoNgoi + ", Trạng thái: " + status);

	    return new Ban(maBan, soChoNgoi, status);
	}


	
    
}
