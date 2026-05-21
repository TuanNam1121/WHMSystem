<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="roleDao" class="com.swp.whmsystem.dal.RoleDAO" scope="page" />
<c:set var="displayName" value="${empty sessionScope.user.fullName ? 'user' : sessionScope.user.fullName}" />
<c:set var="profileUser" value="${empty user ? sessionScope.user : user}" />
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>View Profile</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="assests/css/wms-theme.css" rel="stylesheet">
    </head>
    <body class="profile-page">
        <header class="topbar profile-topbar" style="max-width:720px;margin:0 auto 16px;">
            <a class="back-home-link" href="${pageContext.request.contextPath}/home">← Home</a>
            <div class="topbar-actions">
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
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/viewprofile">View Profile</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item logout-item" href="logout">LOG OUT</a></li>
                    </ul>
                </div>
            </div>
        </header>
        <article class="profile-card">
            <header class="profile-card-header">
                <span>Account: <strong>${displayName}</strong></span>
            </header>
            <section class="profile-section">
                <h2 class="profile-section-title">Basic Information</h2>
                <div class="profile-grid">
                    <div class="profile-field">
                        <label>UserName</label>
                        <div class="profile-value">${profileUser.userName}</div>
                    </div>
                    <div class="profile-field">
                        <label>Phone</label>
                        <div class="profile-value">${profileUser.phone}</div>
                    </div>
                    <div class="profile-field">
                        <label>FullName</label>
                        <div class="profile-value">${profileUser.fullName}</div>
                    </div>
                    <div class="profile-field">
                        <label>Email</label>
                        <div class="profile-value">${profileUser.email}</div>
                    </div>
                    <div class="profile-text">
                        <span>Role:</span>
                        <strong>${roleDao.getRoleNamFromRoleID(profileUser.roleId)}</strong>
                    </div>
                    <div class="profile-text">
                        <span>Gender:</span>
                        <strong>${profileUser.gender}</strong>
                    </div>
                </div>
            </section>
            <section class="profile-section">
                <h2 class="profile-section-title">Security</h2>
                <div class="profile-row">
                    <span class="profile-row-label">Password</span>
                    <a class="profile-action-link" href="changePassword">Change password</a>
                </div>
            </section>
        </article>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
