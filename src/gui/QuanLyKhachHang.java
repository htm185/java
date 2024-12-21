package gui;

import entity.KhachHang;
import main.main;
import dao.KhachHang_DAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import connectDB.ConnectDB;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class QuanLyKhachHang extends JFrame implements ActionListener, MouseListener {

    private KhachHang_DAO kh_dao;
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtMaKhachHang, txtTenKhachHang, txtSoDienThoai;
    private JButton btnThem, btnXoa, btnSua, btnBack;

    public QuanLyKhachHang() {
    	ConnectDB.getInstance().connect();
        kh_dao = new KhachHang_DAO();

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Quản Lý Khách Hàng");
        setSize(800, 600);
        setLocationRelativeTo(null);

        
        String[] columnNames = {"Mã Khách Hàng", "Tên Khách Hàng", "Số Điện Thoại"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        
        txtMaKhachHang = new JTextField(10);
        txtTenKhachHang = new JTextField(10);
        txtSoDienThoai = new JTextField(10);

        btnThem = new JButton("Thêm Khách Hàng");
        btnXoa = new JButton("Xóa Khách Hàng");
        btnSua = new JButton("Chỉnh sửa");
        btnBack = new JButton("Trang Chủ");

        btnThem.addActionListener(this);
        btnXoa.addActionListener(this);
        btnSua.addActionListener(this);
        btnBack.addActionListener(this);

        // Panel chứa thông tin nhập liệu
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Mã Khách Hàng:"));
        inputPanel.add(txtMaKhachHang);
        inputPanel.add(new JLabel("Tên Khách Hàng:"));
        inputPanel.add(txtTenKhachHang);
        inputPanel.add(new JLabel("Số Điện Thoại:"));
        inputPanel.add(txtSoDienThoai);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4));
        buttonPanel.add(btnThem);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnBack);

       
        table.addMouseListener(this);

        
        add(scrollPane, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

       
        docDuLieuDatabaseVaoTable();
    }

    
    private void themKhachHang() {
        String maKhachHang = txtMaKhachHang.getText();
        String tenKhachHang = txtTenKhachHang.getText();
        String soDienThoai = txtSoDienThoai.getText();

        KhachHang khachHang = new KhachHang(maKhachHang, tenKhachHang, soDienThoai);

        if (kh_dao.themKhachHang(khachHang)) {
            model.addRow(new Object[] {
                khachHang.getMaKhachHang(), khachHang.getTenKhachHang(), khachHang.getSoDienThoai()
            });
            xoaTrang();
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công.");
        } else {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi thêm khách hàng.");
        }
    }

  
    private void suaKhachHang() {
        int row = table.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để sửa.");
            return;
        }

        String maKhachHang = txtMaKhachHang.getText().trim();
        String tenKhachHang = txtTenKhachHang.getText().trim();
        String soDienThoai = txtSoDienThoai.getText().trim();

        KhachHang khachHang = new KhachHang(maKhachHang, tenKhachHang, soDienThoai);

        if (kh_dao.update(khachHang)) {
            table.setValueAt(tenKhachHang, row, 1);
            table.setValueAt(soDienThoai, row, 2);
            JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công.");
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thất bại.");
        }
    }

    
    private void xoaKhachHang() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String maKhachHang = (String) table.getValueAt(row, 0);
            if (kh_dao.xoaKhachHang(maKhachHang)) {
                model.removeRow(row);
                xoaTrang();
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công.");
            }
	        else {
	            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi xóa khách hàng.");
	        }
        }
    }


    private void xoaTrang() {
        txtMaKhachHang.setText("");
        txtTenKhachHang.setText("");
        txtSoDienThoai.setText("");
    }
  // Đọc dữ liệu từ database và hiển thị vào bảng
    private void docDuLieuDatabaseVaoTable() {
        List<KhachHang> list = kh_dao.getAllKhachHang();
        for (KhachHang kh : list) {
            String[] rowData = {kh.getMaKhachHang(), kh.getTenKhachHang(), kh.getSoDienThoai()};
            model.addRow(rowData);
        }
        table.setModel(model);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnThem)) {
            themKhachHang();
        } else if (o.equals(btnXoa)) {
            xoaKhachHang();
        } else if (o.equals(btnSua)) {
            suaKhachHang();
        } else if (o.equals(btnBack)) {
            dispose(); 
            new TrangChu();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = table.getSelectedRow();
        txtMaKhachHang.setText((String) table.getValueAt(row, 0));
        txtTenKhachHang.setText((String) table.getValueAt(row, 1));
        txtSoDienThoai.setText((String) table.getValueAt(row, 2));
    }

    public static void main(String[] args) {
		new QuanLyKhachHang().setVisible(true);
	}
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
