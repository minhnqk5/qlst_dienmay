package com.qlst.servlet;

import com.qlst.dao.CustomerDAO;
import com.qlst.dao.OnlineInvoiceDAO;
import com.qlst.dao.WalkInInvoiceDAO;
import com.qlst.model.Customer9;
import com.qlst.model.CustomerTransactionStatistics9;
import com.qlst.model.OnlineInvoice9;
import com.qlst.model.WalkInInvoice9;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/manager/statistics/customer-revenue/transactions")
public class CustomerTransactionServlet extends HttpServlet {

    private CustomerDAO customerDAO;
    private OnlineInvoiceDAO onlineInvoiceDAO;
    private WalkInInvoiceDAO walkInInvoiceDAO;

    @Override
    public void init() {
        customerDAO = new CustomerDAO();
        onlineInvoiceDAO = new OnlineInvoiceDAO();
        walkInInvoiceDAO = new WalkInInvoiceDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int customerId;
        Date from;
        Date to;
        try {
            customerId = Integer.parseInt(req.getParameter("customerId"));
            from = Date.valueOf(req.getParameter("ngayBatDau"));
            to = Date.valueOf(req.getParameter("ngayKetThuc"));
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            Customer9 customer = customerDAO.getCustomerById(customerId);
            if (customer == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            OnlineInvoice9[] online = onlineInvoiceDAO.getOnlineInvoice(customerId, from, to);
            WalkInInvoice9[] walkIn = walkInInvoiceDAO.getWalkInInvoice(customer.getSdt(), from, to);

            int tongSoLuong = 0;
            double tongTien = 0;
            for (OnlineInvoice9 invoice : online) {
                tongSoLuong += invoice.getOrder().getTongSoLuong();
                tongTien += invoice.getThanhTien();
            }
            for (WalkInInvoice9 invoice : walkIn) {
                tongSoLuong += invoice.getTongSoLuong();
                tongTien += invoice.getTongTien();
            }

            CustomerTransactionStatistics9 transaction =
                    new CustomerTransactionStatistics9(customer, tongSoLuong, tongTien, online, walkIn);

            req.setAttribute("transaction", transaction);
            req.setAttribute("ngayBatDau", from.toString());
            req.setAttribute("ngayKetThuc", to.toString());
        } catch (SQLException e) {
            throw new ServletException("Lỗi truy vấn giao dịch của khách hàng", e);
        }
        req.getRequestDispatcher("/WEB-INF/views/manager/transactions.jsp").forward(req, resp);
    }
}
