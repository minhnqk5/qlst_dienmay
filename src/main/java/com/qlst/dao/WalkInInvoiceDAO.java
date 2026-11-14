package com.qlst.dao;

import com.qlst.model.SalesStaff9;
import com.qlst.model.WalkInInvoice9;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class WalkInInvoiceDAO extends DAO {

    private static final String SQL =
        "SELECT w.id, w.ngayGio, w.tenKhachHang, w.sdt, w.tongTien, w.salesStaff_id, m.ten AS staffName, "
      + "       COALESCE((SELECT SUM(d.soLuong) FROM tblwalkininvoicedetail9 d WHERE d.walkInInvoice_id = w.id), 0) AS tongSoLuong "
      + "FROM tblwalkininvoice9 w JOIN tblmember9 m ON m.id = w.salesStaff_id "
      + "WHERE w.sdt = ? AND w.ngayGio >= ? AND w.ngayGio < DATE_ADD(?, INTERVAL 1 DAY) "
      + "ORDER BY w.ngayGio";

    public WalkInInvoiceDAO() {
        super();
    }

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
                    inv.setTongSoLuong(rs.getInt("tongSoLuong"));
                    inv.setSalesStaff(staff);
                    list.add(inv);
                }
            }
        }
        return list.toArray(new WalkInInvoice9[0]);
    }
}
