<%--
  Created by IntelliJ IDEA.
  User: tung
  Date: 9/6/26
  Time: 14:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <title>Product Details - WHM System</title>

    <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.jpg">

    <link rel="stylesheet" href="assets/css/bootstrap.min.css">

    <link rel="stylesheet" href="assets/css/animate.css">

    <link rel="stylesheet" href="assets/plugins/select2/css/select2.min.css">

    <link rel="stylesheet" href="assets/css/bootstrap-datetimepicker.min.css">

    <link rel="stylesheet" href="assets/plugins/owlcarousel/owl.carousel.min.css">

    <link rel="stylesheet" href="assets/css/dataTables.bootstrap4.min.css">

    <link rel="stylesheet" href="assets/plugins/fontawesome/css/fontawesome.min.css">
    <link rel="stylesheet" href="assets/plugins/fontawesome/css/all.min.css">

    <link rel="stylesheet" href="assets/css/style.css?v=product-detail-image-match">
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
                    <h4>Product Details</h4>
                    <h6>Full details of a product</h6>
                </div>

                <div class="page-btn">
                    <a href="productlist" class="btn btn-cancel">
                        <i class="fas fa-arrow-left me-2"></i>Back to List
                    </a>
                </div>
            </div>

            <div class="row product-detail-summary-row">
                <div class="col-lg-8 col-sm-12">
                    <div class="card product-detail-info-card">
                        <div class="card-body">
                            <div class="productdetails">
                                <ul class="product-bar">
                                    <li>
                                        <h4>Product</h4>
                                        <h6>${sessionScope.product.name}</h6>
                                    </li>
                                    <li>
                                        <h4>Category</h4>
                                        <h6>${sessionScope.product.category.name}</h6>
                                    </li>
                                    <li>
                                        <h4>Brand</h4>
                                        <h6>${sessionScope.product.brand.name}</h6>
                                    </li>
                                    <li>
                                        <h4>Unit</h4>
                                        <h6>${sessionScope.product.unit.name}</h6>
                                    </li>
                                    <li>
                                        <h4>SKU</h4>
                                        <h6>${sessionScope.product.sku}</h6>
                                    </li>
                                    <li>
                                        <h4>Status</h4>
                                        <h6>${product.isActive ? "Active" : "Inactive"}</h6>
                                    </li>
                                    <li>
                                        <h4>Description</h4>
                                        <h6>${sessionScope.product.description}</h6>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4 col-sm-12">
                    <div class="card product-detail-image-card">
                        <div class="card-body">
                            <div class="slider-product-details">
                                <div class="owl-carousel owl-theme product-slide">
                                    <div class="slider-product">
                                        <img src="${sessionScope.product.imgUrl}" alt="img">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="card">
                <div class="card-body">

                    <form action="productDetails" method="get">
                        <input type="hidden" name="productId" value="${param.productId}">
                        <div class="card mb-0" id="filter_inputs" style="display: block !important;">
                            <div class="card-body pb-0">
                                <div class="row">
                                    <div class="col-lg-12 col-sm-12">
                                        <div class="row">

                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <input type="text" name="serial" value="${param.serial}"
                                                           placeholder="Search serial...">
                                                </div>
                                            </div>

                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <div class="input-groupicon">
                                                        <input type="text" name="date" value="${param.date}"
                                                               placeholder="DD-MM-YYYY" class="datetimepicker">
                                                        <div class="addonset">
                                                            <img src="assets/img/icons/calendars.svg" alt="calendar">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <select class="select" name="status">
                                                        <option value="">Choose Status</option>
                                                        <option value="AVAILABLE" ${param.status == 'AVAILABLE' ? 'selected' : ''}>
                                                            Available
                                                        </option>
                                                        <option value="UNAVAILABLE" ${param.status == 'UNAVAILABLE' ? 'selected' : ''}>
                                                            Unavailable
                                                        </option>
                                                        <option value="SOLD" ${param.status == 'SOLD' ? 'selected' : ''}>
                                                            Sold
                                                        </option>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <select class="select" name="sortBy">
                                                        <option value="">Sort By</option>
                                                        <option value="serialAZ" ${param.sortBy == 'serialAZ' ? 'selected' : ''}>
                                                            Serial: A-Z
                                                        </option>
                                                        <option value="serialZA" ${param.sortBy == 'serialZA' ? 'selected' : ''}>
                                                            Serial: Z-A
                                                        </option>
                                                        <option value="dateNewest" ${param.sortBy == 'dateNewest' ? 'selected' : ''}>
                                                            Date: Newest
                                                        </option>
                                                        <option value="dateOldest" ${param.sortBy == 'dateOldest' ? 'selected' : ''}>
                                                            Date: Oldest
                                                        </option>
                                                        <option value="importPriceLow" ${param.sortBy == 'importPriceLow' ? 'selected' : ''}>
                                                            Import price: Low to high
                                                        </option>
                                                        <option value="importPriceHigh" ${param.sortBy == 'importPriceHigh' ? 'selected' : ''}>
                                                            Import price: High to low
                                                        </option>
                                                        <option value="exportPriceLow" ${param.sortBy == 'exportPriceLow' ? 'selected' : ''}>
                                                            Export price: Low to high
                                                        </option>
                                                        <option value="exportPriceHigh" ${param.sortBy == 'exportPriceHigh' ? 'selected' : ''}>
                                                            Export price: High to low
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

                        <div class="table-responsive" id="product-item-table" tabindex="-1">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>No</th>
                                    <th>Serial Number</th>
                                    <th>Barcode</th>
                                    <th>Imported Date</th>
                                    <th>Imported Price</th>
                                    <th>Export Price</th>
                                    <th>Status</th>
                                </tr>
                                </thead>
                                <tbody>

                                <c:forEach items="${sessionScope.productItemList}" var="p" varStatus="v">
                                    <tr>
                                        <td>${v.index+1}</td>
                                        <td>${p.serial}</td>
                                        <td>
                                            <c:if test="${not empty p.serial}">
                                                <c:url var="barcodeUrl" value="/barcode">
                                                    <c:param name="code" value="${p.serial}"/>
                                                </c:url>
                                                <img src="${barcodeUrl}" alt="Barcode"
                                                     style="width: 160px; height: 48px; object-fit: contain;">
                                            </c:if>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${p.importAt}" pattern="dd-MM-yyyy HH:mm:ss"/>
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${p.importPrice}" pattern="#,###"/>
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${p.exportPrice}" pattern="#,###"/>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${p.status == 'AVAILABLE'}">
                                                    <span class="badges bg-lightgreen">Available</span>
                                                </c:when>
                                                <c:when test="${p.status == 'SOLD'}">
                                                    <span class="badges bg-lightgrey">Sold</span>
                                                </c:when>
                                                <c:when test="${p.status == 'UNAVAILABLE'}">
                                                    <span class="badges bg-lightred">Unavailable</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badges bg-lightgrey">${p.status}</span>
                                                </c:otherwise>
                                            </c:choose>
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
<script src="assets/js/moment.min.js"></script>
<script src="assets/js/bootstrap-datetimepicker.min.js"></script>
<script src="assets/plugins/owlcarousel/owl.carousel.min.js"></script>
<script src="assets/plugins/select2/js/select2.min.js"></script>
<script src="assets/js/script.js"></script>
<c:if test="${focusTable}">
    <script>
        window.addEventListener("load", function () {
            const table = document.getElementById("product-item-table");
            table.scrollIntoView({behavior: "smooth", block: "start"});
            table.focus({preventScroll: true});
        });
    </script>
</c:if>
</body>
</html>
</body>
</html>
