<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="activeMenu" value="roles" scope="request" />
<c:set var="pageTitle" value="Role Management" scope="request" />
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Role Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="assests/css/wms-theme.css" rel="stylesheet">
</head>
<body>
<div class="wrapper">
    <jsp:include page="../common/adminSidebar.jsp" />
    <main class="main-content">
        <jsp:include page="../common/userTopbar.jsp" />
        <c:if test="${not empty message}">
            <div class="alert-success">${message}</div>
        </c:if>
        <section class="table-container">
            <div class="table-header">
                <h2>Role List</h2>
                <div class="header-actions">
                    <form action="${pageContext.request.contextPath}/ViewRoleList" method="get">

                        <!-- Search name -->
                        <input
                                id="keyword"
                                type="text"
                                name="keyword"
                                class="filter-input"
                                placeholder="Search by rolename"
                                value="${param.keyword}"
                        >

                        <select name="sortBy" class="filter-select" id="sortBy">
                            <option value>Sort</option>
                            <option value="rolename"
                            ${param.sortBy == 'rolename' ? 'selected' : ''}>
                                Role Name
                            </option>
                            <option value="isactive"
                            ${param.sortBy == 'isactive' ? 'selected' : ''}>
                                Active
                            </option>
                        </select>



                        <!-- Search button -->
                        <button type="submit" class="search-btn">
                            Search
                        </button>
                    </form>
                    <button class="add-btn">
                        <a href="${pageContext.request.contextPath}/AddNewRole">Add new role</a>
                    </button>
                </div>

            </div>
            <table>
                <thead>
                    <tr>
                        <th>RoleId</th>
                        <th>RoleName</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${rolelist}" var="r">
                        <tr>
                            <td>${r.roleId}</td>
                            <td>${r.roleName}</td>
                            <td><input type="checkbox" ${r.isActive ? 'checked' : ''} disabled></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/UpdateRole?id=${r.roleId}">Edit</a>
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
