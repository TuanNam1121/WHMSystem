<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="activeMenu" value="roles" scope="request" />
<c:set var="pageTitle" value="${action.equals('new') ? 'Add Role' : 'Update Role'}" scope="request" />
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${action.equals("new") ? "Add Role" : "Update Role"}</title>
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
        <section class="form-container">
            <h2>${action.equals("new") ? "Add Role" : "Update Role"}</h2>
            <c:if test="${error != null}">
                <div class="alert-error">${error}</div>
            </c:if>
            <form action="${action.equals('new') ? 'AddNewRole' : 'UpdateRole'}" method="post">
                <input type="hidden" name="id" value="${role != null ? role.roleId : ''}">
                <div class="form-grid">
                    <div class="form-left">
                        <c:if test="${action != 'new'}">
                            <label>Role id</label>
                            <input type="text" name="roleId" value="${role != null ? role.roleId : ''}" readonly>
                        </c:if>
                        <label>Role name</label>
                        <input type="text" name="roleName" maxlength="100" value="${role != null ? role.roleName : ''}">
                        <label>Status</label>
                        <label style="display:inline-flex;margin-right:16px;">
                            <input type="radio" name="isActive" value="true" ${role.isActive ? 'checked' : ''}> Activated
                        </label>
                        <label style="display:inline-flex;">
                            <input type="radio" name="isActive" value="false" ${role.isActive ? '' : 'checked'}> Deactivated
                        </label>
                    </div>
                    <c:if test="${action != 'new'}">
                        <div class="form-right">
                            <label>Permissions</label>
                            <table>
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Name</th>
                                        <th>Description</th>
                                        <th>Included</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${permissions}" var="p">
                                        <c:set var="checked" value="false"/>
                                        <c:forEach items="${includePermissions}" var="ip">
                                            <c:if test="${ip.permissionId == p.permissionId}">
                                                <c:set var="checked" value="true"/>
                                            </c:if>
                                        </c:forEach>
                                        <tr>
                                            <td>${p.permissionId}</td>
                                            <td>${p.permissionName}</td>
                                            <td>${p.description}</td>
                                            <td><input type="checkbox" name="permission" value="${p.permissionId}" ${checked ? 'checked' : ''}></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>
                </div>
                <div class="button-area">
                    <button type="submit">${action.equals("new") ? "CREATE" : "UPDATE"}</button>
                    <a href="ViewRoleList" class="btn-secondary" style="display:inline-flex;align-items:center;padding:8px 20px;">Cancel</a>
                </div>
            </form>
        </section>
    </main>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</div>
</body>
</html>
