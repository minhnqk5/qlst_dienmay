package com.qlst.dao;

import com.qlst.model.Customer9;
import com.qlst.model.OnlineInvoice9;
import com.qlst.model.Order9;
import com.qlst.model.OrderDetail9;
import com.qlst.model.Product9;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OnlineInvoiceDAO extends DAO {

    private static final String SQL_INVOICE =
        "SELECT oi.id, oi.ngayGio, oi.thanhTien, o.id AS order_id, o.ngayDat, o.tinhTrangThanhToan, o.diaChiGiaoHang "
      + "FROM tblonlineinvoice9 oi JOIN tblorder9 o ON o.id = oi.order_id "
      + "WHERE o.customer_id = ? AND oi.ngayGio >= ? AND oi.ngayGio < DATE_ADD(?, INTERVAL 1 DAY) "
      + "ORDER BY oi.ngayGio";

    private static final String SQL_DETAIL =
        "SELECT d.id, d.soLuong, d.donGia, d.thanhTien, p.id AS product_id, p.tenMatHang "
      + "FROM tblorderdetail9 d JOIN tblproduct9 p ON p.id = d.product_id WHERE d.order_id = ? ORDER BY d.id";

    public OnlineInvoiceDAO() {
        super();
    }

    public OnlineInvoice9[] getOnlineInvoice(int customerId, Date from, Date to) throws SQLException {
        List<OnlineInvoice9> list = new ArrayList<>();
        try (PreparedStatement ps = connection().prepareStatement(SQL_INVOICE)) {
            ps.setInt(1, customerId);
            ps.setDate(2, from);
            ps.setDate(3, to);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Customer9 customer = new Customer9();
                    customer.setId(customerId);

                    Order9 order = new Order9();
                    order.setId(rs.getInt("order_id"));
                    order.setNgayDat(toDate(rs.getTimestamp("ngayDat")));
                    order.setTinhTrangThanhToan(rs.getString("tinhTrangThanhToan"));
                    order.setDiaChiGiaoHang(rs.getString("diaChiGiaoHang"));
                    order.setCustomer(customer);

                    OnlineInvoice9 inv = new OnlineInvoice9();
                    inv.setId(rs.getInt("id"));
                    inv.setNgayGio(toDate(rs.getTimestamp("ngayGio")));
                    inv.setThanhTien(rs.getDouble("thanhTien"));
                    inv.setOrder(order);
                    list.add(inv);
                }
            }
        }
        for (OnlineInvoice9 inv : list) {
            inv.getOrder().setDetail(getOrderDetail(inv.getOrder().getId()));
        }
        return list.toArray(new OnlineInvoice9[0]);
    }

    private OrderDetail9[] getOrderDetail(int orderId) throws SQLException {
        List<OrderDetail9> details = new ArrayList<>();
        try (PreparedStatement ps = connection().prepareStatement(SQL_DETAIL)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product9 p = new Product9();
                    p.setId(rs.getInt("product_id"));
                    p.setTenMatHang(rs.getString("tenMatHang"));

                    OrderDetail9 d = new OrderDetail9();
                    d.setId(rs.getInt("id"));
                    d.setSoLuong(rs.getInt("soLuong"));
                    d.setDonGia(rs.getDouble("donGia"));
                    d.setThanhTien(rs.getDouble("thanhTien"));
                    d.setProduct(p);
                    details.add(d);
                }
            }
        }
        return details.toArray(new OrderDetail9[0]);
    }

    private static java.util.Date toDate(Timestamp t) {
        return t == null ? null : new java.util.Date(t.getTime());
    }
}
