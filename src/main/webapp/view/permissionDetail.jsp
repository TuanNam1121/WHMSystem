<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="activeMenu" value="permissions" scope="request" />
<c:set var="pageTitle" value="${act.equals('new') ? 'Add Permission' : 'Edit Permission'}" scope="request" />
<!DOCTYPE html
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${act.equals("new") ? "Add New Permission" : "Edit Permission"}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="../assests/css/wms-theme.css" rel="stylesheet">
</head>
<body>
<div class="wrapper">
    <jsp:include page="../common/adminSidebar.jsp" />
    <main class="main-content">
        <jsp:include page="../common/userTopbar.jsp" />
        <c:if test="${not empty message}">
            <div class="alert-success">${message}</div>
        </c:if>
        <section class="form-container">
            <h2>${act.equals("new") ? "Add New Permission" : "Edit Permission"}</h2>
            <c:if test="${error != null}">
                <div class="alert-error">${error}</div>
            </c:if>
            <form action="${act.equals('new') ? 'AddNewPermission' : 'EditPermission'}" method="post">
                <input type="hidden" name="id" value="${p != null ? p.permissionId : ''}">
                <div class="form-grid">
                    <div class="form-left">
                        <label>Permission name</label>
                        <input type="text" name="permissionName" required value="${p != null ? p.permissionName : permissionName}">
                        <label>Description</label>
                        <input type="text" name="permissionDescription" required value="${p != null ? p.description : ''}">
                    </div>
                    <div class="form-right">
                        <label>Assign to roles</label>
                        <table>
                            <thead>
                                <tr><th>Role</th><th>Assign</th></tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${rolelist}" var="r">
                                    <c:set var="checked" value="false"/>
                                    <c:forEach items="${roles}" var="rr">
                                        <c:if test="${rr.roleId == r.roleId}">
                                            <c:set var="checked" value="true"/>
                                        </c:if>
                                    </c:forEach>
                                    <tr>
                                        <td>${r.roleName}</td>
                                        <td><input type="checkbox" name="role" value="${r.roleId}" ${checked ? 'checked' : ''}></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="button-area">
                    <button type="submit">${act.equals("new") ? "CREATE" : "UPDATE"}</button>
                    <a href="ViewPermissionList" class="btn-secondary" style="display:inline-flex;align-items:center;padding:8px 20px;">Cancel</a>
                </div>
            </form>
        </section>
    </main>
                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</div>
</body>
</html>
