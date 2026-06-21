<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <h4>Brand List</h4>
                    <h6>Manage your Brand</h6>
                </div>
                <div class="page-btn">
                    <a href="${pageContext.request.contextPath}/AddBrand" class="btn btn-added"><img
                            src="assets/img/icons/plus.svg" class="me-2"
                            alt="img">Add Brand</a>
                </div>
            </div>

            <div class="card">
                <div class="card-body">
                    <form action="brandList" method="get">
                        <div class="card mb-0" id="filter_inputs" style="display: block !important;">
                            <div class="card-body pb-0">
                                <div class="row">
                                    <div class="col-lg-12 col-sm-12">
                                        <div class="row">

                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <input type="text" name="keyword" value="${param.keyword}"
                                                           placeholder="Search brand name / description...">
                                                </div>
                                            </div>


                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <select class="select" name="sortBy">
                                                        <option value="">Sort By</option>
                                                        <option value="nameAZ" ${param.sortBy == 'nameAZ' ? 'selected' : ''}>
                                                            Name: A-Z
                                                        </option>
                                                        <option value="nameZA" ${param.sortBy == 'nameZA' ? 'selected' : ''}>
                                                            Name: Z-A
                                                        </option>
                                                        <option value="descriptionAZ" ${param.sortBy == 'descriptionAZ' ? 'selected' : ''}>
                                                            Description: A-Z
                                                        </option>
                                                        <option value="descriptionZA" ${param.sortBy == 'descriptionZA' ? 'selected' : ''}>
                                                            Description: Z-A
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
                        <div class="table-responsive" id="brand-table" tabindex="-1">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>No</th>
                                    <th>Image</th>
                                    <th>Brand Name</th>
                                    <th>Brand Description</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${sessionScope.brandList}" var="b" varStatus="v">
                                    <tr>
                                        <td>${v.index + 1}</td>
                                        <td>
                                            <a class="product-img">
                                                <img src="${b.img}" alt="product">
                                            </a>
                                        </td>
                                        <td>
                                            <a href="javascript:void(0);">${b.name}</a>
                                        </td>
                                        <td style="max-width: 400px; overflow: hidden">${b.description}</td>
                                        <td>
                                            <a class="me-3"
                                               href="${pageContext.request.contextPath}/BrandDetail?id=${b.id}">
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
<c:if test="${focusTable}">
    <script>
        window.addEventListener("load", function () {
            const table = document.getElementById("brand-table");
            table.scrollIntoView({behavior: "smooth", block: "start"});
            table.focus({preventScroll: true});
        });
    </script>
</c:if>
</body>
</html>
