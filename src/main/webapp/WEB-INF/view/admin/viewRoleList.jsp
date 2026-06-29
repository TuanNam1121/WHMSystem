<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="activeMenu" value="roles" scope="request"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <title>Role Management</title>
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
    <jsp:include page="/WEB-INF/common/adminTopbar.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/common/adminSidebar.jsp"></jsp:include>
    <div class="page-wrapper">
        <div class="content">
            <div class="page-header">
                <div class="page-title">
                    <h4>Role List</h4>
                    <h6>Manage Roles</h6>
                </div>
                <div class="page-btn">
                    <a href="${pageContext.request.contextPath}/AddNewRole" class="btn btn-added">
                        <img src="assets/img/icons/plus.svg" alt="img" class="me-1">Add new role
                    </a>
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

                    <form action="${pageContext.request.contextPath}/ViewRoleList" method="get">
                        <div class="card mb-0" id="filter_inputs" style="display: block !important;">
                            <div class="card-body pb-0">
                                <div class="row">
                                    <div class="col-lg-12 col-sm-12">
                                        <div class="row">
                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <input type="text" name="keyword" value="${param.keyword}" placeholder="Search by rolename">
                                                </div>
                                            </div>
                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <select name="sortBy" class="select">
                                                        <option value="">Sort</option>
                                                        <option value="rolename" ${param.sortBy == 'rolename' ? 'selected' : ''}>Role Name</option>
                                                        <option value="isactive" ${param.sortBy == 'isactive' ? 'selected' : ''}>Active</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-lg-1 col-sm-6 col-12">
                                                <div class="form-group">
                                                    <button type="submit" class="btn btn-filters ms-auto" style="border: none; padding: 0;">
                                                        <img src="assets/img/icons/search-whites.svg" alt="img">
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    <div class="table-responsive mt-4">
                        <table class="table">
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
                                    <td>
                                        <c:choose>
                                            <c:when test="${r.isActive}">
                                                <span class="badges bg-lightgreen">Active</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badges bg-lightred">Inactive</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <a class="me-3" href="${pageContext.request.contextPath}/UpdateRole?id=${r.roleId}">
                                            <img src="assets/img/icons/edit.svg" alt="img">
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <jsp:include page="/WEB-INF/common/pagination.jsp"/>
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
