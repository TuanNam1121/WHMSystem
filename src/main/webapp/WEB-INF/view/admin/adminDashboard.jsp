<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="activeMenu" value="dashboard" scope="request"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <meta name="description" content="POS - Bootstrap Admin Template">
    <meta name="keywords"
          content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
    <meta name="author" content="Dreamguys - Bootstrap Admin Template">
    <meta name="robots" content="noindex, nofollow">
    <title>Admin Dashboard - WHM System</title>

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
                    <h4>Admin Dashboard</h4>
                    <h6>Reset Password Request From Staff</h6>
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

                    <form action="AdminDashBoard" method="GET">
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>User Id</th>
                                    <th>Created At</th>
                                    <th>Status</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${requestlist}" var="r">
                                    <tr>
                                        <td>${r.userId}</td>
                                        <td>${r.createdAt}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${r.status == 'NEW'}">
                                                    <span class="badges bg-lightyellow" style="min-width: 100px; display: inline-block; text-align: center;">${r.status}</span>
                                                </c:when>
                                                <c:when test="${r.status == 'COMPLETED'}">
                                                    <span class="badges bg-lightgreen" style="min-width: 100px; display: inline-block; text-align: center;">${r.status}</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badges bg-lightgrey" style="min-width: 100px; display: inline-block; text-align: center;">${r.status}</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:if test="${r.status == 'NEW'}">
                                                <a class="me-3"
                                                   href="solveRequest?userid=${r.userId}">
                                                    <img src="assets/img/icons/edit.svg" alt="img"> Solve
                                                </a>
                                            </c:if>
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

