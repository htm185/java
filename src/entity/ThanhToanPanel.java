package entity;

import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import gui.DatMon;

import java.awt.*;
import javax.swing.*;

import connectDB.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.text.DecimalFormat;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ThanhToanPanel extends JPanel {
    private JTextArea TextArea;
    private JButton btnThanhToan;
    private List<String> thucUongList;
    private List<Integer> soLuongList;
    private List<String> maBanList;
    private List<String> maNVList;
    private double totalAmount;
    private String ngayVao;
    private String hinhThucThanhToan;


    public ThanhToanPanel(List<String> thucUongList, List<Integer> soLuongList, List<String> maBanList, 
                          List<String> maNVList, double totalAmount, String ngayVao, String hinhThucThanhToan) {
        this.thucUongList = thucUongList;
        this.soLuongList = soLuongList;
        this.maBanList = maBanList;
        this.maNVList = maNVList;
        this.totalAmount = totalAmount;
        this.ngayVao = ngayVao;
        this.hinhThucThanhToan = hinhThucThanhToan;

        setLayout(new BorderLayout());

        TextArea = new JTextArea(20, 40);  
        TextArea.setEditable(false);
        TextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));  
        JScrollPane scrollPane = new JScrollPane(TextArea);
        add(scrollPane, BorderLayout.CENTER);

        btnThanhToan = new JButton("In hóa đơn");
        btnThanhToan.addActionListener(e -> {
            try {
                thanhToan();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        JPanel panel = new JPanel();
        panel.add(btnThanhToan);
        add(panel, BorderLayout.SOUTH);

        hienThiHoaDon();
    }


    private void hienThiHoaDon() {
        StringBuilder invoice = new StringBuilder();
        DecimalFormat currencyFormat = new DecimalFormat("#,### VNĐ");
        DatMon dm = new DatMon();

        invoice.append("*************** HÓA ĐƠN QUÁN CÀ PHÊ ***************\n\n");
        invoice.append("Ngày vào       : ").append(ngayVao).append("\n");
        invoice.append("Hình thức thanh toán : ").append(hinhThucThanhToan).append("\n");
        invoice.append("--------------------------------------------------\n");
        invoice.append(String.format("%-15s %-10s %-10s %-10s\n", "Thức uống", "Số lượng", "Đơn giá", "Tổng tiền"));

        double grandTotal = 0.0;
        for (int i = 0; i < thucUongList.size(); i++) {
            String maTU = thucUongList.get(i);
            int quantity = soLuongList.get(i);
            double price = dm.getDrinkPrice(maTU);
            double total = price * quantity;

            invoice.append(String.format("%-15s %-10d %-10s %-10s\n", maTU, quantity, currencyFormat.format(price), currencyFormat.format(total)));

            grandTotal += total;
        }

        invoice.append("--------------------------------------------------\n");
        invoice.append(String.format("Tổng tiền : %-10s\n", currencyFormat.format(grandTotal)));
        invoice.append("**************************************************\n");

        TextArea.setText(invoice.toString());  
    }

    

   


	private void thanhToan() throws SQLException {
    	try {
		        HoaDon_DAO hoaDonDAO = new HoaDon_DAO();
		        int totalHoaDon = hoaDonDAO.getAll().size();
		        
		        String maHoaDon = String.format("HD%03d", totalHoaDon + 1);
	
		        
		        
		        KhachHang_DAO khachHangDAO = new KhachHang_DAO();
		        int totalKhachHang = khachHangDAO.getAllKhachHang().size();
		        String maKH = String.format("KH%03d", totalKhachHang + 1);
		        KhachHang khachHang = new KhachHang(maKH);
		        khachHangDAO.themKhachHang(khachHang);
		        
		        NhanVien nhanVien = new NhanVien(maNVList.get(0));
		        Ban ban = new Ban(maBanList.get(0));
		        TrangThaiThanhToan trangThaiThanhToan = TrangThaiThanhToan.PAID;
		       
		        HinhThucThanhToan hinhThucThanhToan = HinhThucThanhToan.getByName(this.hinhThucThanhToan);
		        
		        
		        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		        Date utilDate = formatter.parse(this.ngayVao);
		        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		        
		        HoaDon hoaDon = new HoaDon(maHoaDon, khachHang, nhanVien, sqlDate, trangThaiThanhToan, hinhThucThanhToan, ban, this.totalAmount);
		        		
		        String result = hoaDonDAO.save(hoaDon);
//		        System.out.println("Kết quả save: " + result);
		        if (result != null) {
		        	hoaDonDAO.updateTrangThaiBan(ban.getMaBan(), "Đã đặt");
		            JOptionPane.showMessageDialog(null, "Lưu Hóa Đơn thành công.");
		        }

		        
//		        if (hoaDonDAO.save(hoaDon) != null) {
//		        	JOptionPane.showMessageDialog(null, "Lưu Hóa Đơn thành công.");
//		        }
	    	} catch (ParseException e) {
	            JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ!.");
	            e.printStackTrace();
	    	}
        
    }

	
}
