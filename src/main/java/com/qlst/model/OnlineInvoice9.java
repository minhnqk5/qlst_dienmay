package com.qlst.model;

import java.util.Date;

public class OnlineInvoice9 {
    private int id;
    private Date ngayGio;
    private double thanhTien;
    private Order9 order;

    public OnlineInvoice9() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Date getNgayGio() { return ngayGio; }
    public void setNgayGio(Date ngayGio) { this.ngayGio = ngayGio; }
    public double getThanhTien() { return thanhTien; }
    public void setThanhTien(double thanhTien) { this.thanhTien = thanhTien; }
    public Order9 getOrder() { return order; }
    public void setOrder(Order9 order) { this.order = order; }
}
