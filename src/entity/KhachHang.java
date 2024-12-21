
package entity;

import java.util.Objects;

public class KhachHang {
		private String maKhachHang;
	    private String tenKhachHang;
	    private String soDienThoai;

	    public KhachHang() {
	        
	    }

		public KhachHang(String maKhachHang, String tenKhachHang, String soDienThoai) {
			
			this.maKhachHang = maKhachHang;
			this.tenKhachHang = tenKhachHang;
			this.soDienThoai = soDienThoai;
		}

		public KhachHang(String maKhachHang) {
			
			this.maKhachHang = maKhachHang;
		}

		

		public String getMaKhachHang() {
			return maKhachHang;
		}

		public void setMaKhachHang(String maKhachHang) {
			this.maKhachHang = maKhachHang;
		}

		public String getTenKhachHang() {
			return tenKhachHang;
		}

		public void setTenKhachHang(String tenKhachHang) {
			this.tenKhachHang = tenKhachHang;
		}

		public String getSoDienThoai() {
			return soDienThoai;
		}

		public void setSoDienThoai(String soDienThoai) {
			this.soDienThoai = soDienThoai;
		}

		@Override
		public int hashCode() {
			return Objects.hash(maKhachHang);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			KhachHang other = (KhachHang) obj;
			return Objects.equals(maKhachHang, other.maKhachHang);
		}

	 
}
