<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="currentPage" value="${empty requestScope.currentPage ? 'overview' : requestScope.currentPage}" />

<aside class="app-sidebar">
    <a class="sidebar-logo" href="${pageContext.request.contextPath}/home">
        <span class="logo-mark">W</span>
        <span class="logo-text">WMS</span>
    </a>
    <nav class="sidebar-nav" aria-label="Main navigation">
        <a class="sidebar-link ${currentPage == 'home' ? 'active' : ''}"
           href="${pageContext.request.contextPath}/home">Home</a>
        <a class="sidebar-link ${currentPage == 'overview' ? 'active' : ''}"
           href="${pageContext.request.contextPath}/home">Tổng quan</a>
        <a class="sidebar-link ${currentPage == 'orders' ? 'active' : ''}"
           href="${pageContext.request.contextPath}/home">Đơn hàng</a>
        <a class="sidebar-link ${currentPage == 'shipping' ? 'active' : ''}"
           href="${pageContext.request.contextPath}/home">Vận chuyển</a>
        <a class="sidebar-link ${currentPage == 'products' ? 'active' : ''}"
           href="${pageContext.request.contextPath}/home">Sản phẩm</a>
        <a class="sidebar-link ${currentPage == 'warehouse' ? 'active' : ''}"
           href="${pageContext.request.contextPath}/home">Quản lý kho</a>
        <a class="sidebar-link ${currentPage == 'reports' ? 'active' : ''}"
           href="${pageContext.request.contextPath}/#">Báo cáo</a>
    </nav>
</aside>
