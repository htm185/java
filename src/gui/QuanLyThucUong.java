package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import connectDB.ConnectDB;
import dao.ThucDon_DAO;
import entity.ThucUong;
import main.main;

public class QuanLyThucUong extends JFrame implements ActionListener, MouseListener{
	private ThucDon_DAO td_dao;
	private DefaultTableModel tableModel;
	private JTable table;
	private JTextField txtTim;
	private JButton btnThem;
	private JButton btnSua;
	private JButton btnXoa;
	private JButton btnLuu;
	private JButton btnTim;
	private JLabel lblMaTU;
	private JTextField txtMaTU;
	private JLabel lblTen;
	private JTextField txtTen;
	private JLabel lblGia;
	private JTextField txtGia;
	private JTextField txtSL;
	private JLabel lblSL;
	private JButton btnback;

	public QuanLyThucUong() {
		ConnectDB.getInstance().connect();
		td_dao = new ThucDon_DAO();
		
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Quản Lý Thức Uống");
        setSize(800, 600);
        setLocationRelativeTo(null);



		 // Tạo bảng
        String[] columnNames = {"Mã thức uống" , "Tên thức uống" ,"Giá", "Số lượng"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        
     // Tạo các thành phần nhập liệu
        txtMaTU = new JTextField(10);
        txtTen = new JTextField(10);
        txtGia = new JTextField(10);
        txtSL = new JTextField(10);
        
        
        btnTim = new JButton("Tìm");
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLuu = new JButton("Lưu");
        btnback = new JButton("Trang chủ");

		
		btnThem.addActionListener(this);
		btnSua.addActionListener(this);
		btnXoa.addActionListener(this);
		btnLuu.addActionListener(this);
		btnTim.addActionListener(this);
		btnback.addActionListener(this);
		table.addMouseListener(this);
		
	
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));
        inputPanel.add(lblMaTU = new JLabel("Mã thức uống: "));
        inputPanel.add(txtMaTU);
        inputPanel.add(lblTen = new JLabel("Tên thức uống: "));
        inputPanel.add(txtTen);
        inputPanel.add(lblGia = new JLabel("Giá thức uống: "));
        inputPanel.add(txtGia);
        inputPanel.add(lblSL = new JLabel("Số lượng: "));
        inputPanel.add(txtSL);
        
        
        JPanel inputPanel1 = new JPanel();
        inputPanel1.setLayout(new GridLayout(1, 7));
        inputPanel1.add(new JLabel("Nhập mã số cần tìm: "));
        inputPanel1.add(txtTim = new JTextField(10));
        inputPanel1.add(btnTim);
        inputPanel1.add(btnThem);
        inputPanel1.add(btnSua);
        inputPanel1.add(btnXoa);
        inputPanel1.add(btnback);

		
        add(scrollPane, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(inputPanel1, BorderLayout.SOUTH);
		
		DocDuLieuDatabaseVaoTable();
		
	}
//	public static void main(String[] args) {
//		new QuanLyThucUong().setVisible(true);
//	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int row = table.getSelectedRow();
		txtMaTU.setText(tableModel.getValueAt(row, 0).toString());
		txtTen.setText(tableModel.getValueAt(row, 1).toString());
		txtGia.setText(tableModel.getValueAt(row, 2).toString());
		txtSL.setText(tableModel.getValueAt(row, 3).toString());

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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(btnTim)) {
			
			
		}
		if(o.equals(btnThem)) {
			luuTU();
		}
		if(o.equals(btnSua)) {
			suaTU();
		}
		if(o.equals(btnXoa)) {
			xoaTU();
		}
		if(o.equals(btnback)) {
			dispose(); // Đóng cửa sổ hiện tại
            new TrangChu(); // Mở lại trang chủ
		}

	}



	public void luuTU() {
		 String ma = txtMaTU.getText();
		    String ten = txtTen.getText();
		    double gia = Double.parseDouble(txtGia.getText());
		    int sl = Integer.parseInt(txtSL.getText());
		    ThucUong tu = new ThucUong(ma, ten, gia, sl);
		    try {
		    	td_dao.create(tu);  
		        tableModel.addRow(new Object[] {
		            tu.getMaTU(), tu.getTenTU(), tu.getGiaTU(), tu.getSoLuong()
		        });
		        xoaTrang();
		    } catch (Exception e) {
		        JOptionPane.showMessageDialog(this, "Trùng mã thức uống");
		    }
	
	}






	public void suaTU() {
	    int row = table.getSelectedRow();
	  
	    if (row < 0) {
	        JOptionPane.showMessageDialog(this, "Vui lòng chọn một thức uống để sửa.");
	        return;
	    }

	    String ma = txtMaTU.getText().trim();
	    String ten = txtTen.getText().trim();
	    String giaText = txtGia.getText().trim();
	    String sl = txtSL.getText().trim();

	    
	    if (ma.isEmpty() || ten.isEmpty() || giaText.isEmpty() || sl.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.");
	        return;
	    }

	    double gia;
	    try {
	        gia = Double.parseDouble(giaText);
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(this, "Giá phải là một số hợp lệ.");
	        return;
	    }
	    
	    int soLuong;
	    try {
	        soLuong = Integer.parseInt(sl);
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(this, "Số lượng phải là một số hợp lệ.");
	        return;
	    }
	    
	   
	    ThucUong tu = new ThucUong(ma, ten, gia, soLuong);

	  
	    if (td_dao.update(tu)) {
	        table.setValueAt(ten, row, 1);
	        table.setValueAt(String.valueOf(gia), row, 2);  
	        table.setValueAt(String.valueOf(soLuong), row, 3);
	        JOptionPane.showMessageDialog(this, "Cập nhật thức uống thành công.");
	    } else {
	        JOptionPane.showMessageDialog(this, "Cập nhật thức uống thất bại.");
	    }
	}

	public void xoaTU() {
		int row = table.getSelectedRow();
		if(row >= 0) {
			String matu = (String) table.getValueAt(row, 0);
			if(td_dao.delete(matu)) {
				tableModel.removeRow(row);
				xoaTrang();
			}
		}
	}




	public void xoaTrang() {
		txtMaTU.setText("");
		txtTen.setText("");
		txtGia.setText("");
		txtSL.setText("");
		txtMaTU.requestFocus();
	}

	private void DocDuLieuDatabaseVaoTable() {
		ThucDon_DAO ds = new ThucDon_DAO();
		List<ThucUong> list = ds.getallThucUong();
		for(ThucUong s:list) {
			String[] rowData = {s.getMaTU(), s.getTenTU(), s.getGiaTU()+"", s.getSoLuong()+""};
			tableModel.addRow(rowData);
		}
		table.setModel(tableModel);
	}


}
