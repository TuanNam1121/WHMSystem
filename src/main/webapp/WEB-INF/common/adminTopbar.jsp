<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="${empty requestScope.pageTitle ? 'Admin' : requestScope.pageTitle}" />

<header class="topbar">
    <div class="breadcrumb">Homepage / <strong>${pageTitle}</strong></div>
    <div class="topbar-actions">
        <div class="admin-box">
            <div class="avatar"></div>
            <span>admin</span>
        </div>
    </div>
</header>
