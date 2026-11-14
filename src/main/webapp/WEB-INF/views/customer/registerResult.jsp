<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Kết quả đăng ký" scope="request"/>
<%@ include file="/WEB-INF/views/common/header.jspf" %>
<section class="card narrow center">
    <div class="big-ok">✔</div>
    <h1 id="outDangKyThanhCong">Đăng ký thành viên thành công</h1>
    <p>Chào mừng <b><c:out value="${sessionScope.SessionUsername}"/></b>! Thông tin của bạn đã được lưu vào hệ thống.</p>
    <a class="btn" href="<c:url value='/'/>">Về trang chủ</a>
</section>
