<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <title>Dreams Pos admin template</title>

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
                    <h4>Category list</h4>
                    <h6>View/Search product Category</h6>
                </div>
                <div class="page-btn">
                    <c:if test="${sessionScope.userPermissions.contains('CREATE_CATEGORY')}">
                        <a href="addCategory" class="btn btn-added">
                            <img src="assets/img/icons/plus.svg" class="me-1" alt="img">Add Category
                        </a>
                    </c:if>
                </div>
            </div>

            <div class="card">
                <div class="card-body">
                    <c:if test="${not empty sessionScope.error}">
                        <div class="alert alert-warning alert-dismissible fade show" role="alert">
                            <strong>${sessionScope.error}</strong>
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                        <% session.removeAttribute("error"); %>
                    </c:if>
                    <form action="categoryList" method="get">
                        <div class="card mb-0" id="filter_inputs" style="display: block !important;">
                            <div class="card-body pb-0">
                                <div class="row">
                                    <div class="col-lg-12 col-sm-12">
                                        <div class="row">

                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <input type="text" name="keyword" value="${param.keyword}"
                                                           placeholder="Search category name / description...">
                                                </div>
                                            </div>

                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <select class="select" name="isActive">
                                                        <option value="">Choose Status</option>
                                                        <option value="1" ${param.isActive == '1' ? 'selected' : ''}>Active</option>
                                                        <option value="0" ${param.isActive == '0' ? 'selected' : ''}>Inactive</option>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <select class="select" name="sortBy">
                                                        <option value="">Sort By</option>
                                                        <option value="nameAZ" ${param.sortBy == 'nameAZ' ? 'selected' : ''}>
                                                            Name A-Z
                                                        </option>
                                                        <option value="nameZA" ${param.sortBy == 'nameZA' ? 'selected' : ''}>
                                                            Name Z-A
                                                        </option>
                                                        <option value="active" ${param.sortBy == 'active' ? 'selected' : ''}>
                                                            Active first
                                                        </option>
                                                        <option value="inactive" ${param.sortBy == 'inactive' ? 'selected' : ''}>
                                                            Inactive first
                                                        </option>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="col-lg-1 col-sm-6 col-12">
                                                <div class="form-group">
                                                    <button type="submit" class="btn btn-filters ms-auto"
                                                            style="border: none; padding: 0;">
                                                        <img src="assets/img/icons/search-whites.svg" alt="img">
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="table-responsive" id="category-table" tabindex="-1">
                            <table class="table">
                            <thead>
                            <tr>
                                <th>No</th>
                                <th>Category name</th>
                                <th>Description</th>
                                <th>Active</th>
                                <c:if test="${sessionScope.userPermissions.contains('UPDATE_CATEGORY')}">
                                    <th>Action</th>
                                </c:if>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach items="${sessionScope.searchedCategoryList}" var="c" varStatus="v">
                                <tr>
                                    <td>${v.index + 1}</td>
                                    <td>
                                        <a href="javascript:void(0);">${c.name}</a>
                                    </td>
                                    <td>${c.description}</td>
                                    <td>${c.isActive ? 'Active' : 'Inactive'}</td>
                                    <c:if test="${sessionScope.userPermissions.contains('UPDATE_CATEGORY')}">
                                        <td>
                                            <a class="me-3" href="updateCategory?cateid=${c.categoryId}">
                                                <img src="assets/img/icons/edit.svg" alt="img">
                                            </a>
                                        </td>
                                    </c:if>
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
