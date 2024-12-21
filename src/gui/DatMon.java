
package gui;


import javax.swing.*;
import connectDB.ConnectDB;
import entity.ThanhToanPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatMon extends JFrame implements ActionListener, MouseListener{
    private JComboBox<String> cboDrink;
    private JTextField txtQuantity;
    private JLabel lblPrice;
    private JTextArea txtOrderDetails;
    private JTextField txtTotalAmount;
    private JButton btnAddOrder, btnCalculateTotal, btnPay;
    private List<String> thucUongList = new ArrayList<>(); 
    private List<Integer> soLuongList = new ArrayList<>(); 
    private List<String> maBanList = new ArrayList<>(); 
    private List<String> maNVList = new ArrayList<>(); 
    private double tongTien = 0.0; 
    private JComboBox<String> cboMaNV;
    private JComboBox<String> cboMaBan;
    private JTextField txtNgayVao;
    private JComboBox<String> cboHinhThucThanhToan;

    public DatMon() {
        setTitle("Order and Payment System");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        // Tạo các thành phần giao diện
        cboDrink = new JComboBox<>();
        txtQuantity = new JTextField(10);
        lblPrice = new JLabel("Giá: ");
        txtOrderDetails = new JTextArea(10, 30);
        txtOrderDetails.setEditable(false);
        cboMaBan = new JComboBox<>();
        cboMaNV = new JComboBox<>();
        txtTotalAmount = new JTextField(10);
        txtTotalAmount.setEditable(false);
        btnAddOrder = new JButton("Thêm vào đơn hàng");
        btnCalculateTotal = new JButton("Tính tổng tiền");
        btnPay = new JButton("Thanh toán");
        txtNgayVao = new JTextField(10);
        cboHinhThucThanhToan = new JComboBox<>(new String[]{"Tiền mặt", "Chuyển khoản"});


     // Thiết lập layout
        JPanel panelTop = new JPanel();
        panelTop.setLayout(new GridLayout(0, 1));
        panelTop.add(new JLabel("Chọn thức uống:"));
        panelTop.add(cboDrink);
        panelTop.add(lblPrice);
        panelTop.add(new JLabel("Số lượng:"));
        panelTop.add(txtQuantity);
        
        panelTop.add(new JLabel("Chọn bàn:"));
        panelTop.add(cboMaBan);
        panelTop.add(new JLabel("Chọn nhân viên:"));
        panelTop.add(cboMaNV);
        panelTop.add(new JLabel("Ngày vào:"));
        panelTop.add(txtNgayVao);
        panelTop.add(new JLabel("Hình thức thanh toán:"));
        panelTop.add(cboHinhThucThanhToan);
        panelTop.add(btnAddOrder);

        JPanel panelBottom = new JPanel();
        panelBottom.setLayout(new FlowLayout());
        panelBottom.add(new JLabel("Tổng tiền:"));
        panelBottom.add(txtTotalAmount);
        panelBottom.add(btnCalculateTotal);
        panelBottom.add(btnPay);

        btnAddOrder.addActionListener(this);
        btnCalculateTotal.addActionListener(this);
        btnPay.addActionListener(this);
        
        setLayout(new BorderLayout());
        add(panelTop, BorderLayout.NORTH);
        add(new JScrollPane(txtOrderDetails), BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);


        loadDrinks();
        loadMaBan();
        loadMaNV();

        cboDrink.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updatePriceLabel();
                }
            }
        });


    }

    private void loadMaNV() {
        String query = "SELECT maNV FROM NhanVien";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Quanlyquancaphe", "sa", "sapassword");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                cboMaNV.addItem(rs.getString("maNV"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadMaBan() {
        String query = "SELECT maBan FROM Ban WHERE trangThai = N'Trống'";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Quanlyquancaphe", "sa", "sapassword");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String maBan = rs.getString("maBan");
                cboMaBan.addItem(maBan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadDrinks() {
        String query = "SELECT maTU, tenTU FROM ThucUong";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Quanlyquancaphe", "sa", "sapassword");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String maTU = rs.getString("maTU");
                String tenTU = rs.getString("tenTU");
                cboDrink.addItem(maTU + " - " + tenTU);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updatePriceLabel() {
        String selectedDrink = (String) cboDrink.getSelectedItem();
        String[] parts = selectedDrink.split(" - ");
        String maTU = parts[0];
        double price = getDrinkPrice(maTU);
        lblPrice.setText("Giá: " + price + " VND");
    }

    public double getDrinkPrice(String maTU) {
        double price = 0.0;
        String query = "SELECT giaTU FROM ThucUong WHERE maTU = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Quanlyquancaphe", "sa", "sapassword");
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, maTU);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                price = rs.getDouble("giaTU");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }

    private void addOrderItem() {
        String selectedDrink = (String) cboDrink.getSelectedItem();
        String quantityText = txtQuantity.getText();
        int quantity;

        try {
            quantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng hợp lệ.");
            return;
        }

        String[] parts = selectedDrink.split(" - ");
        String maTU = parts[0];
        String tenTU = parts[1];

        double price = getDrinkPrice(maTU);
        double total = price * quantity;

        // Thêm vào danh sách
        thucUongList.add(maTU);
        soLuongList.add(quantity);

        // Cập nhật tổng tiền
        tongTien += total;

        // Cập nhật chi tiết đơn hàng
        txtOrderDetails.append(tenTU + " - " + quantity + " x " + price + " = " + total + " VND\n");
//        txtTotalAmount.setText(tongTien + " VND");
    }
    
   


    private void calculateTotalAmount() {
        txtTotalAmount.setText(tongTien + " VND");
    }

//    public static void main(String[] args) {
//        new DatMon().setVisible(true);
//    }

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
		if (e.getSource() == btnAddOrder) {
			addOrderItem();
        }
		if (e.getSource() == btnCalculateTotal) {
			calculateTotalAmount();
        }
		if(e.getSource() == btnPay) {
			 String selectedMaBan = (String) cboMaBan.getSelectedItem();
             String selectedMaNV = (String) cboMaNV.getSelectedItem();
             String ngayVao = txtNgayVao.getText();
             String hinhThucThanhToan = (String) cboHinhThucThanhToan.getSelectedItem();
             maBanList.add(selectedMaBan);
             maNVList.add(selectedMaNV);

             // Chuyển dữ liệu sang ThanhToanPanel
             ThanhToanPanel thanhToanPanel = new ThanhToanPanel(thucUongList, soLuongList, maBanList, maNVList, tongTien, ngayVao, hinhThucThanhToan);
             JFrame paymentFrame = new JFrame("Thanh Toán");
             paymentFrame.setSize(500, 400);
             paymentFrame.setLocationRelativeTo(null);
             paymentFrame.add(thanhToanPanel);
             paymentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
             paymentFrame.setVisible(true);
		}
	}
}

