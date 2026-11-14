package com.qlst.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/manager/home", "/manager/statistics"})
public class ManagerPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String view = "/manager/home".equals(req.getServletPath())
                ? "/WEB-INF/views/manager/home.jsp"
                : "/WEB-INF/views/manager/statisticsMenu.jsp";
        req.getRequestDispatcher(view).forward(req, resp);
    }
}
