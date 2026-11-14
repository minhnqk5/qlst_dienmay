<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN"/>
<c:set var="pageTitle" value="Thống kê khách hàng theo doanh thu" scope="request"/>
<%@ include file="/WEB-INF/views/common/header.jspf" %>

<section class="card">
    <h1>Thống kê khách hàng theo doanh thu</h1>

    <form class="filter" method="get" action="<c:url value='/manager/statistics/customer-revenue'/>">
        <div class="field">
            <label for="ngayBatDau">Ngày bắt đầu</label>
            <input id="ngayBatDau" name="ngayBatDau" type="date" value="<c:out value='${ngayBatDau}'/>">
        </div>
        <div class="field">
            <label for="ngayKetThuc">Ngày kết thúc</label>
            <input id="ngayKetThuc" name="ngayKetThuc" type="date" value="<c:out value='${ngayKetThuc}'/>">
        </div>
        <button type="submit" class="btn" id="btnXem">Xem</button>
    </form>

    <c:if test="${not empty error}">
        <div class="alert alert-error"><c:out value="${error}"/></div>
    </c:if>

    <table class="table" id="tblTKDoanhThuKH">
        <thead>
        <tr>
            <th class="num">STT</th>
            <th>Mã khách hàng</th>
            <th>Tên khách hàng</th>
            <th class="num">Số lần giao dịch</th>
            <th class="num">Tổng số lượng mua</th>
            <th class="num">Tổng doanh thu (VNĐ)</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="s" items="${listDoanhThuKH}" varStatus="st">
            <c:url var="detailUrl" value="/manager/statistics/customer-revenue/transactions">
                <c:param name="customerId" value="${s.id}"/>
                <c:param name="ngayBatDau" value="${ngayBatDau}"/>
                <c:param name="ngayKetThuc" value="${ngayKetThuc}"/>
            </c:url>
            <tr class="clickable" onclick="location.href='<c:out value="${detailUrl}"/>'">
                <td class="num">${st.count}</td>
                <td><a class="lnkcustomerDetail" href="<c:out value="${detailUrl}"/>"><c:out value="${s.maKhachHang}"/></a></td>
                <td><a class="lnkcustomerDetail" href="<c:out value="${detailUrl}"/>"><c:out value="${s.ten}"/></a></td>
                <td class="num">${s.soGiaoDich}</td>
                <td class="num">${s.tongSoLuong}</td>
                <td class="num"><fmt:formatNumber value="${s.doanhThuTrenKhachHang}" pattern="#,##0"/></td>
            </tr>
        </c:forEach>
        <c:if test="${empty listDoanhThuKH}">
            <tr><td colspan="6" class="empty">
                <c:choose>
                    <c:when test="${searched}">Không có khách hàng nào có giao dịch trong khoảng thời gian đã chọn.</c:when>
                    <c:otherwise>Chọn ngày bắt đầu, ngày kết thúc rồi nhấn <b>Xem</b> để hiển thị thống kê.</c:otherwise>
                </c:choose>
            </td></tr>
        </c:if>
        </tbody>
        <c:if test="${not empty listDoanhThuKH}">
            <tfoot>
            <tr>
                <td colspan="5" class="num"><b>Tổng doanh thu (${listDoanhThuKH[0].tongSoKhachMuaHang} khách hàng)</b></td>
                <td class="num"><b><fmt:formatNumber value="${listDoanhThuKH[0].tongDoanhThu}" pattern="#,##0"/></b></td>
            </tr>
            </tfoot>
        </c:if>
    </table>

    <a class="btn btn-ghost" id="quayLai" href="<c:url value='/manager/statistics'/>">← Quay lại</a>
</section>

