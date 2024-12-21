CREATE TABLE ThucUong (
    maTU NVARCHAR(50) PRIMARY KEY, -- Mã thức uống
    tenTU NVARCHAR(100),           -- Tên thức uống
    giaTU FLOAT,                   -- Giá thức uống
    loaiTU NVARCHAR(50),           -- Loại thức uống
    soLuong INT                    -- Số lượng thức uống
);


INSERT INTO ThucUong (maTU, tenTU, giaTU, loaiTU, soLuong)
VALUES
    (N'TR001', N'Cà phê sữa đá', 20000, N'Cà phê', 10),
    (N'TR002', N'Trà sữa trân châu', 25000, N'Trà', 15),
    (N'TR003', N'Nước ép trái cây', 18000, N'Nước ép', 20);



CREATE TABLE Ban (
    maBan NVARCHAR(50) PRIMARY KEY,
    soChoNgoi INT,
    trangThai NVARCHAR(20) CHECK (trangThai IN (N'Trống', N'Đã đặt'))
);

INSERT INTO Ban (maBan, soChoNgoi, trangThai)
VALUES
    (N'A101', 4, N'Trống'),
    (N'A102', 6, N'Đã đặt'),
    (N'A103', 2, N'Trống'),
    (N'A104', 8, N'Đã đặt'),
    (N'A105', 4, N'Đã đặt');



CREATE TABLE KhachHang (
    maKhachHang NVARCHAR(20) PRIMARY KEY,
    tenKhachHang NVARCHAR(100),
    soDienThoai NVARCHAR(20)
);
INSERT INTO KhachHang (maKhachHang, tenKhachHang, soDienThoai)
VALUES
    (N'KH001', N'Nguyễn Văn A', '0987654321'),
    (N'KH002', N'Lê Thị B', '0345678901'),
    (N'KH003', N'Thảo My', '0329678098');


CREATE TABLE DatBan (
    maBan NVARCHAR(50),
    maKhachHang NVARCHAR(20),
    ghiChu NVARCHAR(200),
    CONSTRAINT FK_DatBan_KhachHang FOREIGN KEY (maKhachHang) REFERENCES KhachHang(maKhachHang),
    CONSTRAINT FK_DatBan_Ban FOREIGN KEY (maBan) REFERENCES Ban(maBan)
);
INSERT INTO DatBan (maBan,  maKhachHang, ghiChu)
VALUES
    (N'A101', N'KH001', N'Bàn sát cửa sổ'),
    (N'A102', N'KH002', N'Không có yêu cầu đặc biệt'),
    (N'A103', N'KH002', N'Đặt trước cho sinh nhật'),
    (N'A104', N'KH001', N'Yêu cầu bàn riêng'),
    (N'A105', N'KH003', N'Đặt bàn gấp');


CREATE TABLE HoaDon (
    maHoaDon NVARCHAR(20) PRIMARY KEY,
    maKhachHang NVARCHAR(20),
    maNV NVARCHAR(50),
    ngayVao DATE,
    trangThai NVARCHAR(20),
    hinhThucThanhToan NVARCHAR(20) CHECK (hinhThucThanhToan IN (N'Tiền mặt', N'Chuyển khoản')),
    tongTien FLOAT
    FOREIGN KEY (maKhachHang) REFERENCES KhachHang(maKhachHang),
    FOREIGN KEY (maNV) REFERENCES NhanVien(maNV)
);
INSERT INTO HoaDon (maHoaDon, maKhachHang, maNV, ngayVao, trangThai, hinhThucThanhToan, tongTien)
VALUES
    (N'HD001', N'NV001','2024-10-01', N'Chưa thanh toán', N'Tiền mặt', 150000.0),
    (N'HD002',  N'NV001','2024-10-02', N'Đã thanh toán', N'Chuyển khoản', 250000.0),
    (N'HD003', N'NV001','2024-10-03', N'Chưa thanh toán', N'Tiền mặt', 300000.0),
    (N'HD004',N'NV002', '2024-10-04', N'Đã thanh toán', N'Chuyển khoản', 400000.0),
    (N'HD005',N'NV002', '2024-10-05', N'Chưa thanh toán', N'Chuyển khoản', 500000.0);

CREATE TABLE ChiTietHoaDon (
    maHoaDon NVARCHAR(20) PRIMARY KEY ,
    maBan NVARCHAR(50),
    maTU NVARCHAR(50),
    tenTU NVARCHAR(100),
    soLuong INT,
    thanhTien FLOAT,
  	FOREIGN KEY (maBan) REFERENCES Ban(maBan),
    FOREIGN KEY (maTU) REFERENCES ThucUong(maTU)
	
);
INSERT INTO ChiTietHoaDon ( maHoaDon, maBan,maTU, soLuong, thanhTien)
VALUES
    ( N'HD001',N'A101', N'TR001', 2, 40000),  
    ( N'HD001', N'A102',N'TR003', 1, 18000),  
    ( N'HD002',N'A103', N'TR002', 3, 75000),  
    ( N'HD003', N'A104',N'TR001', 1, 20000); 


CREATE TABLE NhanVien (
    maNV NVARCHAR(50) PRIMARY KEY,
    hoTen NVARCHAR(100),
    username NVARCHAR(50),
    password NVARCHAR(50),
    diaChi NVARCHAR(200),
    sdt NVARCHAR(10),
    );

INSERT INTO NhanVien (maNV, hoTen, username, password, diaChi, sdt)
VALUES
    (N'NV001', N'Hoàng Thảo My',N'user1', N'123', N'Hà Nội', 0987654321),
    (N'NV002',N'Trần Thị Thùy Linh',N'user2', N'1234', N'Hồ Chí Minh', 0345678901);

CREATE TABLE Admin (
    username NVARCHAR(50) PRIMARY KEY,
    password NVARCHAR(50)
);

INSERT INTO Admin (username, password)
VALUES
    ('admin', 'admin');
