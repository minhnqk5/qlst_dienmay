package com.qlst.model;

import java.util.Date;

public class CustomerRevenueStatistics9 extends Customer9 {
    private int tongSoKhachMuaHang;
    private double doanhThuTrenKhachHang;
    private double tongDoanhThu;
    private Date from;
    private Date to;
    private int soGiaoDich;
    private int tongSoLuong;

    public CustomerRevenueStatistics9() {
    }

    public int getTongSoKhachMuaHang() { return tongSoKhachMuaHang; }
    public void setTongSoKhachMuaHang(int tongSoKhachMuaHang) { this.tongSoKhachMuaHang = tongSoKhachMuaHang; }
    public double getDoanhThuTrenKhachHang() { return doanhThuTrenKhachHang; }
    public void setDoanhThuTrenKhachHang(double doanhThuTrenKhachHang) { this.doanhThuTrenKhachHang = doanhThuTrenKhachHang; }
    public double getTongDoanhThu() { return tongDoanhThu; }
    public void setTongDoanhThu(double tongDoanhThu) { this.tongDoanhThu = tongDoanhThu; }
    public Date getFrom() { return from; }
    public void setFrom(Date from) { this.from = from; }
    public Date getTo() { return to; }
    public void setTo(Date to) { this.to = to; }
    public int getSoGiaoDich() { return soGiaoDich; }
    public void setSoGiaoDich(int soGiaoDich) { this.soGiaoDich = soGiaoDich; }
    public int getTongSoLuong() { return tongSoLuong; }
    public void setTongSoLuong(int tongSoLuong) { this.tongSoLuong = tongSoLuong; }
}
