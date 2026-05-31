<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="activeMenu" value="users" scope="request"/>
<c:set var="pageTitle" value="User Management" scope="request"/>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="assests/css/wms-theme.css" rel="stylesheet">
</head>
<body>
<div class="wrapper">
    <jsp:include page="../common/adminSidebar.jsp"/>
    <main class="main-content">
        <jsp:include page="../common/userTopbar.jsp"/>
        <c:if test="${not empty message}">
            <div class="alert-success">${message}</div>
        </c:if>
        <section class="table-container">
            <div class="table-header">
                <h2>User List</h2>
                <button class="add-btn">
                    <a href="${pageContext.request.contextPath}/AddNewUser">Add new user</a>
                </button>
            </div>
            <div class="filter-bar">
                <form action="${pageContext.request.contextPath}/ViewUserList" method="get">

                    <!-- Search name -->
                    <input
                            id="keyword"
                            type="text"
                            name="keyword"
                            class="filter-input"
                            placeholder="Search by name"
                            value="${param.keyword}"
                    >

                    <select name="sortBy" class="filter-select" id="sortBy">
                        <option value="">Sort</option>
                        <option value="role"
                        ${param.sortBy == 'userid' ? 'selected' : ''}>
                            Role
                        </option>
                        <option value="username"
                        ${param.sortBy == 'username' ? 'selected' : ''}>
                            User Name
                        </option>
                        <option value="isactive"
                        ${param.sortBy == 'isactive' ? 'selected' : ''}>
                            Active
                        </option>
                    </select>

                    <!-- Role filter -->
                    <select name="roleId" class="filter-select" id="roleId">
                        <option value="">Role</option>

                        <c:forEach items="${roleList}" var="r">
                            <option
                                    value="${r.roleId}"
                                ${param.roleId == r.roleId.toString() ? 'selected' : ''}>
                                    ${r.roleName}
                            </option>
                        </c:forEach>
                    </select>

                    <!-- Search button -->
                    <button type="submit" class="search-btn">
                        Search
                    </button>
                </form>
            </div>
            <table>
                <thead>
                <tr>
                    <th>UserId</th>
                    <th>UserName</th>
                    <th>FullName</th>
                    <th>RoleName</th>
                    <th>Gender</th>
                    <th>Phone</th>
                    <th>Email</th>
                    <th>Active</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${userlist}" var="u">
                    <tr>
                        <td>${u.id}</td>
                        <td>${u.userName}</td>
                        <td>${u.fullName}</td>
                        <td>${roleDao.getRoleNamFromRoleID(u.roleId)}</td>
                        <td>${u.gender}</td>
                        <td>${u.phone}</td>
                        <td>${u.email}</td>
                        <td><input type="checkbox" ${u.isActive ? 'checked' : ''} disabled></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/UpdateUserInformation?id=${u.id}">Update</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </section>
    </main>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</div>
</body>
</html>
