<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <title>Inventory</title>

    <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.jpg">
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/animate.css">
    <link rel="stylesheet" href="assets/plugins/select2/css/select2.min.css">
    <link rel="stylesheet" href="assets/plugins/fontawesome/css/fontawesome.min.css">
    <link rel="stylesheet" href="assets/plugins/fontawesome/css/all.min.css">
    <link rel="stylesheet" href="assets/css/style.css?v=20260621-table-pagination">

    <style>
        .inventory-summary-card {
            border: 1px solid #e8ebed;
            border-radius: 8px;
            padding: 20px;
            background: #ffffff;
            display: flex;
            align-items: center;
            gap: 14px;
            height: 100%;
        }

        .inventory-summary-icon {
            width: 50px;
            height: 50px;
            border-radius: 6px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 22px;
        }

        .inventory-summary-icon.inventory-purple {
            background: #f0edff;
            color: #7367f0;
        }

        .inventory-summary-icon.inventory-yellow {
            background: #fff4cc;
            color: #ff9f43;
        }

        .inventory-summary-icon.inventory-red {
            background: #ffe5e5;
            color: #ea5455;
        }

        .inventory-summary-icon.inventory-green {
            background: #ddf8ec;
            color: #28c76f;
        }

        .inventory-summary-card p {
            margin-bottom: 4px;
            color: #637381;
        }

        .inventory-summary-card h4 {
            margin-bottom: 0;
            font-size: 24px;
        }

        .inventory-out-stock {
            background: #ffe5e5;
        }

        .inventory-low-stock {
            background: #fff4cc;
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
                    <h4>Inventory</h4>
                    <h6>View current stock by product</h6>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-3 col-sm-6 col-12 mb-3">
                    <div class="inventory-summary-card">
                        <div class="inventory-summary-icon inventory-purple">
                            <i class="fas fa-box"></i>
                        </div>
                        <div>
                            <p>Total Products</p>
                            <h4>${totalProducts}</h4>
                        </div>
                    </div>
                </div>

                <div class="col-lg-3 col-sm-6 col-12 mb-3">
                    <div class="inventory-summary-card">
                        <div class="inventory-summary-icon inventory-yellow">
                            <i class="fas fa-exclamation-triangle"></i>
                        </div>
                        <div>
                            <p>Low Stock</p>
                            <h4>${lowStockProducts}</h4>
                        </div>
                    </div>
                </div>

                <div class="col-lg-3 col-sm-6 col-12 mb-3">
                    <div class="inventory-summary-card">
                        <div class="inventory-summary-icon inventory-red">
                            <i class="fas fa-times-circle"></i>
                        </div>
                        <div>
                            <p>Out of Stock</p>
                            <h4>${outOfStockProducts}</h4>
                        </div>
                    </div>
                </div>

                <div class="col-lg-3 col-sm-6 col-12 mb-3">
                    <div class="inventory-summary-card">
                        <div class="inventory-summary-icon inventory-green">
                            <i class="fas fa-dollar-sign"></i>
                        </div>
                        <div>
                            <p>Total Value</p>
                            <h4><fmt:formatNumber value="${totalInventoryValue}" pattern="#,##0"/>đ</h4>
                        </div>
                    </div>
                </div>
            </div>

            <div class="card">
                <div class="card-body">
                    <form action="inventory" method="get">
                        <div class="card mb-0" id="filter_inputs" style="display: block !important;">
                            <div class="card-body pb-0">
                                <div class="row">
                                    <div class="col-lg-12 col-sm-12">
                                        <div class="row">
                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <input type="text" name="keyword" value="${keyword}"
                                                           placeholder="Search product or SKU...">
                                                </div>
                                            </div>

                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <select class="select" name="stockStatus">
                                                        <option value="">Choose Status</option>
                                                        <option value="inStock" ${stockStatus == 'inStock' ? 'selected' : ''}>
                                                            In stock
                                                        </option>
                                                        <option value="lowStock" ${stockStatus == 'lowStock' ? 'selected' : ''}>
                                                            Low stock
                                                        </option>
                                                        <option value="outOfStock" ${stockStatus == 'outOfStock' ? 'selected' : ''}>
                                                            Out of stock
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
                                    <th>No</th>
                                    <th>Product</th>
                                    <th>SKU</th>
                                    <th>Quantity</th>
                                    <th>Unit</th>
                                    <th>Total Value</th>
                                    <th>Status</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${inventoryList}" var="item" varStatus="v">
                                    <tr class="${item.rowClass}">
                                        <td>${(page - 1) * pageSize + v.index + 1}</td>
                                        <td class="productimgname">
                                            <a href="javascript:void(0);" class="product-img">
                                                <img src="${item.imgUrl}" alt="product">
                                            </a>
                                            <p>${item.productName}</p>
                                        </td>
                                        <td>${item.sku}</td>
                                        <td><strong>${item.quantity}</strong></td>
                                        <td>${item.unitName}</td>
                                        <td><strong><fmt:formatNumber value="${item.totalValue}" pattern="#,##0"/>đ</strong></td>
                                        <td>
                                            <span class="badges ${item.statusClass}">${item.stockStatus}</span>
                                        </td>
                                        <td>
                                            <a class="me-3" href="productDetails?productId=${item.productId}">
                                                <img src="assets/img/icons/eye.svg" alt="img">
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>

                                <c:if test="${empty inventoryList}">
                                    <tr>
                                        <td colspan="8" class="text-center">No inventory found.</td>
                                    </tr>
                                </c:if>
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
<script src="assets/js/script.js"></script>
</body>
</html>
