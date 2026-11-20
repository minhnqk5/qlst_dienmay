package com.qlst.dao;

import com.qlst.model.Product9;
import com.qlst.model.SalesStaff9;
import com.qlst.model.WalkInInvoice9;
import com.qlst.model.WalkInInvoiceDetail9;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class WalkInInvoiceDAO extends DAO {

    public WalkInInvoiceDAO() {
            super();
        }

    private static final String SQL =
        "SELECT w.id, w.ngayGio, w.tenKhachHang, w.sdt, w.tongTien, w.salesStaff_id, m.ten AS staffName "
      + "FROM tblwalkininvoice9 w JOIN tblmember9 m ON m.id = w.salesStaff_id "
      + "WHERE w.sdt = ? AND w.ngayGio >= ? AND w.ngayGio < DATE_ADD(?, INTERVAL 1 DAY) "
      + "ORDER BY w.ngayGio";

    public WalkInInvoice9[] getWalkInInvoice(String sdt, Date from, Date to) throws SQLException {
        List<WalkInInvoice9> list = new ArrayList<>();
        try (PreparedStatement ps = connection().prepareStatement(SQL)) {
            ps.setString(1, sdt);
            ps.setDate(2, from);
            ps.setDate(3, to);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SalesStaff9 staff = new SalesStaff9();
                    staff.setId(rs.getInt("salesStaff_id"));
                    staff.setTen(rs.getString("staffName"));

                    WalkInInvoice9 inv = new WalkInInvoice9();
                    inv.setId(rs.getInt("id"));
                    Timestamp t = rs.getTimestamp("ngayGio");
                    inv.setNgayGio(t == null ? null : new java.util.Date(t.getTime()));
                    inv.setTenKhachHang(rs.getString("tenKhachHang"));
                    inv.setSdt(rs.getString("sdt"));
                    inv.setTongTien(rs.getDouble("tongTien"));
                    inv.setSalesStaff(staff);
                    list.add(inv);
                }
            }
        }
        for (WalkInInvoice9 invoice : list) {
            invoice.setDetail(getWalkInInvoiceDetail(invoice.getId()));
        }
        return list.toArray(new WalkInInvoice9[0]);
    }





    

    private static final String SQL_DETAIL =
            "SELECT d.id, d.soLuong, d.donGia, d.thanhTien, p.id AS product_id, p.tenMatHang "
        + "FROM tblwalkininvoicedetail9 d JOIN tblproduct9 p ON p.id = d.product_id "
        + "WHERE d.walkInInvoice_id = ? ORDER BY d.id";

    private WalkInInvoiceDetail9[] getWalkInInvoiceDetail(int walkInInvoiceId) throws SQLException {
        List<WalkInInvoiceDetail9> details = new ArrayList<>();
        try (PreparedStatement ps = connection().prepareStatement(SQL_DETAIL)) {
            ps.setInt(1, walkInInvoiceId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product9 product = new Product9();
                    product.setId(rs.getInt("product_id"));
                    product.setTenMatHang(rs.getString("tenMatHang"));

                    WalkInInvoiceDetail9 detail = new WalkInInvoiceDetail9();
                    detail.setId(rs.getInt("id"));
                    detail.setSoLuong(rs.getInt("soLuong"));
                    detail.setDonGia(rs.getDouble("donGia"));
                    detail.setThanhTien(rs.getDouble("thanhTien"));
                    detail.setProduct(product);
                    details.add(detail);
                }
            }
        }
        return details.toArray(new WalkInInvoiceDetail9[0]);
    }
}
