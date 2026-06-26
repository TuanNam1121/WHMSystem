<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="activeMenu" value="${empty requestScope.activeMenu ? '' : requestScope.activeMenu}" />

<aside class="sidebar">
    <a class="logo-box sidebar-logo" href="${pageContext.request.contextPath}/AdminDashBoard">
        <span class="logo-mark">W</span>
        <span>Admin</span>
    </a>
    <nav class="menu">
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



<div class="sidebar" id="sidebar">
    <div class="sidebar-inner slimscroll">
        <div id="sidebar-menu" class="sidebar-menu">
            <ul>
                <li class="active">
                    <a href="${pageContext.request.contextPath}/AdminDashBoard"><img src="assets/img/icons/dashboard.svg" alt="img"><span> Dashboard</span>
                    </a>
                </li>
                <li class="submenu">
                    <a href="${pageContext.request.contextPath}/ViewUserList"><img src="assets/img/icons/product.svg"
                                                       alt="img"><span> User Management</span> <span
                                                       class="menu"></span></a>
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/ViewUserList"
                               class="menu-item ${activeMenu == 'users' ? 'active' : ''}">User Management</a></li>
                        <li><a href="${pageContext.request.contextPath}/ViewRoleList"
                               class="menu-item ${activeMenu == 'roles' ? 'active' : ''}">Role Management</a></li>
                        <li><a href="${pageContext.request.contextPath}/ViewPermissionList"
                               class="menu-item ${activeMenu == 'permissions' ? 'active' : ''}">Permission Management</a></li>
                    </ul>
                </li>
                <li class="submenu">
                    <a href="${pageContext.request.contextPath}/ViewRoleList"><img src="assets/img/icons/product.svg"
                                                       alt="img"><span>Role Management</span> <span
                                                       class="menu-arrow"></span></a>
                        
                </li>
                <li class="menu">
                    <a href="${pageContext.request.contextPath}/ViewPermissionList"><img src="assets/img/icons/product.svg"
                        class="${activeMenu == 'users' ? 'active' : ''}"
                                                       alt="img"><span>Permission Management</span></a>
                        
                </li>

            </ul>
        </div>
    </div>
</div>
