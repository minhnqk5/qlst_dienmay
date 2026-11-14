package com.qlst.dao;

import com.qlst.model.Customer9;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDAO extends DAO {

    public CustomerDAO() {
        super();
    }

    
    public Customer9 getCustomer(Customer9 c) throws SQLException {
        String sql = "SELECT id, tenDangNhap, ten, sdt, email, diaChi FROM tblcustomer9 "
                   + "WHERE tenDangNhap = ? OR sdt = ? "
                   + "UNION ALL "
                   + "SELECT id, tenDangNhap, ten, sdt, email, diaChi FROM tblmember9 WHERE tenDangNhap = ? "
                   + "LIMIT 1";
        try (PreparedStatement ps = connection().prepareStatement(sql)) {
            ps.setString(1, c.getTenDangNhap());
            ps.setString(2, c.getSdt());
            ps.setString(3, c.getTenDangNhap());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    public boolean saveCustomer(Customer9 c) throws SQLException {
        String sql = "INSERT INTO tblcustomer9 (tenDangNhap, matKhau, ten, sdt, email, diaChi) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getTenDangNhap());
            ps.setString(2, c.getMatKhau());
            ps.setString(3, c.getTen());
            ps.setString(4, c.getSdt());
            ps.setString(5, c.getEmail());
            ps.setString(6, c.getDiaChi());
            if (ps.executeUpdate() != 1) {
                return false;
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    c.setId(keys.getInt(1));
                }
            }
            return true;
        }
    }

    public Customer9 getCustomerById(int id) throws SQLException {
        String sql = "SELECT id, tenDangNhap, ten, sdt, email, diaChi FROM tblcustomer9 WHERE id = ?";
        try (PreparedStatement ps = connection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    private Customer9 map(ResultSet rs) throws SQLException {
        Customer9 c = new Customer9();
        c.setId(rs.getInt("id"));
        c.setTenDangNhap(rs.getString("tenDangNhap"));
        c.setTen(rs.getString("ten"));
        c.setSdt(rs.getString("sdt"));
        c.setEmail(rs.getString("email"));
        c.setDiaChi(rs.getString("diaChi"));
        return c;
    }
}
