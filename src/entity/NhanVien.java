package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NhanVien {
    private String maNV, hoTen, username, password, diaChi;
    private String sdt;
    private String role;
    


	public String getRole() {
		return role;
	}



	public void setRole(String role) {
		this.role = role;
	}



	public NhanVien(String maNV, String hoTen, String username, String password, String diaChi, String sdt) {
		this.maNV = maNV;
		this.hoTen = hoTen;
		this.username = username;
		this.password = password;
		this.diaChi = diaChi;
		this.sdt = sdt;
	}



	public NhanVien(String username, String password, String hoTen) {
        this.username = username;
        this.password = password;
        this.hoTen = hoTen;
    }
    
    

    public NhanVien(String maNV) {
		
		this.maNV = maNV;
	}



	public NhanVien(String username, String password) {
		
		this.username = username;
		this.password = password;
	}

	public NhanVien() {
		
	}

	public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

 
	public String getSdt() {
		return sdt;
	}



	public void setSdt(String sdt) {
		this.sdt = sdt;
	}



	public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
     
     public static NhanVien getFromResultSet(ResultSet rs) throws SQLException
     {
         String userName = rs.getNString("username");
         String password = rs.getNString("password");
//         String hoTen = rs.getNString("hoTen");
         NhanVien nhanVien = new NhanVien(userName, password);
         return nhanVien;
         
     }
     
}