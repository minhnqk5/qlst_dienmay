package com.qlst.model;

public class Customer9 {
    private int id;
    private String tenDangNhap;
    private String matKhau;
    private String ten;
    private String sdt;
    private String email;
    private String diaChi;

    public Customer9() {
    }

    public Customer9(String tenDangNhap, String matKhau, String ten, String sdt, String email, String diaChi) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.ten = ten;
        this.sdt = sdt;
        this.email = email;
        this.diaChi = diaChi;
    }

    public String getMaKhachHang() {
        return String.format("KH%03d", id);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTenDangNhap() { return tenDangNhap; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }
    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }
    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }
    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
}
