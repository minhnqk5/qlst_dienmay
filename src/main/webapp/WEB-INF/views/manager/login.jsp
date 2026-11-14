<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Đăng nhập" scope="request"/>
<%@ include file="/WEB-INF/views/common/header.jspf" %>
<section class="card narrow">
    <h1>Nhân viên quản lí đăng nhập</h1>
    <c:if test="${not empty error}">
        <div class="alert alert-error"><c:out value="${error}"/></div>
    </c:if>
    <form method="post" action="<c:url value='/login'/>">
        <div class="field">
            <label for="tenDangNhap">Tên đăng nhập</label>
            <input id="tenDangNhap" name="tenDangNhap" type="text" required value="<c:out value='${tenDangNhap}'/>">
        </div>
        <div class="field">
            <label for="matKhau">Mật khẩu</label>
            <input id="matKhau" name="matKhau" type="password" required>
        </div>
        <button type="submit" class="btn">Đăng nhập</button>
    </form>
</section>
