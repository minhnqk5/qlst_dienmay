package com.qlst.dao;

import com.qlst.model.Manager9;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDAO extends DAO {

    public MemberDAO() {
        super();
    }

    public Manager9 getManager(Manager9 m) throws SQLException {
        String sql = "SELECT mb.id, mb.tenDangNhap, mb.matKhau, mb.ten, mb.sdt, mb.email, mb.diaChi, mb.viTri "
                   + "FROM tblmember9 mb JOIN tblmanager9 mg ON mg.id = mb.id "
                   + "WHERE mb.tenDangNhap = ?";
        try (PreparedStatement ps = connection().prepareStatement(sql)) {
            ps.setString(1, m.getTenDangNhap());
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Manager9 r = new Manager9();
                r.setId(rs.getInt("id"));
                r.setTenDangNhap(rs.getString("tenDangNhap"));
                r.setMatKhau(rs.getString("matKhau"));
                r.setTen(rs.getString("ten"));
                r.setSdt(rs.getString("sdt"));
                r.setEmail(rs.getString("email"));
                r.setDiaChi(rs.getString("diaChi"));
                r.setViTri(rs.getString("viTri"));
                return r;
            }
        }
    }
}
