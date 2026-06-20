<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="displayName" value="${empty sessionScope.user.fullName ? 'user' : sessionScope.user.fullName}"/>

<header class="topbar">
    <form class="search-form" action="${pageContext.request.contextPath}/search" method="get">
        <input class="form-control" type="search" name="keyword" value="${param.keyword}" placeholder="Search...">
    </form>
    <div class="topbar-actions">
        <c:if test="${sessionScope.user.roleId == 1}">
            <a class="admin-dashboard-btn" href="${pageContext.request.contextPath}/AdminDashBoard">Admin Dashboard</a>
        </c:if>
        <a class="notification-btn" href="${pageContext.request.contextPath}/home">Notification</a>
        <div class="dropdown">
            <button class="user-menu-btn dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                <span class="user-avatar"></span>
                <span class="user-name">${displayName}</span>
            </button>
            <ul class="dropdown-menu dropdown-menu-end user-dropdown">
                <li class="dropdown-header">
                    <span class="user-avatar small"></span>
                    <span>${displayName}</span>
                </li>
                <li>
                    <hr class="dropdown-divider">
                </li>
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/viewprofile">View Profile</a></li>
                <li>
                    <hr class="dropdown-divider">
                </li>
                <li><a class="dropdown-item logout-item" href="logout">LOG OUT</a></li>
            </ul>
        </div>
    </div>
</header>
