package com.qlst.model;

import java.util.Date;

public class Order9 {
    private int id;
    private Date ngayDat;
    private String tinhTrangThanhToan;
    private String diaChiGiaoHang;
    private OrderDetail9[] detail;
    private Customer9 customer;

    public Order9() {
    }

    public int getTongSoLuong() {
        int sum = 0;
        if (detail != null) {
            for (OrderDetail9 d : detail) sum += d.getSoLuong();
        }
        return sum;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Date getNgayDat() { return ngayDat; }
    public void setNgayDat(Date ngayDat) { this.ngayDat = ngayDat; }
    public String getTinhTrangThanhToan() { return tinhTrangThanhToan; }
    public void setTinhTrangThanhToan(String tinhTrangThanhToan) { this.tinhTrangThanhToan = tinhTrangThanhToan; }
    public String getDiaChiGiaoHang() { return diaChiGiaoHang; }
    public void setDiaChiGiaoHang(String diaChiGiaoHang) { this.diaChiGiaoHang = diaChiGiaoHang; }
    public OrderDetail9[] getDetail() { return detail; }
    public void setDetail(OrderDetail9[] detail) { this.detail = detail; }
    public Customer9 getCustomer() { return customer; }
    public void setCustomer(Customer9 customer) { this.customer = customer; }
}
