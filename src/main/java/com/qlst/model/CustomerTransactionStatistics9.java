package com.qlst.model;

public class CustomerTransactionStatistics9 extends Customer9 {
    private int tongSoLuong;
    private double tongTien;
    private OnlineInvoice9[] listOnlineInvoice;
    private WalkInInvoice9[] listWalkInInvoice;

    public CustomerTransactionStatistics9() {
    }

    public CustomerTransactionStatistics9(Customer9 c,
                                          int tongSoLuong, double tongTien,
                                          OnlineInvoice9[] listOnlineInvoice, WalkInInvoice9[] listWalkInInvoice) {
        setId(c.getId());
        setTenDangNhap(c.getTenDangNhap());
        setTen(c.getTen());
        setSdt(c.getSdt());
        setEmail(c.getEmail());
        setDiaChi(c.getDiaChi());
        this.tongSoLuong = tongSoLuong;
        this.tongTien = tongTien;
        this.listOnlineInvoice = listOnlineInvoice;
        this.listWalkInInvoice = listWalkInInvoice;
    }

    public int getTongSoLuong() { return tongSoLuong; }
    public void setTongSoLuong(int tongSoLuong) { this.tongSoLuong = tongSoLuong; }
    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }
    public OnlineInvoice9[] getListOnlineInvoice() { return listOnlineInvoice; }
    public void setListOnlineInvoice(OnlineInvoice9[] listOnlineInvoice) { this.listOnlineInvoice = listOnlineInvoice; }
    public WalkInInvoice9[] getListWalkInInvoice() { return listWalkInInvoice; }
    public void setListWalkInInvoice(WalkInInvoice9[] listWalkInInvoice) { this.listWalkInInvoice = listWalkInInvoice; }
}
