package com.qlst.servlet;

import com.qlst.dao.MemberDAO;
import com.qlst.filter.AuthFilter;
import com.qlst.model.Manager9;
import com.qlst.util.PasswordUtil;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet({"/login", "/logout"})
public class LoginServlet extends HttpServlet {

    private static final String VIEW = "/WEB-INF/views/login.jsp";
    private MemberDAO memberDAO;

    @Override
    public void init() {
        memberDAO = new MemberDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("/logout".equals(req.getServletPath())) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        if ("/logout".equals(req.getServletPath())) {
            HttpSession s = req.getSession(false);
            if (s != null) {
                s.invalidate();
            }
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String username = req.getParameter("tenDangNhap") == null ? "" : req.getParameter("tenDangNhap").trim();
        String password = req.getParameter("matKhau") == null ? "" : req.getParameter("matKhau");

        Manager9 manager = null;
        try {
            Manager9 m = new Manager9();
            m.setTenDangNhap(username);
            manager = memberDAO.getManager(m);
        } catch (SQLException e) {
            getServletContext().log("Lỗi CSDL khi đăng nhập", e);
            req.setAttribute("error", "Hệ thống đang gặp sự cố, vui lòng thử lại sau.");
            req.getRequestDispatcher(VIEW).forward(req, resp);
            return;
        }

        if (manager == null || !PasswordUtil.verify(password, manager.getMatKhau())) {
            req.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng.");
            req.setAttribute("tenDangNhap", username);
            req.getRequestDispatcher(VIEW).forward(req, resp);
            return;
        }

        HttpSession old = req.getSession(false);
        if (old != null) {
            old.invalidate();
        }
        manager.setMatKhau(null);
        HttpSession session = req.getSession(true);
        session.setAttribute(AuthFilter.SESSION_MANAGER, manager);
        session.setAttribute("SessionUsername", manager.getTenDangNhap());
        resp.sendRedirect(req.getContextPath() + "/manager/home");
    }
}
