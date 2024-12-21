package gui;

import entity.Ban;
import entity.TrangThaiBan;
import connectDB.ConnectDB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.Ban_DAO;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuanLyBan extends JFrame implements ActionListener, MouseListener {
    private Ban_DAO b_dao;
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtMaBan, txtSoLuong;
    private JComboBox<String> cboTrangThai;
    private JButton btnThem, btnXoa, btnSua, btnback;

    public QuanLyBan() {
        ConnectDB.getInstance().connect();
        b_dao = new Ban_DAO();

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Quản Lý Bàn");
        setSize(800, 600);
        setLocationRelativeTo(null);

       
        String[] columnNames = {"Mã Bàn", "Số Lượng Chỗ Ngồi", "Trạng Thái"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

       
        txtMaBan = new JTextField(10);
        txtSoLuong = new JTextField(10);
        
        cboTrangThai = new JComboBox<>(new String[] {"Trống", "Đã đặt"});

        btnThem = new JButton("Thêm Bàn");
        btnXoa = new JButton("Xóa Bàn");
        btnSua = new JButton("Chỉnh sửa");
        btnback = new JButton("Trang chủ");

        btnThem.addActionListener(this);
        btnXoa.addActionListener(this);
        btnSua.addActionListener(this);
        btnback.addActionListener(this);

        
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Mã Bàn:"));
        inputPanel.add(txtMaBan);
        inputPanel.add(new JLabel("Số Lượng Chỗ Ngồi:"));
        inputPanel.add(txtSoLuong);
        inputPanel.add(new JLabel("Trạng Thái:"));
        inputPanel.add(cboTrangThai);

        JPanel inputPanel1 = new JPanel(new GridLayout(1, 4));
        inputPanel1.add(btnThem);
        inputPanel1.add(btnXoa);
        inputPanel1.add(btnSua);
        inputPanel1.add(btnback);

        table.addMouseListener(this);

        add(scrollPane, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(inputPanel1, BorderLayout.SOUTH);

        DocDuLieuDatabaseVaoTable();

        setVisible(true);
    }

    public void themBan() {
        String maBan = txtMaBan.getText();
        int soLuong = Integer.parseInt(txtSoLuong.getText());
        String trangThaiStr = (String) cboTrangThai.getSelectedItem();
        TrangThaiBan trangThai = TrangThaiBan.getByName(trangThaiStr);

        Ban b = new Ban(maBan, soLuong, trangThai);

        if (b_dao.themBan(b)) {
            model.addRow(new Object[] {
                b.getMaBan(), b.getSoChoNgoi(), b.getTrangThai().getName()
            });
            xoaTrang();
            JOptionPane.showMessageDialog(this, "Thêm bàn thành công.");
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm bàn.");
        }
    }



    public void xoaBan() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String maBan = (String) table.getValueAt(row, 0);
            if (b_dao.xoaBan(maBan)) {
                model.removeRow(row);
                xoaTrang();
                JOptionPane.showMessageDialog(this, "Xóa bàn thành công.");
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa bàn.");
            }
        }
    }



    public void suaBan() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một bàn để sửa.");
            return;
        }

        String ma = txtMaBan.getText().trim();
        String sl = txtSoLuong.getText().trim();
        String trangThaiStr = (String) cboTrangThai.getSelectedItem();
        TrangThaiBan trangThai = TrangThaiBan.getByName(trangThaiStr);

        if (ma.isEmpty() || sl.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.");
            return;
        }

        int soLuong;
        try {
            soLuong = Integer.parseInt(sl);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng khách phải là một số hợp lệ.");
            return;
        }

        Ban b = new Ban(ma, soLuong, trangThai);

        if (b_dao.update(b)) {
            model.setValueAt(soLuong, row, 1);
            model.setValueAt(trangThai.getName(), row, 2);
            JOptionPane.showMessageDialog(this, "Cập nhật bàn thành công.");
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật bàn thất bại.");
        }
    }

    public void xoaTrang() {
        txtMaBan.setText("");
        txtSoLuong.setText("");
        cboTrangThai.setSelectedIndex(0);
    }

    private void DocDuLieuDatabaseVaoTable() {
        List<Ban> list = b_dao.getallBan();
        for (Ban s : list) {
            String[] rowData = {s.getMaBan(), String.valueOf(s.getSoChoNgoi()), s.getTrangThai().getName()};
            model.addRow(rowData);
        }
        table.setModel(model);
    }


    public static void main(String[] args) {
        new QuanLyBan();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnThem)) {
            themBan();
        }
        if (o.equals(btnXoa)) {
            xoaBan();
        }
        if (o.equals(btnSua)) {
            suaBan();
        }
        if (o.equals(btnback)) {
            dispose(); // Đóng cửa sổ hiện tại
            new TrangChu(); // Mở lại trang chủ
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = table.getSelectedRow();
        txtMaBan.setText(model.getValueAt(row, 0).toString());
        txtSoLuong.setText(model.getValueAt(row, 1).toString());
        cboTrangThai.setSelectedItem(model.getValueAt(row, 2).toString());
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
