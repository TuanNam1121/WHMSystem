<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="activeMenu" value="permissions" scope="request" />
<c:set var="pageTitle" value="Permission Management" scope="request" />
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Permission Management</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="assests/css/wms-theme.css" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="common/adminSidebar.jsp" />
            <main class="main-content">
                <jsp:include page="common/userTopbar.jsp" />
                <c:if test="${not empty message}">
                    <div class="alert-success">${message}</div>
                </c:if>
                <section class="content-box">
                    <div class="table-header">
                        <h2>Permission List</h2>
                        <form class="search-form" action="${pageContext.request.contextPath}/ViewPermissionList" method="get">
                            <input class="form-control" type="search" name="keyword" value="${param.keyword}" placeholder="Search...">

                            <select name="roleid" class="filter-select" id="roleId">
                                <option value="0" ${roleid==0?'selected':''}>ALL</option>
                                <c:forEach items="${roles}" var="r">
                                    <option value="${r.roleId}" ${roleid==r.roleId?'selected':''}>${r.roleName}</option>
                                </c:forEach>
                            </select>
                            <input type="submit" value="Search">
                        </form>
                        <button class="add-btn" >
                            <a href="PermissionDetail?action=new">Create new permission</a>
                        </button>
                    </div>
                    <table>
                        <thead>
                            <tr>
                                <th>Permission ID</th>
                                <th>Permission name</th>
                                <th>Description</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${permissions}" var="p">
                                <tr>
                                    <td>${p.permissionId}</td>
                                    <td>${p.permissionName}</td>
                                    <td>${p.description}</td>
                                    <td><a href="PermissionDetail?action=edit&permissionId=${p.permissionId}">Edit</a></td>
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
