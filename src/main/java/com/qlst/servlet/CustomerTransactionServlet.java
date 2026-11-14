package com.qlst.servlet;

import com.qlst.dao.CustomerDAO;
import com.qlst.dao.OnlineInvoiceDAO;
import com.qlst.dao.WalkInInvoiceDAO;
import com.qlst.model.Customer9;
import com.qlst.model.OnlineInvoice9;
import com.qlst.model.WalkInInvoice9;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

            double totalAmount = 0;
            int totalQuantity = 0;
            for (OnlineInvoice9 inv : online) {
                totalAmount += inv.getThanhTien();
                totalQuantity += inv.getOrder().getTongSoLuong();
            }
            for (WalkInInvoice9 inv : walkIn) {
                totalAmount += inv.getTongTien();
                totalQuantity += inv.getTongSoLuong();
            }

            List<Object> listGiaoDich = new ArrayList<>();
            int i = 0;
            int j = 0;
            while (i < online.length || j < walkIn.length) {
                boolean takeOnline = j >= walkIn.length
                        || (i < online.length && !online[i].getNgayGio().after(walkIn[j].getNgayGio()));
                listGiaoDich.add(takeOnline ? online[i++] : walkIn[j++]);
            }

            req.setAttribute("customer", customer);
            req.setAttribute("listOnlineInvoice", online);
            req.setAttribute("listWalkInInvoice", walkIn);
            req.setAttribute("listGiaoDich", listGiaoDich);
            req.setAttribute("totalAmount", totalAmount);
            req.setAttribute("totalQuantity", totalQuantity);
            req.setAttribute("ngayBatDau", from.toString());
            req.setAttribute("ngayKetThuc", to.toString());
        } catch (SQLException e) {
            throw new ServletException("Lỗi truy vấn giao dịch của khách hàng", e);
        }
        req.getRequestDispatcher("/WEB-INF/views/manager/transactions.jsp").forward(req, resp);
    }
}
