<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="activeMenu" value="permissions" scope="request" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <title>${act.equals("new") ? "Add New Permission" : "Edit Permission"}</title>
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
                    <h4>${act.equals("new") ? "Add New Permission" : "Edit Permission"}</h4>
                    <h6>${act.equals("new") ? "Create a new permission" : "Update existing permission"}</h6>
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

                    <form action="${act.equals('new') ? 'AddNewPermission' : 'EditPermission'}" method="post">
                        <input type="hidden" name="id" value="${p != null ? p.permissionId : ''}">
                        
                        <div class="row">
                            <div class="col-lg-6 col-sm-12">
                                <div class="form-group">
                                    <label>Permission Name</label>
                                    <input type="text" name="permissionName" required value="${p != null ? p.permissionName : permissionName}" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label>Description</label>
                                    <textarea name="permissionDescription" required class="form-control" rows="3">${p != null ? p.description : ''}</textarea>
                                </div>
                            </div>
                            
                            <div class="col-lg-6 col-sm-12">
                                <div class="form-group">
                                    <label>Assign to roles</label>
                                    <div class="table-responsive">
                                        <table class="table">
                                            <thead>
                                                <tr>
                                                    <th>Role</th>
                                                    <th>Assign</th>
                                                </tr>
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
                                                        <td>
                                                            <input type="checkbox" name="role" value="${r.roleId}" ${checked ? 'checked' : ''} style="transform: scale(1.5);">
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>

                            <div class="col-lg-12 mt-3">
                                <button type="submit" class="btn btn-submit me-2">${act.equals("new") ? "Create" : "Update"}</button>
                                <a href="ViewPermissionList" class="btn btn-cancel">Cancel</a>
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

