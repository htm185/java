package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import connectDB.ConnectDB;
import dao.NhanVien_DAO;
import entity.NhanVien;



public class QuanLyNhanVien extends JFrame implements ActionListener, MouseListener{
	private NhanVien_DAO nv_dao;
	private DefaultTableModel tableModel;
	private JTable table;
	private JTextField txtTim;
	private JButton btnThem;
	private JButton btnSua;
	private JButton btnXoa;
	private JButton btnLuu;
	private JButton btnTim;
	
	private JButton btnback;
	private JTextField txtMaNV;
	private JTextField txtHoTen;
	private JTextField txtPassWord;
	private JTextField txtUsername;
	private JTextField txtDiaChi;
	private JTextField txtSDT;
	private JLabel lblMaNV;
	private JLabel lblHoTen;
	private JLabel lblUsername;
	private JLabel lblPassWord;
	private JLabel lblDiaChi;
	private JLabel lblSDT;

	public QuanLyNhanVien() {
		ConnectDB.getInstance().connect();
		nv_dao = new NhanVien_DAO();

		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Quản Lý Nhân Viên");
        setSize(800, 600);
        setLocationRelativeTo(null);

        String[] columnNames = {"Mã nhân viên" , "Họ tên" ,"Username", "Password", "Địa chỉ", "Số điện thoại"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        
        txtMaNV = new JTextField(10);
        txtHoTen = new JTextField(10);
        txtUsername = new JTextField(10);
        txtPassWord = new JTextField(10);
        txtDiaChi = new JTextField(10);
        txtSDT = new JTextField(10);
       
        
        
        
        
        
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
        inputPanel.setLayout(new GridLayout(7, 2));
        inputPanel.add(lblMaNV = new JLabel("Mã nhân viên: "));
        inputPanel.add(txtMaNV);
        inputPanel.add(lblHoTen = new JLabel(" Họ tên: "));
        inputPanel.add(txtHoTen);
        inputPanel.add(lblUsername = new JLabel("Username: "));
        inputPanel.add(txtUsername);
        inputPanel.add(lblPassWord = new JLabel("Password: "));
        inputPanel.add(txtPassWord);
        inputPanel.add(lblDiaChi = new JLabel("Địa chỉ: "));
        inputPanel.add(txtDiaChi);
        inputPanel.add(lblSDT = new JLabel("Số điện thoại: "));
        inputPanel.add(txtSDT);
        
        
        
        JPanel inputPanel1 = new JPanel();
        inputPanel1.setLayout(new GridLayout(1, 6));
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
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int row = table.getSelectedRow();
		txtMaNV.setText(tableModel.getValueAt(row, 0).toString());
		txtHoTen.setText(tableModel.getValueAt(row, 1).toString());
		txtUsername.setText(tableModel.getValueAt(row, 2).toString());
		txtPassWord.setText(tableModel.getValueAt(row, 3).toString());
		txtDiaChi.setText(tableModel.getValueAt(row, 4).toString());
		txtSDT.setText(tableModel.getValueAt(row, 5).toString());
	

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
	    Object o = e.getSource();
	    if(o.equals(btnTim)) {
	        timNhanVien();
	    }
	    if(o.equals(btnThem)) {
	        themNhanVien();
	    }
	    if(o.equals(btnSua)) {
	        suaNhanVien();
	    }
	    if(o.equals(btnXoa)) {
	        xoaNhanVien();
	    }
	    if(o.equals(btnTim)) {
	    	timNhanVien();
	    }
	    if(o.equals(btnback)) {
	        dispose(); 
	        new TrangChu(); // Mở lại trang chủ
	    }
	}

	public void themNhanVien() {
	    String maNV = txtMaNV.getText();
	    String hoTen = txtHoTen.getText();
	    String username = txtUsername.getText();
	    String password = txtPassWord.getText();
	    String diaChi = txtDiaChi.getText();
	    String sdt = txtSDT.getText();
	    
	    if (maNV.isEmpty() || hoTen.isEmpty() || username.isEmpty() || password.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
	        return;
	    }
	    
	    NhanVien nhanVien = new NhanVien(maNV, hoTen, username, password, diaChi, sdt);
	    
	    if (nv_dao.create(nhanVien)) {
	        tableModel.addRow(new Object[] {maNV, hoTen, username, password, diaChi, sdt});
	        JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
	    } else {
	        JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại!");
	    }
	}

	public void suaNhanVien() {
	    String maNV = txtMaNV.getText();
	    String hoTen = txtHoTen.getText();
	    String username = txtUsername.getText();
	    String password = txtPassWord.getText();
	    String diaChi = txtDiaChi.getText();
	    String sdt = txtSDT.getText();

	    NhanVien nhanVien = new NhanVien(maNV, hoTen, username, password, diaChi, sdt);

	    if (nv_dao.update(nhanVien)) {
	        int row = table.getSelectedRow();
	        tableModel.setValueAt(maNV, row, 0);
	        tableModel.setValueAt(hoTen, row, 1);
	        tableModel.setValueAt(username, row, 2);
	        tableModel.setValueAt(password, row, 3);
	        tableModel.setValueAt(diaChi, row, 4);
	        tableModel.setValueAt(sdt, row, 5);
	        JOptionPane.showMessageDialog(this, "Sửa thông tin nhân viên thành công!");
	    } else {
	        JOptionPane.showMessageDialog(this, "Sửa thông tin nhân viên thất bại!");
	    }
	}

	public void xoaNhanVien() {
	    int row = table.getSelectedRow();
	    if (row != -1) {
	        String maNV = tableModel.getValueAt(row, 0).toString();
	        if (nv_dao.delete(maNV)) {
	            tableModel.removeRow(row);
	            JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!");
	        } else {
	            JOptionPane.showMessageDialog(this, "Xóa nhân viên thất bại!");
	        }
	    } else {
	        JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa!");
	    }
	}

	public void timNhanVien() {
	    String maNV = txtTim.getText();
	    ArrayList<NhanVien> result = nv_dao.searchByMaNV(maNV);
	    tableModel.setRowCount(0);
	    for (NhanVien nv : result) {
	        tableModel.addRow(new Object[] {
	            nv.getMaNV(), nv.getHoTen(), nv.getUsername(), nv.getPassword(), nv.getDiaChi(), nv.getSdt()
	        });
	    }
	}
	
	private void DocDuLieuDatabaseVaoTable() {
        List<NhanVien> list = nv_dao.getallNhanVien();
        for (NhanVien nv : list) {
            String[] rowData = {nv.getMaNV(), nv.getHoTen(), nv.getUsername(),
                    nv.getPassword(), nv.getDiaChi(), nv.getSdt()};
            tableModel.addRow(rowData);
        }
        table.setModel(tableModel);
    }
	
	public static void main(String[] args) {
		new QuanLyNhanVien().setVisible(true);
	}

}