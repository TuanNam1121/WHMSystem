<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="activeMenu" value="${empty requestScope.activeMenu ? '' : requestScope.activeMenu}" />

<aside class="sidebar">
    <a class="logo-box sidebar-logo" href="${pageContext.request.contextPath}/home">
        <span class="logo-mark">W</span>
        <span>Admin</span>
    </a>
    <nav class="menu">
        <a href="${pageContext.request.contextPath}/home"
           class="menu-item ${activeMenu == 'userhome' ? 'active' : ''}">Home</a>
        <a href="${pageContext.request.contextPath}/AdminDashBoard"
           class="menu-item ${activeMenu == 'dashboard' ? 'active' : ''}">Dashboard</a>
        <a href="${pageContext.request.contextPath}/ViewUserList"
           class="menu-item ${activeMenu == 'users' ? 'active' : ''}">User Management</a>
        <a href="${pageContext.request.contextPath}/ViewRoleList"
           class="menu-item ${activeMenu == 'roles' ? 'active' : ''}">Role Management</a>
        <a href="${pageContext.request.contextPath}/ViewPermissionList"
           class="menu-item ${activeMenu == 'permissions' ? 'active' : ''}">Permission Management</a>
    </nav>
</aside>
