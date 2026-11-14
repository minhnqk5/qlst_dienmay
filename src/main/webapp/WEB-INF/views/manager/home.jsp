<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Trang quản lí" scope="request"/>
<%@ include file="/WEB-INF/views/common/header.jspf" %>
<section class="card narrow center">
    <h1>Trang chính nhân viên quản lí</h1>
    <p class="muted">
        Xin chào
        <b><c:out value="${sessionScope.manager.tenDangNhap}"/></b>.
    </p>
    <a class="btn" id="xemBaoCao" href="<c:url value='/manager/statistics'/>">Xem báo cáo</a>
</section>
