package gui;

import dao.HoaDon_DAO;
import entity.HoaDon;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class QuanLyHoaDon extends JFrame implements ActionListener{
    private JTable tableHoaDon;
    private DefaultTableModel tableModel;
    private JButton btnTrangChu;
    private JButton btnXoaHoaDon;
    private HoaDon_DAO hoaDonDAO;

   
    public QuanLyHoaDon() {
        hoaDonDAO = new HoaDon_DAO();
        setLayout(new BorderLayout());
        
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Quản Lý Hóa Đơn");
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        
        String[] columnNames = {"Mã Hóa Đơn", "Mã Khách Hàng", "Mã Nhân Viên", "Ngày Vào", "Trạng Thái", "Hình Thức Thanh Toán", "Tổng Tiền"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableHoaDon = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableHoaDon);
        add(scrollPane, BorderLayout.CENTER);

       
        JPanel panelButtons = new JPanel();
        btnTrangChu = new JButton("Trang Chủ");
        btnXoaHoaDon = new JButton("Xóa Hóa Đơn");
        panelButtons.add(btnTrangChu);
        panelButtons.add(btnXoaHoaDon);
        add(panelButtons, BorderLayout.SOUTH);
         
        btnTrangChu.addActionListener(this);
        btnXoaHoaDon.addActionListener(this);
        
        loadHoaDonData();

        
    }

    // Hàm load dữ liệu từ cơ sở dữ liệu và hiển thị lên bảng
    private void loadHoaDonData() {
        try {
            List<HoaDon> hoaDonList = hoaDonDAO.getAll();
            tableModel.setRowCount(0); // Xóa các dòng cũ trong bảng

            // Thêm từng hóa đơn vào bảng
            for (HoaDon hoaDon : hoaDonList) {
                tableModel.addRow(new Object[] {
                    hoaDon.getMaHoaDon(),
                    hoaDon.getKhachHang().getMaKhachHang(),
                    hoaDon.getNhanVien().getMaNV(),
                    hoaDon.getNgayVao(),
                    hoaDon.getTrangThai().getName(),
                    hoaDon.getHinhThucThanhToan().getName(),
                    hoaDon.getTongTien()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu hóa đơn: " + e.getMessage());
        }
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnTrangChu) {
			dispose(); // Đóng cửa sổ hiện tại
            new TrangChu(); // Mở lại trang chủ
        }
		if (e.getSource() == btnXoaHoaDon) {
			int selectedRow = tableHoaDon.getSelectedRow();
            if (selectedRow != -1) {
                String maHoaDon = tableModel.getValueAt(selectedRow, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa hóa đơn này?", "Xóa Hóa Đơn", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        hoaDonDAO.deleteById(maHoaDon);
                        JOptionPane.showMessageDialog(this, "Xóa hóa đơn thành công.");
                        // Refresh lại dữ liệu sau khi xóa
                        loadHoaDonData();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi xóa hóa đơn: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để xóa.");
            }
        }
	}

    

   

   
//    public static void main(String[] args) {
//       
//        new QuanLyHoaDon().setVisible(true);
//    }
}
