package gui;

import entity.DatBan;
import entity.KhachHang;
import entity.ThucUong;
import entity.Ban;
import connectDB.ConnectDB;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dao.DatBan_DAO;
import dao.ThucDon_DAO;
import entity.DatBan;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QuanLyDatBan extends JFrame  implements ActionListener, MouseListener{
	private DatBan_DAO db_dao;
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtMaBan, txtKhachHang, txtGhiChu;
   
	private JButton btnThem;
	private JButton btnXoa;
	private JButton btnSua;
	private JButton btnback;

    public QuanLyDatBan() {
    	ConnectDB.getInstance().connect();
		db_dao = new DatBan_DAO();
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Quản Lý Đặt Bàn");
        setSize(800, 600);
        setLocationRelativeTo(null);
      
        String[] columnNames = {"Mã Bàn",  "Khách Hàng", "Ghi Chú"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        
 
        
       
        txtMaBan = new JTextField(10);
        txtKhachHang = new JTextField(10);
        txtGhiChu = new JTextField(20);
        
        btnThem = new JButton("Thêm Đặt Bàn");
        btnXoa = new JButton("Xóa Đặt Bàn");
        btnSua = new JButton("Chỉnh sửa");
        btnback = new JButton("Trang chủ");
        
        btnThem.addActionListener(this);
        btnXoa.addActionListener(this);
        btnSua.addActionListener(this);
        btnback.addActionListener(this);
        
       
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Mã Bàn:"));
        inputPanel.add(txtMaBan);
        inputPanel.add(new JLabel("Mã Khách Hàng:"));
        inputPanel.add(txtKhachHang);
        inputPanel.add(new JLabel("Ghi Chú:"));
        inputPanel.add(txtGhiChu);
        JPanel inputPanel1 = new JPanel();
        inputPanel1.setLayout(new GridLayout(1, 4));
        inputPanel1.add(btnThem);
        inputPanel1.add(btnXoa);
        inputPanel1.add(btnSua);
        inputPanel1.add(btnback);
        
        table.addMouseListener(this);
   

        
        add(scrollPane, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(inputPanel1, BorderLayout.SOUTH);
//     
        
        
        
        DocDuLieuDatabaseVaoTable();
        
        setVisible(true);
    }

    private void themDatBan() {
        String maBan = txtMaBan.getText();
        String maKhachHang = txtKhachHang.getText();
        String ghiChu = txtGhiChu.getText();

        KhachHang khachHang = new KhachHang(maKhachHang); 
        DatBan datBan = new DatBan(maBan, khachHang, ghiChu);

        try {
            // Kiểm tra xem mã bàn có tồn tại trong cơ sở dữ liệu không
            Connection con = ConnectDB.getInstance().getConnection();
            String sqlCheckBan = "SELECT * FROM Ban WHERE maBan = ?";
            PreparedStatement stmtCheckBan = con.prepareStatement(sqlCheckBan);
            stmtCheckBan.setString(1, maBan);
            ResultSet rsCheckBan = stmtCheckBan.executeQuery();

            if (!rsCheckBan.next()) {
                // Nếu mã bàn không tồn tại, hiển thị thông báo lỗi và không thêm dòng vào bảng
                JOptionPane.showMessageDialog(this, "Mã bàn không tồn tại: " + maBan);
                return;  // Dừng hàm và không thêm vào bảng
            }

            // Kiểm tra xem mã khách hàng có tồn tại trong cơ sở dữ liệu không
            String sqlCheckKhachHang = "SELECT * FROM KhachHang WHERE maKhachHang = ?";
            PreparedStatement stmtCheckKhachHang = con.prepareStatement(sqlCheckKhachHang);
            stmtCheckKhachHang.setString(1, maKhachHang);
            ResultSet rsCheckKhachHang = stmtCheckKhachHang.executeQuery();

            if (!rsCheckKhachHang.next()) {
                // Nếu mã khách hàng không hợp lệ, hiển thị thông báo lỗi và không thêm dòng vào bảng
                JOptionPane.showMessageDialog(this, "Mã khách hàng không hợp lệ: " + maKhachHang);
                return;  // Dừng hàm và không thêm vào bảng
            }

            // Thực hiện thêm đặt bàn vào cơ sở dữ liệu
            if (db_dao.datBan(datBan)) {
                // Nếu thêm vào database thành công, thì thêm vào JTable
                model.addRow(new Object[] {
                    datBan.getMaBan(), datBan.getKhachHang().getMaKhachHang(), datBan.getGhiChu()
                });
                xoaTrang();  // Xóa thông tin nhập liệu
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi thêm đặt bàn.");
        }
    }

    
    public void xoaDatBan() {
		int row = table.getSelectedRow();
		if(row >= 0) {
			String madb = (String) table.getValueAt(row, 0);
			if(db_dao.xoaDatBan(madb)) {
				model.removeRow(row);
				xoaTrang();
			}
		}
	}
    public void suaDB() {
	    int row = table.getSelectedRow();
	    
	    // Kiểm tra xem có dòng nào được chọn không
	    if (row < 0) {
	        JOptionPane.showMessageDialog(this, "Vui lòng chọn một đặt bàn để sửa.");
	        return;
	    }

	    String ma = txtMaBan.getText().trim();
	    String kh = txtKhachHang.getText().trim();
	    String gc = txtGhiChu.getText().trim();
	    KhachHang khachhang = new KhachHang(kh);

	    
	    if (ma.isEmpty() || kh.isEmpty() || gc.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.");
	        return;
	    }

	   

	    
	   DatBan db = new DatBan(ma, khachhang, gc);

	    // Cập nhật trong DAO
	    if (db_dao.update(db)) {
	        table.setValueAt(kh, row, 1);  
	        table.setValueAt(gc, row, 2);
	        JOptionPane.showMessageDialog(this, "Cập nhật đặt bàn thành công.");
	    } else {
	        JOptionPane.showMessageDialog(this, "Cập nhật đặt bàn thất bại.");
	    }
	}
    public void xoaTrang() {
    	
        txtMaBan.setText("");
       
        txtKhachHang.setText("");
        txtGhiChu.setText("");
	}

    private void DocDuLieuDatabaseVaoTable() {
		DatBan_DAO ds = new DatBan_DAO();
		List<DatBan> list = ds.getallDatBan();
		for(DatBan s:list) {
			String[] rowData = {s.getMaBan(), s.getKhachHang().getMaKhachHang(), s.getGhiChu()};
			model.addRow(rowData);
		}
		table.setModel(model);
	}

    public static void main(String[] args) {
        new QuanLyDatBan();
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(btnThem)) {
			themDatBan();
		}
		if(o.equals(btnXoa)) {
			xoaDatBan();
		}
		if(o.equals(btnSua)) {
			suaDB();
		}
		if(o.equals(btnback)) {
			dispose(); // Đóng cửa sổ hiện tại
            new TrangChu(); // Mở lại trang chủ
		}
		

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = table.getSelectedRow();
		txtMaBan.setText(model.getValueAt(row, 0).toString());
		
		txtKhachHang.setText(model.getValueAt(row, 1).toString());
		txtGhiChu.setText(model.getValueAt(row, 2).toString());
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

