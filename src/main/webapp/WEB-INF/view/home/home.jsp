<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <meta name="description" content="POS - Bootstrap Admin Template">
    <meta name="keywords"
          content="admin, estimates, bootstrap, business, corporate, creative, management, minimal, modern,  html5, responsive">
    <meta name="author" content="Dreamguys - Bootstrap Admin Template">
    <meta name="robots" content="noindex, nofollow">
    <title>Home - WHM System</title>

    <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.jpg">

    <link rel="stylesheet" href="assets/css/bootstrap.min.css">

    <link rel="stylesheet" href="assets/css/animate.css">

    <link rel="stylesheet" href="assets/css/dataTables.bootstrap4.min.css">

    <link rel="stylesheet" href="assets/plugins/fontawesome/css/fontawesome.min.css">
    <link rel="stylesheet" href="assets/plugins/fontawesome/css/all.min.css">

    <link rel="stylesheet" href="assets/css/style.css?v=home-chart-year-compact">
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
            <div class="row">
                <div class="col-lg-3 col-sm-6 col-12">
                    <div class="dash-widget">
                        <div class="dash-widgetimg">
                            <span><img src="assets/img/icons/dash1.svg" alt="img"></span>
                        </div>
                        <div class="dash-widgetcontent">
                            <h5>
                                <span class="counters money-vn"
                                      data-count="${requestScope.newPurchaseOrderTotalPrice}"></span>
                            </h5>
                            <h6>Total Purchase Due</h6>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-sm-6 col-12">
                    <div class="dash-widget dash1">
                        <div class="dash-widgetimg">
                            <span><img src="assets/img/icons/dash2.svg" alt="img"></span>
                        </div>
                        <div class="dash-widgetcontent">
                            <h5>
                                <span class="counters money-vn"
                                      data-count="${requestScope.newSaleOrderTotalPrice}"></span>
                            </h5>
                            <h6>Total Sales Due</h6>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-sm-6 col-12">
                    <div class="dash-widget dash2">
                        <div class="dash-widgetimg">
                            <span><img src="assets/img/icons/dash3.svg" alt="img"></span>
                        </div>
                        <div class="dash-widgetcontent">
                            <h5>
                                <span class="counters money-vn"
                                      data-count="${requestScope.completedImportTotalPrice}"></span>
                            </h5>
                            <h6>Total Import Amount</h6>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-sm-6 col-12">
                    <div class="dash-widget dash3">
                        <div class="dash-widgetimg">
                            <span><img src="assets/img/icons/dash4.svg" alt="img"></span>
                        </div>
                        <div class="dash-widgetcontent">
                            <h5>
                                <span class="counters money-vn"
                                      data-count="${requestScope.completedSaleOrderTotalPrice}"></span>
                            </h5>
                            <h6>Total Export Amount</h6>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-sm-6 col-12 d-flex">
                    <div class="dash-count">
                        <div class="dash-counts">
                            <h4>${requestScope.customerCount}</h4>
                            <h5>Customers</h5>
                        </div>
                        <div class="dash-imgs">
                            <i data-feather="user"></i>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-sm-6 col-12 d-flex">
                    <div class="dash-count das1">
                        <div class="dash-counts">
                            <h4>${requestScope.activeSupplierCount}</h4>
                            <h5>Suppliers</h5>
                        </div>
                        <div class="dash-imgs">
                            <i data-feather="user-check"></i>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-sm-6 col-12 d-flex">
                    <div class="dash-count das2">
                        <div class="dash-counts">
                            <h4>${requestScope.completedPurchaseInvoiceCount}</h4>
                            <h5>Purchase Invoice</h5>
                        </div>
                        <div class="dash-imgs">
                            <i data-feather="file-text"></i>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-sm-6 col-12 d-flex">
                    <div class="dash-count das3">
                        <div class="dash-counts">
                            <h4>${requestScope.completedSaleInvoiceCount}</h4>
                            <h5>Sales Invoice</h5>
                        </div>
                        <div class="dash-imgs">
                            <i data-feather="file"></i>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-7 col-sm-12 col-12 d-flex">
                    <div class="card flex-fill">
                        <div class="card-header pb-0 d-flex justify-content-between align-items-center">
                            <h5 class="card-title mb-0">Purchase & Sales</h5>
                            <div class="graph-sets">
                                <ul>
                                    <li>
                                        <span>Sales</span>
                                    </li>
                                    <li>
                                        <span>Purchase</span>
                                    </li>
                                </ul>
                                <div class="dropdown chart-year-dropdown">
                                    <button class="btn btn-white btn-sm dropdown-toggle" type="button"
                                            id="chartYearDropdown" data-bs-toggle="dropdown"
                                            data-bs-auto-close="outside" aria-expanded="false">
                                        <span class="chart-year-label">${requestScope.chartYear}</span>
                                        <img src="assets/img/icons/dropdown.svg" alt="img" class="ms-2">
                                    </button>
                                    <div class="dropdown-menu chart-year-menu" aria-labelledby="chartYearDropdown"
                                         tabindex="0">
                                        <c:if test="${requestScope.chartYear < requestScope.maxChartYear}">
                                            <a href="home?year=${requestScope.chartYear + 1}"
                                               class="dropdown-item">${requestScope.chartYear + 1}</a>
                                        </c:if>
                                        <a href="home?year=${requestScope.chartYear}"
                                           class="dropdown-item">${requestScope.chartYear}</a>
                                        <c:if test="${requestScope.chartYear > 2021}">
                                            <a href="home?year=${requestScope.chartYear - 1}"
                                               class="dropdown-item">${requestScope.chartYear - 1}</a>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <div id="sales_charts"
                                 data-sales="${requestScope.monthlySalesChartData}"
                                 data-purchase="${requestScope.monthlyPurchaseChartData}"></div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-5 col-sm-12 col-12 d-flex">
                    <div class="card flex-fill">
                        <div class="card-header pb-0 d-flex justify-content-between align-items-center">
                            <h4 class="card-title mb-0">Low Stock Products</h4>
                            <c:if test="${sessionScope.user.roleId != 1 &&
                                          sessionScope.userPermissions.contains('VIEW_INVENTORY')}">
                                <div class="dropdown">
                                    <a href="javascript:void(0);" data-bs-toggle="dropdown" aria-expanded="false"
                                       class="dropset">
                                        <i class="fa fa-ellipsis-v"></i>
                                    </a>
                                    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                        <li>
                                            <a href="inventory?stockStatus=lowStock&sortBy=quantityAsc"
                                               class="dropdown-item">Inventory List</a>
                                        </li>
                                    </ul>
                                </div>
                            </c:if>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive dataview low-stock-scroll">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th>Sno</th>
                                        <th>Products</th>
                                        <th>Stock</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${requestScope.lowStockProducts}" var="item" varStatus="v">
                                        <tr>
                                            <td>${v.index + 1}</td>
                                            <td class="productimgname">
                                                <c:choose>
                                                    <c:when test="${sessionScope.userPermissions.contains('VIEW_PRODUCT')}">
                                                        <a href="productDetails?productId=${item.productId}" class="product-img">
                                                            <img src="${not empty item.imgUrl ? item.imgUrl : 'assets/img/product/product7.jpg'}"
                                                                 alt="product">
                                                        </a>
                                                        <a href="productDetails?productId=${item.productId}">${item.productName}</a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="product-img">
                                                            <img src="${not empty item.imgUrl ? item.imgUrl : 'assets/img/product/product7.jpg'}"
                                                                 alt="product">
                                                        </span>
                                                        <span>${item.productName}</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <span class="low-stock-quantity ${item.statusClass}">${item.quantity}</span>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty requestScope.lowStockProducts}">
                                        <tr>
                                            <td colspan="3" class="text-center">No low stock products</td>
                                        </tr>
                                    </c:if>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card mb-0">
                <div class="card-body">
                    <h4 class="card-title">Top 5 Best Selling Products</h4>
                    <div class="table-responsive dataview">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>No</th>
                                <th>Product</th>
                                <th>Brand</th>
                                <th>Category</th>
                                <th>Total Item Sold</th>
                                <th>Total Price</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${requestScope.topSellingProducts}" var="item" varStatus="v">
                                <tr>
                                    <td>${v.index + 1}</td>
                                    <td class="productimgname">
                                        <c:choose>
                                            <c:when test="${sessionScope.userPermissions.contains('VIEW_PRODUCT')}">
                                                <a class="product-img" href="productDetails?productId=${item.productId}">
                                                    <img src="${not empty item.imgUrl ? item.imgUrl : 'assets/img/product/product7.jpg'}"
                                                         alt="product">
                                                </a>
                                                <a href="productDetails?productId=${item.productId}">${item.productName}</a>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="product-img">
                                                    <img src="${not empty item.imgUrl ? item.imgUrl : 'assets/img/product/product7.jpg'}"
                                                         alt="product">
                                                </span>
                                                <span>${item.productName}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${item.brandName}</td>
                                    <td>${item.categoryName}</td>
                                    <td>${item.totalItemSold}</td>
                                    <td><fmt:formatNumber value="${item.totalPrice}" pattern="#,###"/></td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty requestScope.topSellingProducts}">
                                <tr>
                                    <td colspan="6" class="text-center">No sold products</td>
                                </tr>
                            </c:if>
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

<script src="assets/plugins/apexchart/apexcharts.min.js"></script>
<script src="assets/plugins/apexchart/chart-data.js?v=home-chart-full-tooltip"></script>

<script src="assets/js/script.js"></script>
<script>
    const chartYearDropdown = document.querySelector('.chart-year-dropdown');
    const chartYearMenu = document.querySelector('.chart-year-menu');
    const chartYearLabel = document.querySelector('.chart-year-label');
    const initialChartYear = '${requestScope.chartYear}';
    const minChartYear = 2021;
    const maxChartYear = 9999;
    let typedChartYear = '';

    chartYearDropdown?.addEventListener('shown.bs.dropdown', function () {
        typedChartYear = '';
        chartYearLabel.textContent = initialChartYear;
        chartYearMenu?.focus();
    });

    chartYearDropdown?.addEventListener('hidden.bs.dropdown', function () {
        typedChartYear = '';
        chartYearLabel.textContent = initialChartYear;
    });

    chartYearMenu?.addEventListener('keydown', function (event) {
        if (/^\d$/.test(event.key)) {
            event.preventDefault();
            typedChartYear += event.key;
            chartYearLabel.textContent = typedChartYear;
            return;
        }

        if (event.key === 'Backspace') {
            event.preventDefault();
            typedChartYear = typedChartYear.slice(0, -1);
            chartYearLabel.textContent = typedChartYear || initialChartYear;
            return;
        }

        if (event.key === 'Enter' && typedChartYear) {
            event.preventDefault();
            const selectedYear = Number(typedChartYear);
            if (Number.isInteger(selectedYear) && selectedYear >= minChartYear && selectedYear <= maxChartYear) {
                window.location.href = 'home?year=' + encodeURIComponent(typedChartYear);
                return;
            }
            typedChartYear = '';
            chartYearLabel.textContent = initialChartYear;
        }
    });
</script>
</body>
</html>
