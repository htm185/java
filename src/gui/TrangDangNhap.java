package gui;

import javax.swing.*;

import connectDB.ConnectDB;
import dao.DangNhap_DAO;
import dao.NhanVien_DAO;
import entity.DangNhap;
import entity.NhanVien;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

public class TrangDangNhap extends JFrame  implements ActionListener, MouseListener{
	private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton employeeButton;
    private JButton adminButton;
    private JButton signInButton;
    
    public TrangDangNhap() {
        setTitle("Quản lý quán cà phê");
        setSize(800, 500);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2)); 
        
        // Left panel for login form
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(255, 228, 225)); 
        leftPanel.setLayout(null);
        
       
        // Logo Label
        JLabel logoLabel = new JLabel(new ImageIcon("image/logo2.png"));
        logoLabel.setBounds(70, 0, 250, 250); 
        leftPanel.add(logoLabel);

        usernameField = new JTextField();
        usernameField.setBounds(100, 250, 200, 40);
        usernameField.setBorder(BorderFactory.createTitledBorder("Tên đăng nhập"));
        leftPanel.add(usernameField);
        
        // Password Field
        passwordField = new JPasswordField();
        passwordField.setBounds(100, 300, 200, 40);
        passwordField.setBorder(BorderFactory.createTitledBorder("Mật khẩu"));
        leftPanel.add(passwordField);
        
        // Forget password link
        JLabel forgetPasswordLabel = new JLabel("Quên mật khẩu?");
        forgetPasswordLabel.setBounds(140, 340, 200, 30);
        leftPanel.add(forgetPasswordLabel);
        
        employeeButton = new JButton("Nhân viên");
        employeeButton.setBounds(100, 400, 100, 30); 
        leftPanel.add(employeeButton);
        employeeButton.addActionListener(this);

        adminButton = new JButton("Admin");
        adminButton.setBounds(210, 400, 100, 30); 
        leftPanel.add(adminButton);
        adminButton.addActionListener(this);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        JLabel coffeeImage = new JLabel(new ImageIcon("image/coffee.jpg"));
        rightPanel.add(coffeeImage, BorderLayout.CENTER);
        
    
        // Add both panels to the main panel
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        
        // Add main panel to the frame
        add(mainPanel);
        
  
    }
    
    
    
 // Hàm xử lý đăng nhập Nhân viên
    private void handleEmployeeLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            ConnectDB.getInstance().connect();  // Đảm bảo kết nối cơ sở dữ liệu
            NhanVien_DAO nhanVienDao = new NhanVien_DAO();
            NhanVien nhanVien = nhanVienDao.findByUsernameAndPassword(username, password);

            if (nhanVien != null) {
//                JOptionPane.showMessageDialog(this, "Đăng nhập Nhân viên thành công!");
                TrangChu mainPage = new TrangChu();
                mainPage.setVisible(true);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Sai username hoặc password!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi kết nối cơ sở dữ liệu");
        }
    }
 // Hàm xử lý đăng nhập Admin
    private void handleAdminLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            ConnectDB.getInstance().connect(); 
            DangNhap_DAO dangNhapDao = new DangNhap_DAO();
            DangNhap dangNhap = dangNhapDao.findByUsernameAndPassword1(username, password);

            if (dangNhap != null ) {
            	TrangChu.setIsAdmin(true); 
            	 TrangChu mainPage = new TrangChu();
                 mainPage.setVisible(true);
                 this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Sai username hoặc password!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi kết nối cơ sở dữ liệu");
        }
    }
    @Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	       
	        if (e.getSource() == employeeButton) {
	            handleEmployeeLogin();
	            TrangChu.setIsAdmin(false);
	        }
	        
	        else if (e.getSource() == adminButton) {
	            handleAdminLogin();
	            TrangChu.setIsAdmin(true);
	        }
	       
	}
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

	
	public static void main(String[] args) {
	    SwingUtilities.invokeLater(new Runnable() {
	        @Override
	        public void run() {
	            // Hiển thị trang đăng nhập
	            new TrangDangNhap();
	        }
	    });
	}


	


}
