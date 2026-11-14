<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Trang chủ" scope="request"/>
<%@ include file="/WEB-INF/views/common/header.jspf" %>
<section class="card hero">
    <h1>Hệ thống quản lý siêu thị điện máy</h1>
    <p>Hai chức năng được triển khai trong bản này:</p>
    <div class="grid2">
        <a class="tile" href="<c:url value='/register'/>">
            <h2>👤 Đăng ký thành viên</h2>
            <p>Dành cho khách hàng: nhập thông tin cá nhân để tạo tài khoản thành viên.</p>
        </a>
        <a class="tile" href="<c:url value='/manager/statistics/customer-revenue'/>">
            <h2>📊 Thống kê khách hàng theo doanh thu</h2>
            <p>Dành cho nhân viên quản lí (cần đăng nhập): xem doanh thu và chi tiết giao dịch từng khách.</p>
        </a>
    </div>
</section>
