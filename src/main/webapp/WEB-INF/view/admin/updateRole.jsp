<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="activeMenu" value="roles" scope="request" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <title>${action.equals("new") ? "Add Role" : "Update Role"}</title>
    <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.jpg">
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/animate.css">
    <link rel="stylesheet" href="assets/plugins/select2/css/select2.min.css">
    <link rel="stylesheet" href="assets/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" href="assets/plugins/fontawesome/css/fontawesome.min.css">
    <link rel="stylesheet" href="assets/plugins/fontawesome/css/all.min.css">
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>
<div id="global-loader">
    <div class="whirly-loader"></div>
</div>
<div class="main-wrapper">
    <jsp:include page="/WEB-INF/common/header.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/common/sidebar.jsp"></jsp:include>
    <div class="page-wrapper">
        <div class="content">
            <div class="page-header">
                <div class="page-title">
                    <h4>${action.equals("new") ? "Add Role" : "Update Role"}</h4>
                    <h6>${action.equals("new") ? "Create a new role" : "Edit existing role"}</h6>
                </div>
            </div>

            <div class="card">
                <div class="card-body">
                    <c:if test="${not empty message}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <strong>${message}</strong>
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>
                    <c:if test="${error != null}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <strong>${error}</strong>
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <form action="${action.equals('new') ? 'AddNewRole' : 'UpdateRole'}" method="post">
                        <input type="hidden" name="id" value="${role != null ? role.roleId : ''}">
                        
                        <div class="row">
                            <div class="col-lg-6 col-sm-12">
                                <c:if test="${action != 'new'}">
                                    <div class="form-group">
                                        <label>Role ID</label>
                                        <input type="text" name="roleId" value="${role != null ? role.roleId : ''}" readonly class="form-control disabled">
                                    </div>
                                </c:if>

                                <div class="form-group">
                                    <label>Role Name</label>
                                    <input type="text" name="roleName" maxlength="100" value="${role != null ? role.roleName : ''}" required class="form-control">
                                </div>

                                <div class="form-group">
                                    <label>Status</label>
                                    <div class="d-flex gap-4 align-items-center border rounded p-3 bg-white">
                                        <div class="form-check">
                                            <input id="active-btn" class="form-check-input" type="radio" name="isActive" value="true" ${role == null || role.isActive ? 'checked' : ''}>
                                            <label for="active-btn" class="form-check-label">Activated</label>
                                        </div>
                                        <div class="form-check">
                                            <input id="deactive-btn" class="form-check-input" type="radio" name="isActive" value="false" ${role != null && !role.isActive ? 'checked' : ''}>
                                            <label for="deactive-btn" class="form-check-label">Deactivated</label>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <c:if test="${action != 'new'}">
                                <div class="col-lg-12">
                                    <div class="form-group">
                                        <label>Permissions</label>
                                        <div class="table-responsive">
                                            <table class="table">
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
                                                            <td>
                                                                <input type="checkbox" name="permission" value="${p.permissionId}" ${checked ? 'checked' : ''} style="transform: scale(1.5);">
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </c:if>

                            <div class="col-lg-12 mt-3">
                                <button type="submit" class="btn btn-submit me-2">${action.equals("new") ? "Create" : "Update"}</button>
                                <a href="ViewRoleList" class="btn btn-cancel">Cancel</a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="assets/js/jquery-3.6.0.min.js"></script>
<script src="assets/js/feather.min.js"></script>
<script src="assets/js/jquery.slimscroll.min.js"></script>
<script src="assets/js/jquery.dataTables.min.js"></script>
<script src="assets/js/dataTables.bootstrap4.min.js"></script>
<script src="assets/js/bootstrap.bundle.min.js"></script>
<script src="assets/plugins/select2/js/select2.min.js"></script>
<script src="assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
<script src="assets/plugins/sweetalert/sweetalerts.min.js"></script>
<script src="assets/js/script.js"></script>
</body>
</html>

