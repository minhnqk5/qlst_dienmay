package com.qlst.servlet;

import com.qlst.dao.CustomerRevenueStatisticDAO;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/manager/statistics/customer-revenue")
public class CustomerRevenueServlet extends HttpServlet {

    private static final String VIEW = "/WEB-INF/views/manager/customerRevenue.jsp";
    private CustomerRevenueStatisticDAO statisticDAO;

    @Override
    public void init() {
        statisticDAO = new CustomerRevenueStatisticDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String start = req.getParameter("ngayBatDau");
        String end = req.getParameter("ngayKetThuc");
        req.setAttribute("ngayBatDau", start == null ? "" : start);
        req.setAttribute("ngayKetThuc", end == null ? "" : end);

        if (start != null || end != null) {
            try {
                Date from = Date.valueOf(start);
                Date to = Date.valueOf(end);
                if (from.after(to)) {
                    req.setAttribute("error", "Thời gian không hợp lệ: ngày bắt đầu phải trước hoặc bằng ngày kết thúc.");
                } else {
                    req.setAttribute("listDoanhThuKH", statisticDAO.getCustomerRevenueStatistics(from, to));
                    req.setAttribute("searched", true);
                }
            } catch (IllegalArgumentException e) {
                req.setAttribute("error", "Vui lòng chọn ngày bắt đầu và ngày kết thúc hợp lệ.");
            } catch (SQLException e) {
                throw new ServletException("Lỗi truy vấn thống kê khách hàng theo doanh thu", e);
            }
        }
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }
}
