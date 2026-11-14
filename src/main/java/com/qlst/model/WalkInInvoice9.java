package com.qlst.model;

import java.util.Date;

public class WalkInInvoice9 {
    private int id;
    private Date ngayGio;
    private String tenKhachHang;
    private String sdt;
    private double tongTien;
    private int tongSoLuong;
    private SalesStaff9 salesStaff;

    public WalkInInvoice9() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Date getNgayGio() { return ngayGio; }
    public void setNgayGio(Date ngayGio) { this.ngayGio = ngayGio; }
    public String getTenKhachHang() { return tenKhachHang; }
    public void setTenKhachHang(String tenKhachHang) { this.tenKhachHang = tenKhachHang; }
    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }
    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }
    public int getTongSoLuong() { return tongSoLuong; }
    public void setTongSoLuong(int tongSoLuong) { this.tongSoLuong = tongSoLuong; }
    public SalesStaff9 getSalesStaff() { return salesStaff; }
    public void setSalesStaff(SalesStaff9 salesStaff) { this.salesStaff = salesStaff; }
}
