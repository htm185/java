package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.Ban;
import entity.TrangThaiBan;

public class Ban_DAO {
    ArrayList<Ban> dsBan;
    Ban b;

    public Ban_DAO() {
        dsBan = new ArrayList<>();
        b = new Ban();
    }

    public ArrayList<Ban> getallBan() {
        ArrayList<Ban> dsBan = new ArrayList<>();
        try {
            Connection con = ConnectDB.getInstance().getConnection();
            String sql = "SELECT maBan, soChoNgoi, trangThai FROM Ban";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            while (rs.next()) {
                Ban b = Ban.getFromResultSet(rs); 
                dsBan.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return dsBan;
    }



    // Thêm bàn
    public boolean themBan(Ban b) {
        Connection con = ConnectDB.getConnection(); 
        PreparedStatement stmt = null;
        int n = 0;
        try {
            stmt = con.prepareStatement("INSERT INTO Ban (maBan, soChoNgoi, trangThai) VALUES (?, ?, ?)");
            stmt.setString(1, b.getMaBan());
            stmt.setInt(2, b.getSoChoNgoi());
            stmt.setString(3, b.getTrangThai().getName());

            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return n > 0; 
    }





    // Xóa bàn
    public boolean xoaBan(String maBan) {
        Connection con = ConnectDB.getConnection(); 
        PreparedStatement stmt = null;
        int n = 0;
        try {
            stmt = con.prepareStatement("DELETE FROM Ban WHERE maBan = ?");
            stmt.setString(1, maBan);
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } 

        return n > 0; 
    }




    // Cập nhật bàn
    public boolean update(Ban b) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        int n = 0;
        try {
            stmt = con.prepareStatement("UPDATE Ban SET soChoNgoi = ?, trangThai = ? WHERE maBan = ?");
            stmt.setInt(1, b.getSoChoNgoi());
            stmt.setString(2, b.getTrangThai().getName());

            stmt.setString(3, b.getMaBan());

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
