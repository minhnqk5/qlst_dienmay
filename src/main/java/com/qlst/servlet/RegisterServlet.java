package com.qlst.servlet;

import com.qlst.dao.CustomerDAO;
import com.qlst.model.Customer9;
import com.qlst.util.PasswordUtil;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final String FORM = "/WEB-INF/views/customer/register.jsp";
    private static final String RESULT = "/WEB-INF/views/customer/registerResult.jsp";

    private static final Pattern PHONE = Pattern.compile("^0\\d{9,10}$");
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern USERNAME = Pattern.compile("^[A-Za-z0-9_.]{4,50}$");
    private static final Pattern PASSWORD = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d).{6,72}$");

    private static final String[][] FIELDS = {
        {"ten", "Họ tên"}, {"sdt", "Số điện thoại"}, {"email", "Email"},
        {"diaChi", "Địa chỉ"}, {"tenDangNhap", "Tên đăng nhập"}};

    private CustomerDAO customerDAO;

    @Override
    public void init() {
        customerDAO = new CustomerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(FORM).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        Map<String, String> form = new LinkedHashMap<>();
        for (String[] f : FIELDS) {
            String v = req.getParameter("in" + Character.toUpperCase(f[0].charAt(0)) + f[0].substring(1));
            form.put(f[0], v == null ? "" : v.trim());
        }
        String matKhau = req.getParameter("inMatKhau") == null ? "" : req.getParameter("inMatKhau");
        String xacNhan = req.getParameter("inXacNhanMatKhau") == null ? "" : req.getParameter("inXacNhanMatKhau");

        Map<String, String> errors = validate(form, matKhau, xacNhan);

        if (errors.isEmpty()) {
            Customer9 c = new Customer9(form.get("tenDangNhap"), null,
                    form.get("ten"), form.get("sdt"), form.get("email"), form.get("diaChi"));
            try {
                Customer9 daCo = customerDAO.getCustomer(c);
                if (daCo != null) {
                    if (c.getTenDangNhap().equalsIgnoreCase(daCo.getTenDangNhap())) {
                        errors.put("tenDangNhap", "Tên đăng nhập đã tồn tại, vui lòng chọn tên khác.");
                    }
                    if (c.getSdt().equals(daCo.getSdt())) {
                        errors.put("sdt", "Số điện thoại đã được đăng ký, vui lòng dùng số khác.");
                    }
                } else {
                    c.setMatKhau(PasswordUtil.hash(matKhau));
                    if (!customerDAO.saveCustomer(c)) {
                        errors.put("general", "Chưa lưu được tài khoản. Vui lòng thử lại.");
                    }
                }
            } catch (SQLIntegrityConstraintViolationException dup) {
                errors.put("tenDangNhap", "Tên đăng nhập hoặc số điện thoại đã tồn tại.");
            } catch (SQLException e) {
                getServletContext().log("Lỗi CSDL khi đăng ký thành viên", e);
                errors.put("general", "Hệ thống đang gặp sự cố, vui lòng thử lại sau.");
            }
        }

        if (!errors.isEmpty()) {
            req.setAttribute("form", form);
            req.setAttribute("errors", errors);
            req.getRequestDispatcher(FORM).forward(req, resp);
            return;
        }
        req.getSession(true).setAttribute("SessionUsername", form.get("ten"));
        req.getRequestDispatcher(RESULT).forward(req, resp);
    }

    private Map<String, String> validate(Map<String, String> f, String matKhau, String xacNhan) {
        Map<String, String> e = new LinkedHashMap<>();
        for (String[] x : FIELDS) {
            if (f.get(x[0]).isEmpty()) {
                e.put(x[0], x[1] + " không được để trống.");
            }
        }
        if (matKhau.isEmpty()) {
            e.put("matKhau", "Mật khẩu không được để trống.");
        }
        if (xacNhan.isEmpty()) {
            e.put("xacNhanMatKhau", "Vui lòng nhập xác nhận mật khẩu.");
        }
        if (!e.containsKey("sdt") && !PHONE.matcher(f.get("sdt")).matches()) {
            e.put("sdt", "Số điện thoại không hợp lệ (bắt đầu bằng 0, gồm 10–11 chữ số).");
        }
        if (!e.containsKey("email") && !EMAIL.matcher(f.get("email")).matches()) {
            e.put("email", "Email không hợp lệ.");
        }
        if (!e.containsKey("tenDangNhap") && !USERNAME.matcher(f.get("tenDangNhap")).matches()) {
            e.put("tenDangNhap", "Tên đăng nhập gồm 4–50 ký tự: chữ không dấu, số, dấu chấm hoặc gạch dưới.");
        }
        if (!e.containsKey("matKhau") && !PASSWORD.matcher(matKhau).matches()) {
            e.put("matKhau", "Mật khẩu gồm 6–72 ký tự, có ít nhất 1 chữ cái và 1 chữ số.");
        }
        if (!e.containsKey("matKhau") && !e.containsKey("xacNhanMatKhau") && !matKhau.equals(xacNhan)) {
            e.put("xacNhanMatKhau", "Mật khẩu xác nhận không khớp.");
        }
        return e;
    }
}
