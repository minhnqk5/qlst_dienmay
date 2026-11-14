<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Chọn loại thống kê" scope="request"/>
<%@ include file="/WEB-INF/views/common/header.jspf" %>
<section class="card narrow">
    <h1>Xem báo cáo</h1>
    <p class="muted">Chọn loại thống kê:</p>
    <ul class="menu" id="loaiThongKe">
        <li class="disabled"><span>Thống kê doanh thu</span></li>
        <li class="disabled"><span>Thống kê mặt hàng theo doanh thu</span></li>
        <li><a href="<c:url value='/manager/statistics/customer-revenue'/>">Thống kê khách hàng theo doanh thu</a></li>
    </ul>
    <a class="btn btn-ghost" id="quayLai" href="<c:url value='/manager/home'/>">← Quay lại</a>
</section>
