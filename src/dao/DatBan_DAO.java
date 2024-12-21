package dao;

import entity.Ban;
import entity.DatBan;
import entity.KhachHang;
import entity.ThucUong;
import connectDB.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DatBan_DAO extends JFrame{
	Ban ban;
    ArrayList<DatBan> dsDatBan;  
    ArrayList<Ban> dsBan;
    
    public DatBan_DAO() {
        ban = new Ban();
        dsDatBan = new ArrayList<DatBan>();
        dsBan = new ArrayList<Ban>();
    }
    
    public ArrayList<DatBan> getallDatBan() {
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			String sql ="select * from DatBan";
			Statement statement = (Statement) con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				String maBan = rs.getString(1);
				
				KhachHang kh = new KhachHang(rs.getString(2));
				String ghiChu = rs.getString(3);
				DatBan b = new DatBan(maBan, kh, ghiChu);
				dsDatBan.add(b);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return dsDatBan;
	}


    // Xóa đặt bàn
    public boolean xoaDatBan(String maBan) {
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;
	    try {
	        stmt = con.prepareStatement("DELETE FROM DatBan WHERE maBan = ?");
	        stmt.setString(1, maBan);
	        n = stmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return n > 0;
	}
    
   


 // Đặt bàn

    public boolean datBan(DatBan datBan) {
        try {
            Connection con = ConnectDB.getInstance().getConnection();
            dsDatBan = getallDatBan();

            // Kiểm tra xem mã bàn đã có trong danh sách đặt bàn chưa
            for (DatBan ban : dsDatBan) {
                if (ban.getMaBan().equals(datBan.getMaBan())) {
                    JOptionPane.showMessageDialog(this, "Đặt bàn không thành công: Mã bàn " + datBan.getMaBan() + " đã tồn tại.");
                    return false;
                }
            }

            // Kiểm tra xem mã bàn có tồn tại trong bảng Ban và có trạng thái "Trống" không
            String sqlCheckBan = "SELECT trangThai FROM Ban WHERE maBan = ?";
            PreparedStatement stmtCheckBan = con.prepareStatement(sqlCheckBan);
            stmtCheckBan.setString(1, datBan.getMaBan());
            ResultSet rsCheckBan = stmtCheckBan.executeQuery();

            if (rsCheckBan.next()) {
                String trangThai = rsCheckBan.getString("trangThai");
                if (!"Trống".equals(trangThai)) {
                    JOptionPane.showMessageDialog(this, "Bàn " + datBan.getMaBan() + " không có trạng thái 'Trống'. Vui lòng chọn bàn khác.");
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Mã bàn không tồn tại: " + datBan.getMaBan());
                return false;
            }

            // Kiểm tra xem mã khách hàng có tồn tại trong bảng KhachHang không
            String sqlCheckKhachHang = "SELECT * FROM KhachHang WHERE maKhachHang = ?";
            PreparedStatement stmtCheckKhachHang = con.prepareStatement(sqlCheckKhachHang);
            stmtCheckKhachHang.setString(1, datBan.getKhachHang().getMaKhachHang());
            ResultSet rsCheckKhachHang = stmtCheckKhachHang.executeQuery();

            if (!rsCheckKhachHang.next()) {
                JOptionPane.showMessageDialog(this, "Mã khách hàng không hợp lệ: " + datBan.getKhachHang().getMaKhachHang());
                return false;
            }

            // Nếu mã bàn và mã khách hàng hợp lệ, thêm vào bảng DatBan
            String sqlInsert = "INSERT INTO DatBan (maBan, maKhachHang, ghiChu) VALUES (?, ?, ?)";
            PreparedStatement stmtInsert = con.prepareStatement(sqlInsert);
            stmtInsert.setString(1, datBan.getMaBan());
            stmtInsert.setString(2, datBan.getKhachHang().getMaKhachHang());
            stmtInsert.setString(3, datBan.getGhiChu());

            int n = stmtInsert.executeUpdate();

            if (n > 0) {
                JOptionPane.showMessageDialog(this, "Đặt bàn thành công: " + datBan.getMaBan());
                dsDatBan.add(datBan);  // Thêm vào danh sách đặt bàn

                // Cập nhật trạng thái bàn thành "Đã đặt"
                String sqlUpdateBan = "UPDATE Ban SET trangThai = N'Đã đặt' WHERE maBan = ?";
                PreparedStatement stmtUpdateBan = con.prepareStatement(sqlUpdateBan);
                stmtUpdateBan.setString(1, datBan.getMaBan());
                int updateResult = stmtUpdateBan.executeUpdate();

                if (updateResult > 0) {
                    JOptionPane.showMessageDialog(this, "Cập nhật trạng thái bàn thành 'Đã đặt' thành công.");
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật trạng thái bàn.");
                }

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    
    public boolean update(DatBan db) {
	    ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;
	    try {
	        stmt = con.prepareStatement("update DatBan set  maKhachHang = ?, ghiChu = ? where maBan = ?");
	       
	        stmt.setString(1, db.getKhachHang().getMaKhachHang());
	        stmt.setString(2, db.getGhiChu());
	        stmt.setString(3, db.getMaBan()); 
	        n = stmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } 
	    
	    return n > 0;
	}


}


