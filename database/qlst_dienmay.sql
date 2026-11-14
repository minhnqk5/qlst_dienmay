drop database if exists qlst_dienmay;

CREATE DATABASE IF NOT EXISTS qlst_dienmay
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE qlst_dienmay;

CREATE TABLE IF NOT EXISTS tblcustomer9 (
    id          INT           NOT NULL AUTO_INCREMENT,
    tenDangNhap VARCHAR(100)  NOT NULL,
    matKhau     VARCHAR(255)  NOT NULL,
    ten         VARCHAR(255)  NOT NULL,
    sdt         VARCHAR(20)   NOT NULL,
    email       VARCHAR(255)  NOT NULL,
    diaChi      VARCHAR(255)  NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uq_customer_username (tenDangNhap),
    UNIQUE KEY uq_customer_sdt (sdt)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS tblmember9 (
    id          INT           NOT NULL AUTO_INCREMENT,
    tenDangNhap VARCHAR(100)  NOT NULL,
    matKhau     VARCHAR(255)  NOT NULL,
    ten         VARCHAR(255)  NOT NULL,
    sdt         VARCHAR(20)   NOT NULL,
    email       VARCHAR(255)  NOT NULL,
    diaChi      VARCHAR(255)  NOT NULL,
    viTri       VARCHAR(100)  NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uq_member_username (tenDangNhap)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS tblmanager9 (
    id INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_manager_member FOREIGN KEY (id) REFERENCES tblmember9 (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS tblsalesstaff9 (
    id INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_sales_member FOREIGN KEY (id) REFERENCES tblmember9 (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS tblproduct9 (
    id         INT           NOT NULL AUTO_INCREMENT,
    tenMatHang TEXT          NOT NULL,
    anh        VARCHAR(255)  NULL,
    hang       VARCHAR(255)  NULL,
    baoHanh    FLOAT         NULL,
    donGia     DECIMAL(15,0) NOT NULL,
    soLuong    INT UNSIGNED  NOT NULL DEFAULT 0,
    moTa       VARCHAR(255)  NULL,
    PRIMARY KEY (id),
    CONSTRAINT ck_product_baohanh CHECK (baoHanh IS NULL OR baoHanh >= 0),
    CONSTRAINT ck_product_dongia  CHECK (donGia >= 0)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS tblorder9 (
    id                INT          NOT NULL AUTO_INCREMENT,
    ngayDat           DATETIME     NOT NULL,
    tinhTrangThanhToan VARCHAR(100) NOT NULL,
    diaChiGiaoHang    VARCHAR(255) NOT NULL,
    customer_id       INT          NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_order_customer FOREIGN KEY (customer_id) REFERENCES tblcustomer9 (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS tblorderdetail9 (
    id         INT           NOT NULL AUTO_INCREMENT,
    soLuong    INT UNSIGNED  NOT NULL,
    donGia     DECIMAL(15,0) NOT NULL,
    thanhTien  DECIMAL(15,0) NOT NULL,
    order_id   INT           NOT NULL,
    product_id INT           NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT ck_orderdetail_soluong CHECK (soLuong > 0),
    CONSTRAINT ck_orderdetail_tien    CHECK (donGia >= 0 AND thanhTien >= 0),
    CONSTRAINT fk_orderdetail_order   FOREIGN KEY (order_id)   REFERENCES tblorder9 (id),
    CONSTRAINT fk_orderdetail_product FOREIGN KEY (product_id) REFERENCES tblproduct9 (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS tblonlineinvoice9 (
    id        INT           NOT NULL AUTO_INCREMENT,
    ngayGio   DATETIME      NOT NULL,
    thanhTien DECIMAL(15,0) NOT NULL,
    order_id  INT           NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uq_onlineinvoice_order (order_id),
    KEY idx_onlineinvoice_ngayGio (ngayGio),
    CONSTRAINT ck_onlineinvoice_tien CHECK (thanhTien >= 0),
    CONSTRAINT fk_onlineinvoice_order FOREIGN KEY (order_id) REFERENCES tblorder9 (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS tblwalkininvoice9 (
    id            INT           NOT NULL AUTO_INCREMENT,
    ngayGio       DATETIME      NOT NULL,
    tenKhachHang  VARCHAR(255)  NOT NULL,
    sdt           VARCHAR(20)   NOT NULL,
    tongTien      DECIMAL(15,0) NOT NULL,
    salesStaff_id INT           NOT NULL,
    PRIMARY KEY (id),
    KEY idx_walkin_sdt_ngayGio (sdt, ngayGio),
    CONSTRAINT ck_walkin_tien CHECK (tongTien >= 0),
    CONSTRAINT fk_walkin_salesstaff FOREIGN KEY (salesStaff_id) REFERENCES tblsalesstaff9 (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS tblwalkininvoicedetail9 (
    id              INT           NOT NULL AUTO_INCREMENT,
    soLuong         INT UNSIGNED  NOT NULL,
    thanhTien       DECIMAL(15,0) NOT NULL,
    product_id      INT           NOT NULL,
    walkInInvoice_id INT          NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT ck_walkindetail_soluong CHECK (soLuong > 0),
    CONSTRAINT ck_walkindetail_tien    CHECK (thanhTien >= 0),
    CONSTRAINT fk_walkindetail_product FOREIGN KEY (product_id)       REFERENCES tblproduct9 (id),
    CONSTRAINT fk_walkindetail_invoice FOREIGN KEY (walkInInvoice_id) REFERENCES tblwalkininvoice9 (id)
) ENGINE=InnoDB;


INSERT INTO tblmember9 (id, tenDangNhap, matKhau, ten, sdt, email, diaChi, viTri) VALUES
 (1,   'quanly',    'pbkdf2$65536$dWl9iwd4B8VQC+3YJXL4ng==$v+iLL8GTdchTdphYS04AvtZAieaB//5yIuVtaz+fk10=',   'Đỗ Quản Lý',   '0988000001', 'quanly@qlst.vn',   'Hà Nội', 'Nhân viên quản lí'),
 (412, 'banhang01', 'pbkdf2$65536$jQ7QJHPtJAJVoXcbKMkUSg==$BvOqBnhlaWjCkaY/GWQkVVq62X5Mu6W5yLHmqry8rdo=', 'Vũ Bán Hàng',  '0988000412', 'banhang01@qlst.vn','Hà Nội', 'Nhân viên bán hàng');
INSERT INTO tblmanager9 (id) VALUES (1);
INSERT INTO tblsalesstaff9 (id) VALUES (412);

INSERT INTO tblcustomer9 (id, tenDangNhap, matKhau, ten, sdt, email, diaChi) VALUES
 (7,   'baohg',   'pbkdf2$65536$wxCOyBcry5pKtQsugsMj/Q==$mT51HQbyN3NG635Tilbtq7sd9PBbkOdM1HIJ9V+f5Mc=', 'Hoàng Gia Bảo',  '0901000007', 'baohg@gmail.com',   'Hải Phòng'),
 (25,  'binhtt',  'pbkdf2$65536$wxCOyBcry5pKtQsugsMj/Q==$mT51HQbyN3NG635Tilbtq7sd9PBbkOdM1HIJ9V+f5Mc=', 'Trần Thị Bình',  '0901000025', 'binhtt@gmail.com',  'Bắc Ninh'),
 (56,  'huypq',   'pbkdf2$65536$wxCOyBcry5pKtQsugsMj/Q==$mT51HQbyN3NG635Tilbtq7sd9PBbkOdM1HIJ9V+f5Mc=', 'Phạm Quốc Huy',  '0901000056', 'huypq@gmail.com',   'Bắc Giang'),
 (71,  'annv',    'pbkdf2$65536$wxCOyBcry5pKtQsugsMj/Q==$mT51HQbyN3NG635Tilbtq7sd9PBbkOdM1HIJ9V+f5Mc=', 'Nguyễn Văn An',  '0901000071', 'annv@gmail.com',    'Hà Nội'),
 (103, 'anhlm',   'pbkdf2$65536$wxCOyBcry5pKtQsugsMj/Q==$mT51HQbyN3NG635Tilbtq7sd9PBbkOdM1HIJ9V+f5Mc=', 'Lê Minh Anh',    '0901000103', 'anhlm@gmail.com',   'Hà Nội');

INSERT INTO tblproduct9 (id, tenMatHang, anh, hang, baoHanh, donGia, soLuong, moTa) VALUES
 (1, 'Smart Tivi Samsung 55 inch 4K',  NULL, 'Samsung',   24, 17000000, 30, 'Tivi 55 inch'),
 (2, 'Tủ lạnh LG Inverter 400L',       NULL, 'LG',        24, 15500000, 12, 'Tủ lạnh ngăn đá dưới'),
 (3, 'Máy giặt Electrolux 10kg',       NULL, 'Electrolux',24, 12000000, 20, 'Cửa trước'),
 (4, 'Điều hòa Daikin 1.5HP Inverter', NULL, 'Daikin',    36, 12900000, 25, 'Điều hòa 1 chiều'),
 (5, 'Tủ lạnh Panasonic 450L',         NULL, 'Panasonic', 24, 16250000, 10, 'Tủ lạnh 4 cửa');

INSERT INTO tblwalkininvoice9 (id, ngayGio, tenKhachHang, sdt, tongTien, salesStaff_id) VALUES
 (250, '2026-08-20 09:00:00', 'Nguyễn Văn An', '0901000071', 17000000, 412),
 (265, '2026-09-10 08:30:00', 'Nguyễn Văn An', '0901000071', 34000000, 412),
 (270, '2026-09-12 10:15:00', 'Trần Thị Bình', '0901000025', 16250000, 412),
 (277, '2026-09-15 14:00:00', 'Nguyễn Văn An', '0901000071', 17000000, 412);
INSERT INTO tblwalkininvoicedetail9 (soLuong, thanhTien, product_id, walkInInvoice_id) VALUES
 (1, 17000000, 1, 250),
 (2, 34000000, 1, 265),
 (1, 16250000, 5, 270),
 (1, 17000000, 1, 277);

INSERT INTO tblorder9 (id, ngayDat, tinhTrangThanhToan, diaChiGiaoHang, customer_id) VALUES
 (1, '2026-09-11 15:00:00', 'Đã thanh toán', 'Bắc Ninh',  25),
 (2, '2026-09-14 09:30:00', 'Đã thanh toán', 'Hà Nội',    103),
 (3, '2026-09-17 20:10:00', 'Đã thanh toán', 'Bắc Giang', 56);
INSERT INTO tblorderdetail9 (soLuong, donGia, thanhTien, order_id, product_id) VALUES
 (1, 16250000, 16250000, 1, 5),
 (2, 12900000, 25800000, 2, 4),
 (1, 12000000, 12000000, 3, 3);
INSERT INTO tblonlineinvoice9 (id, ngayGio, thanhTien, order_id) VALUES
 (1, '2026-09-11 15:20:00', 16250000, 1),
 (2, '2026-09-14 09:45:00', 25800000, 2),
 (3, '2026-09-17 20:30:00', 12000000, 3);
