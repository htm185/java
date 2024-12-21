package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class DangNhap {
	 private String username, password;
	 private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public DangNhap(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public DangNhap() {
		
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

	@Override
	public int hashCode() {
		return Objects.hash(username);
	}
	 public static DangNhap getFromResultSet1(ResultSet rs) throws SQLException
     {
         String userName = rs.getNString("username");
         String password = rs.getNString("password");
//         String hoTen = rs.getNString("hoTen");
         DangNhap dangNhap = new DangNhap(userName, password);
         return dangNhap;
         
     }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DangNhap other = (DangNhap) obj;
		return Objects.equals(username, other.username);
	}
	
	 
}
