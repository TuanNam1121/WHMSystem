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
    <title>Dreams Pos admin template</title>

    <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.jpg">

    <link rel="stylesheet" href="assets/css/bootstrap.min.css">

    <link rel="stylesheet" href="assets/css/animate.css">

    <link rel="stylesheet" href="assets/plugins/select2/css/select2.min.css">

    <link rel="stylesheet" href="assets/plugins/owlcarousel/owl.carousel.min.css">

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
                    <h4>Product Details</h4>
                    <h6>Full details of a product</h6>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-8 col-sm-12">
                    <div class="card">
                        <div class="card-body">
                            <div class="bar-code-view">
                                <img src="assets/img/barcode1.png" alt="barcode">
                                <a class="printimg">
                                    <img src="assets/img/icons/printer.svg" alt="print">
                                </a>
                            </div>
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
                                        <h4>Quantity</h4>
                                        <h6>${sessionScope.product.totalQuantity}</h6>
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
                    <div class="card">
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

                    <div class="table-top">
                        <div class="search-set">
                            <div class="search-path">
                                <a class="btn btn-filter" id="filter_search">
                                    <img src="assets/img/icons/filter.svg" alt="img">
                                    <span><img src="assets/img/icons/closes.svg" alt="img"></span>
                                </a>
                            </div>
                            <div class="search-input">
                                <a class="btn btn-searchset"><img src="assets/img/icons/search-white.svg" alt="img"></a>
                            </div>
                        </div>


                        <div class="wordset">
                            <ul>
                                <li>
                                    <a data-bs-toggle="tooltip" data-bs-placement="top" title="pdf"><img
                                            src="assets/img/icons/pdf.svg" alt="img"></a>
                                </li>
                                <li>
                                    <a data-bs-toggle="tooltip" data-bs-placement="top" title="excel"><img
                                            src="assets/img/icons/excel.svg" alt="img"></a>
                                </li>
                                <li>
                                    <a data-bs-toggle="tooltip" data-bs-placement="top" title="print"><img
                                            src="assets/img/icons/printer.svg" alt="img"></a>
                                </li>
                            </ul>
                        </div>
                    </div>

                    <div class="table-responsive">
                        <table class="table datanew">
                            <thead>
                            <tr>
                                <th>No</th>
                                <th>Serial Number</th>
                                <th>Imported Date</th>
                                <th>Imported Price</th>
                                <th>Active</th>
                                <th>Status</th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach items="${sessionScope.productItemList}" var="p" varStatus="v">
                                <tr>
                                    <td>${v.index+1}</td>
                                    <td>${p.serial}</td>
                                    <td>
                                        <fmt:formatDate value="${p.importAt}" pattern="dd-MM-yyyy HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${p.importPrice}" pattern="#,###"/>
                                    </td>
                                    <td>${p.active ? "Active" : "Inactive"}</td>
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

<script src="assets/plugins/owlcarousel/owl.carousel.min.js"></script>

<script src="assets/plugins/select2/js/select2.min.js"></script>

<script src="assets/js/script.js"></script>
</body>
</html>
