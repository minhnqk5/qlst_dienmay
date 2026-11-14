package com.qlst.dao;

import com.qlst.model.CustomerRevenueStatistics9;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRevenueStatisticDAO extends DAO {

    private static final String SQL =
        "SELECT c.id, c.tenDangNhap, c.ten, c.sdt, c.email, c.diaChi, "
      + "       COUNT(*)         AS soGiaoDich, "
      + "       SUM(t.soLuong)   AS tongSoLuong, "
      + "       SUM(t.thanhTien) AS doanhThu "
      + "FROM ( "
      + "   SELECT o.customer_id AS customer_id, oi.thanhTien AS thanhTien, "
      + "          COALESCE((SELECT SUM(od.soLuong) FROM tblorderdetail9 od WHERE od.order_id = o.id), 0) AS soLuong "
      + "   FROM tblonlineinvoice9 oi JOIN tblorder9 o ON o.id = oi.order_id "
      + "   WHERE oi.ngayGio >= ? AND oi.ngayGio < DATE_ADD(?, INTERVAL 1 DAY) "
      + "   UNION ALL "
      + "   SELECT c2.id, w.tongTien, "
      + "          COALESCE((SELECT SUM(d.soLuong) FROM tblwalkininvoicedetail9 d WHERE d.walkInInvoice_id = w.id), 0) "
      + "   FROM tblwalkininvoice9 w JOIN tblcustomer9 c2 ON c2.sdt = w.sdt "
      + "   WHERE w.ngayGio >= ? AND w.ngayGio < DATE_ADD(?, INTERVAL 1 DAY) "
      + ") t JOIN tblcustomer9 c ON c.id = t.customer_id "
      + "GROUP BY c.id, c.tenDangNhap, c.ten, c.sdt, c.email, c.diaChi "
      + "ORDER BY doanhThu DESC, c.id ASC";

    public CustomerRevenueStatisticDAO() {
        super();
    }

    public CustomerRevenueStatistics9[] getCustomerRevenueStatistics(Date from, Date to) throws SQLException {
        List<CustomerRevenueStatistics9> list = new ArrayList<>();
        try (PreparedStatement ps = connection().prepareStatement(SQL)) {
            ps.setDate(1, from);
            ps.setDate(2, to);
            ps.setDate(3, from);
            ps.setDate(4, to);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CustomerRevenueStatistics9 s = new CustomerRevenueStatistics9();
                    s.setId(rs.getInt("id"));
                    s.setTenDangNhap(rs.getString("tenDangNhap"));
                    s.setTen(rs.getString("ten"));
                    s.setSdt(rs.getString("sdt"));
                    s.setEmail(rs.getString("email"));
                    s.setDiaChi(rs.getString("diaChi"));
                    s.setSoGiaoDich(rs.getInt("soGiaoDich"));
                    s.setTongSoLuong(rs.getInt("tongSoLuong"));
                    s.setDoanhThuTrenKhachHang(rs.getDouble("doanhThu"));
                    s.setFrom(from);
                    s.setTo(to);
                    list.add(s);
                }
            }
        }
        double tong = 0;
        for (CustomerRevenueStatistics9 s : list) {
            tong += s.getDoanhThuTrenKhachHang();
        }
        for (CustomerRevenueStatistics9 s : list) {
            s.setTongSoKhachMuaHang(list.size());
            s.setTongDoanhThu(tong);
        }
        return list.toArray(new CustomerRevenueStatistics9[0]);
    }
}
