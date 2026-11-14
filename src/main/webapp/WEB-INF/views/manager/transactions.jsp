<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN"/>
<c:set var="pageTitle" value="Giao dịch của khách hàng" scope="request"/>
<%@ include file="/WEB-INF/views/common/header.jspf" %>

<c:url var="backUrl" value="/manager/statistics/customer-revenue">
    <c:param name="ngayBatDau" value="${ngayBatDau}"/>
    <c:param name="ngayKetThuc" value="${ngayKetThuc}"/>
</c:url>

<section class="card">
    <h1>Các lần giao dịch của khách hàng</h1>
    <dl class="info">
        <dt>Mã khách hàng</dt><dd><c:out value="${customer.maKhachHang}"/></dd>
        <dt>Tên khách hàng</dt><dd><c:out value="${customer.ten}"/></dd>
        <dt>Ngày bắt đầu</dt><dd id="outNgayBatDau"><c:out value="${ngayBatDau}"/></dd>
        <dt>Ngày kết thúc</dt><dd id="outNgayKetThuc"><c:out value="${ngayKetThuc}"/></dd>
    </dl>

    <c:choose>
        <c:when test="${empty listGiaoDich}">
            <div class="alert alert-info">Khách hàng này không có giao dịch trong khoảng thời gian đã chọn.</div>
        </c:when>
        <c:otherwise>
            <table class="table" id="tblGiaoDich">
                <thead>
                <tr>
                    <th class="num">STT</th>
                    <th>Mã HĐ</th>
                    <th>Loại</th>
                    <th>Ngày giờ</th>
                    <th>Nhân viên bán</th>
                    <th class="num">Tổng số lượng</th>
                    <th class="num">Tổng tiền (VNĐ)</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="t" items="${listGiaoDich}" varStatus="st">
                    <c:set var="isOnline" value="${t['class'].simpleName eq 'OnlineInvoice9'}"/>
                    <tr>
                        <td class="num">${st.count}</td>
                        <c:choose>
                            <c:when test="${isOnline}">
                                <td>HDTT<fmt:formatNumber value="${t.id}" pattern="000"/></td>
                                <td>Trực tuyến</td>
                                <td><fmt:formatDate value="${t.ngayGio}" pattern="HH:mm dd/MM/yyyy"/></td>
                                <td>—</td>
                                <td class="num">${t.order.tongSoLuong}</td>
                                <td class="num"><fmt:formatNumber value="${t.thanhTien}" pattern="#,##0"/></td>
                            </c:when>
                            <c:otherwise>
                                <td>HD<fmt:formatNumber value="${t.id}" pattern="000"/></td>
                                <td>Tại quầy</td>
                                <td><fmt:formatDate value="${t.ngayGio}" pattern="HH:mm dd/MM/yyyy"/></td>
                                <td>SS<fmt:formatNumber value="${t.salesStaff.id}" pattern="000"/> - <c:out value="${t.salesStaff.ten}"/></td>
                                <td class="num">${t.tongSoLuong}</td>
                                <td class="num"><fmt:formatNumber value="${t.tongTien}" pattern="#,##0"/></td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <td colspan="5" class="num"><b>Tổng cộng</b></td>
                    <td class="num"><b>${totalQuantity}</b></td>
                    <td class="num"><b><fmt:formatNumber value="${totalAmount}" pattern="#,##0"/></b></td>
                </tr>
                </tfoot>
            </table>
        </c:otherwise>
    </c:choose>

    <a class="btn btn-ghost" id="quayLai" href="<c:out value="${backUrl}"/>">← Quay lại thống kê</a>
</section>

