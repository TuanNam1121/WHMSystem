<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <title>LWMS</title>

    <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.jpg">

    <link rel="stylesheet" href="assets/css/bootstrap.min.css">

    <link rel="stylesheet" href="assets/css/animate.css">

    <link rel="stylesheet" href="assets/plugins/select2/css/select2.min.css">

    <link rel="stylesheet" href="assets/plugins/fontawesome/css/fontawesome.min.css">
    <link rel="stylesheet" href="assets/plugins/fontawesome/css/all.min.css">

    <link rel="stylesheet" href="assets/css/style.css?v=20260621-table-pagination">

    <style>
        .product-list-card .table-top {
            justify-content: flex-end;
        }

        .product-list-card #filter_inputs {
            display: block !important;
        }

    </style>
</head>
<body>
<jsp:include page="/WEB-INF/common/header.jsp"></jsp:include>
<jsp:include page="/WEB-INF/common/sidebar.jsp"></jsp:include>

<div id="global-loader">
    <div class="whirly-loader"></div>
</div>

<div class="main-wrapper">


    <div class="page-wrapper">
        <div class="content">
            <div class="page-header">
                <div class="page-title">
                    <h4>Product List</h4>
                    <h6>Manage your products</h6>
                </div>
                <div class="page-btn">
                    <a href="AddProduct" class="btn btn-added"><img src="assets/img/icons/plus.svg" alt="img"
                                                                    class="me-1">Add New Product</a>
                </div>
            </div>

            <div class="card product-list-card">
                <div class="card-body">

                    <form action="productlist" method="get">
                        <div class="card mb-0" id="filter_inputs" style="display: block !important;">
                            <div class="card-body pb-0">
                                <div class="row">
                                    <div class="col-lg-12 col-sm-12">
                                        <div class="row">

                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <input type="text" name="productName" value="${param.productName}"
                                                           placeholder="Search...">
                                                </div>
                                            </div>

                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <select class="select" name="isActive">
                                                        <option value="">Choose Status</option>
                                                        <option value="1" ${param.isActive == '1' ? 'selected' : ''}>
                                                            Active
                                                        </option>
                                                        <option value="0" ${param.isActive == '0' ? 'selected' : ''}>
                                                            Inactive
                                                        </option>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <select class="select" name="categoryId">
                                                        <option value="">Choose Category</option>
                                                        <c:forEach items="${sessionScope.categoryList}" var="c">
                                                            <option value="${c.categoryId}"
                                                                ${param.categoryId == c.categoryId ? 'selected' : ''}>
                                                                    ${c.name}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <select class="select" name="brandId">
                                                        <option value="">Choose Brand</option>
                                                        <c:forEach items="${sessionScope.brandList}" var="b">
                                                            <option value="${b.id}"
                                                                ${param.brandId == b.id ? 'selected' : ''}>
                                                                    ${b.name}
                                                            </option>
                                                        </c:forEach>
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
                                                        <option value="skuAZ" ${param.sortBy == 'skuAZ' ? 'selected' : ''}>
                                                            SKU A-Z
                                                        </option>
                                                        <option value="skuZA" ${param.sortBy == 'skuZA' ? 'selected' : ''}>
                                                            SKU Z-A
                                                        </option>
                                                        <option value="cateAZ" ${param.sortBy == 'cateAZ' ? 'selected' : ''}>
                                                            Category A-Z
                                                        </option>
                                                        <option value="cateZA" ${param.sortBy == 'cateZA' ? 'selected' : ''}>
                                                            Category Z-A
                                                        </option>
                                                        <option value="brandAZ" ${param.sortBy == 'brandAZ' ? 'selected' : ''}>
                                                            Brand A-Z
                                                        </option>
                                                        <option value="brandZA" ${param.sortBy == 'brandZA' ? 'selected' : ''}>
                                                            Brand Z-A
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

                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Product</th>
                                    <th>SKU</th>
                                    <th>Category</th>
                                    <th>Brand</th>
                                    <th>Qty</th>
                                    <th>Active</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody>

                                <c:forEach items="${sessionScope.productList}" var="p">
                                    <tr>
                                        <td>${p.productId}</td>
                                        <td class="productimgname">
                                            <a href="javascript:void(0);" class="product-img">
                                                <img src="${p.imgUrl}" alt="product">
                                            </a>
                                            <p>${p.name}</p>
                                        </td>
                                        <td>${p.sku}</td>
                                        <td>${p.category.name}</td>
                                        <td>${p.brand.name}</td>
                                        <td>${p.totalQuantity}</td>
                                        <td>${p.isActive ? "Active" : "Inactive"}</td>
                                        <td>

                                            <a class="me-3" href="productDetails?productId=${p.productId}">
                                                <img src="assets/img/icons/eye.svg" alt="img">
                                            </a>
                                            <a class="me-3" href="UpdateProduct?productid=${p.productId}">
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

<script src="assets/js/bootstrap.bundle.min.js"></script>

<script src="assets/plugins/select2/js/select2.min.js"></script>

<script src="assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
<script src="assets/plugins/sweetalert/sweetalerts.min.js"></script>

<script src="assets/js/script.js"></script>
</body>
</html>
