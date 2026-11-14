<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Đăng ký thành viên" scope="request"/>
<%@ include file="/WEB-INF/views/common/header.jspf" %>

<section class="card narrow">
    <h1>Đăng ký thành viên</h1>
    <p class="muted">Nhập thông tin cá nhân và nhấn <b>Đăng ký</b>. Các trường đều bắt buộc.</p>

    <c:if test="${not empty errors.general}">
        <div class="alert alert-error" id="outLoi"><c:out value="${errors.general}"/></div>
    </c:if>

    <form method="post" action="<c:url value='/register'/>" novalidate autocomplete="off">
        <div class="field ${not empty errors.ten ? 'has-error' : ''}">
            <label for="inTen">Họ tên</label>
            <input id="inTen" name="inTen" type="text" maxlength="255" value="<c:out value='${form.ten}'/>">
            <c:if test="${not empty errors.ten}"><small class="err"><c:out value="${errors.ten}"/></small></c:if>
        </div>

        <div class="row">
            <div class="field ${not empty errors.sdt ? 'has-error' : ''}">
                <label for="inSdt">Số điện thoại</label>
                <input id="inSdt" name="inSdt" type="tel" maxlength="11" value="<c:out value='${form.sdt}'/>">
                <c:if test="${not empty errors.sdt}"><small class="err"><c:out value="${errors.sdt}"/></small></c:if>
            </div>
            <div class="field ${not empty errors.email ? 'has-error' : ''}">
                <label for="inEmail">Email</label>
                <input id="inEmail" name="inEmail" type="email" maxlength="255" value="<c:out value='${form.email}'/>">
                <c:if test="${not empty errors.email}"><small class="err"><c:out value="${errors.email}"/></small></c:if>
            </div>
        </div>

        <div class="field ${not empty errors.diaChi ? 'has-error' : ''}">
            <label for="inDiaChi">Địa chỉ</label>
            <input id="inDiaChi" name="inDiaChi" type="text" maxlength="255" value="<c:out value='${form.diaChi}'/>">
            <c:if test="${not empty errors.diaChi}"><small class="err"><c:out value="${errors.diaChi}"/></small></c:if>
        </div>

        <div class="field ${not empty errors.tenDangNhap ? 'has-error' : ''}">
            <label for="inTenDangNhap">Tên đăng nhập</label>
            <input id="inTenDangNhap" name="inTenDangNhap" type="text" maxlength="50" value="<c:out value='${form.tenDangNhap}'/>">
            <c:if test="${not empty errors.tenDangNhap}"><small class="err"><c:out value="${errors.tenDangNhap}"/></small></c:if>
        </div>

        <div class="row">
            <div class="field ${not empty errors.matKhau ? 'has-error' : ''}">
                <label for="inMatKhau">Mật khẩu</label>
                <input id="inMatKhau" name="inMatKhau" type="password" maxlength="72" autocomplete="new-password">
                <c:if test="${not empty errors.matKhau}"><small class="err"><c:out value="${errors.matKhau}"/></small></c:if>
            </div>
            <div class="field ${not empty errors.xacNhanMatKhau ? 'has-error' : ''}">
                <label for="inXacNhanMatKhau">Xác nhận mật khẩu</label>
                <input id="inXacNhanMatKhau" name="inXacNhanMatKhau" type="password" maxlength="72" autocomplete="new-password">
                <c:if test="${not empty errors.xacNhanMatKhau}"><small class="err"><c:out value="${errors.xacNhanMatKhau}"/></small></c:if>
            </div>
        </div>

        <button type="submit" class="btn" id="subDangKy">Đăng ký</button>
    </form>
</section>

