package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrangChu extends JFrame implements ActionListener {

	private static boolean isAdmin = false;
	
    public TrangChu() {
        setTitle("Quản lý quán cà phê");
        setSize(1200, 700);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        boolean adminStatus = isAdmin;
//        System.out.println(isAdmin);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

      
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(139, 0, 0)); 
        menuBar.setPreferredSize(new Dimension(1200, 100)); 

       
        JMenu cafeMenu = new JMenu("Quản Lý Thức Uống");
        cafeMenu.setForeground(Color.WHITE);
        cafeMenu.setFont(new Font("Arial", Font.BOLD, 20));
       

        
        JMenuItem themMon = new JMenuItem("Quản lý thức uống");
        themMon.setFont(new Font("Arial", Font.BOLD, 16));
        themMon.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); 
        themMon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new QuanLyThucUong(); 
            }
        });

        cafeMenu.add(themMon);

        
        JMenu datMon = new JMenu("Đặt Món");
        datMon.setForeground(Color.WHITE);
        datMon.setFont(new Font("Arial", Font.BOLD, 20));
        
        
        JMenuItem order = new JMenuItem("Đặt món và thanh toán");
        order.setFont(new Font("Arial", Font.BOLD, 16));
        order.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); 
        order.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DatMon();
            }
        });
        datMon.add(order);

        JMenu quanLyBan = new JMenu("Quản Lý Bàn");
        quanLyBan.setForeground(Color.WHITE);
        quanLyBan.setFont(new Font("Arial", Font.BOLD, 20));
        
        
        JMenuItem themBan = new JMenuItem("Quản lý bàn");
        themBan.setFont(new Font("Arial", Font.BOLD, 16));
        themBan.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); 
        themBan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new QuanLyBan();
            }
        });
        quanLyBan.add(themBan);

        JMenu quanLyDatBan = new JMenu("Quản Lý Đặt Bàn");
        quanLyDatBan.setForeground(Color.WHITE);
        quanLyDatBan.setFont(new Font("Arial", Font.BOLD, 20));
        
        JMenuItem themDatBan = new JMenuItem("Quản lý đặt bàn");
        themDatBan.setFont(new Font("Arial", Font.BOLD, 16));
        themDatBan.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); 
        themDatBan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new QuanLyDatBan();
            }
        });
        quanLyDatBan.add(themDatBan);
        
        // Tạo menu Khách Hàng
        JMenu khachHang = new JMenu("Khách Hàng");
        khachHang.setForeground(Color.WHITE);
        khachHang.setFont(new Font("Arial", Font.BOLD, 20));
        

        JMenuItem themKH = new JMenuItem("Quản lý khách hàng");
        themKH.setFont(new Font("Arial", Font.BOLD, 16));
        themKH.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); 
        themKH.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new QuanLyKhachHang();
            }
        });
        khachHang.add(themKH);

        // Tạo menu Quản lý
        JMenu quanLy = new JMenu("Quản lý");
        quanLy.setForeground(Color.WHITE);
        quanLy.setFont(new Font("Arial", Font.BOLD, 20));
    
       

        JMenuItem themNV = new JMenuItem("Quản lý nhân viên");
        themNV.setFont(new Font("Arial", Font.BOLD, 16));
        themNV.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); 
        themNV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new QuanLyNhanVien();
            }
        });

        JMenuItem hoaDon = new JMenuItem("Quản lý hóa đơn");
        hoaDon.setFont(new Font("Arial", Font.BOLD, 16));
        hoaDon.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        hoaDon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new QuanLyHoaDon();
            }
        });

//        quanLy.add(themNV);
//        quanLy.add(hoaDon);
        //admin mới xem được 
        if (adminStatus) {
            quanLy.add(themNV);
            quanLy.add(hoaDon);
        }
//        
        
        menuBar.add(cafeMenu);
        menuBar.add(Box.createHorizontalStrut(50));  
        menuBar.add(datMon);
        menuBar.add(Box.createHorizontalStrut(50));  
        menuBar.add(quanLyBan);
        menuBar.add(Box.createHorizontalStrut(50));  
        menuBar.add(quanLyDatBan);
        menuBar.add(Box.createHorizontalStrut(50));  
        menuBar.add(khachHang);
        menuBar.add(Box.createHorizontalStrut(50));  
        menuBar.add(quanLy);

       
        setJMenuBar(menuBar);

        
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(255, 228, 225)); 

        // Thêm logo
        ImageIcon logoIcon = new ImageIcon("image/bgr.jpg");
        JLabel logoLabel = new JLabel(logoIcon);
        logoPanel.add(logoLabel);

        // Thêm logoPanel vào mainPanel
        mainPanel.add(logoPanel, BorderLayout.CENTER);

        
        add(mainPanel);

        
        setVisible(true);
    }

    public static void setIsAdmin(boolean isAdminStatus) {
        isAdmin = isAdminStatus;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

//    public static void main(String[] args) {
//        new TrangChu();
//    }
}
