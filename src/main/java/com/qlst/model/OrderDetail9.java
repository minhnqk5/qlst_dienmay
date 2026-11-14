package com.qlst.model;

public class OrderDetail9 {
    private int id;
    private int soLuong;
    private double donGia;
    private double thanhTien;
    private Product9 product;

    public OrderDetail9() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; }
    public double getThanhTien() { return thanhTien; }
    public void setThanhTien(double thanhTien) { this.thanhTien = thanhTien; }
    public Product9 getProduct() { return product; }
    public void setProduct(Product9 product) { this.product = product; }
}
