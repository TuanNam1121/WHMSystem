<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <title>Import / Export Transactions History</title>

    <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.jpg">
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/animate.css">
    <link rel="stylesheet" href="assets/plugins/select2/css/select2.min.css">
    <link rel="stylesheet" href="assets/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" href="assets/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" href="assets/plugins/fontawesome/css/fontawesome.min.css">
    <link rel="stylesheet" href="assets/plugins/fontawesome/css/all.min.css">
    <link rel="stylesheet" href="assets/css/style.css">

    <style>
        .report-summary-table thead th {
            text-align: center;
            vertical-align: middle;
            background-color: #ff9f43;
            color: #fff;
            font-weight: 600;
            font-size: 13px;
            white-space: nowrap;
            border: 1px solid #e8e8e8;
        }

        .report-summary-table tbody td {
            text-align: center;
            vertical-align: middle;
            font-size: 13px;
            border: 1px solid #e8e8e8;
        }

        .report-summary-table tbody tr:hover {
            background-color: #fff5ec;
        }

        .btn-back {
            background-color: #6c757d;
            color: #fff;
            border: none;
            padding: 8px 20px;
            border-radius: 5px;
            font-size: 13px;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .btn-back:hover {
            background-color: #5a6268;
            color: #fff;
        }

        .badge-import {
            background-color: #e6f9ee;
            color: #28a745;
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
        }

        .badge-export {
            background-color: #fce4e4;
            color: #e74c3c;
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
        }

        .product-meta-card {
            background-color: #f8f9fa;
            border-left: 4px solid #ff9f43;
            padding: 15px 20px;
            border-radius: 4px;
            margin-bottom: 20px;
        }

        .product-meta-card h5 {
            margin-bottom: 10px;
            font-weight: 700;
            color: #333;
        }

        .product-meta-card .meta-item {
            font-size: 14px;
            color: #555;
            margin-right: 25px;
        }

        .product-meta-card .meta-item strong {
            color: #333;
        }
    </style>
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
                    <h4>Import / Export Transactions History</h4>
                    <h6>Detailed import and export transactions for selected product</h6>
                </div>
                <div class="page-btn">
                    <a href="${pageContext.request.contextPath}/inventorySummaryReport?fromDate=${param.fromDate}&toDate=${param.toDate}" class="btn btn-back">
                        <i class="fas fa-arrow-left me-1"></i> Back to Summary Report
                    </a>
                </div>
            </div>

            <div class="card">
                <div class="card-body">
                    <!-- Product Information Header -->
                    <div class="product-meta-card">
                        <h5>${product.name}</h5>
                        <div class="d-flex flex-wrap">
                            <div class="meta-item"><strong>SKU:</strong> ${product.sku}</div>
                            <div class="meta-item"><strong>Category:</strong> ${product.category.name}</div>
                            <div class="meta-item"><strong>Unit:</strong> ${product.unit.name}</div>
                        </div>
                    </div>

                    <form action="${pageContext.request.contextPath}/inventorySummaryDetail" method="get">
                        <input type="hidden" name="productId" value="${product.productId}">
                        <!-- Filter Section -->
                        <div class="card mb-3" id="filter_inputs" style="display: block !important;">
                            <div class="card-body pb-0">
                                <div class="row">
                                    <div class="col-lg-12 col-sm-12">
                                        <div class="row">
                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <label>From Date</label>
                                                    <div class="input-groupicon">
                                                        <input type="text" name="fromDate" value="${fromDate}"
                                                               placeholder="DD-MM-YYYY" class="datetimepicker">
                                                        <div class="addonset">
                                                            <img src="assets/img/icons/calendars.svg" alt="calendar">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <label>To Date</label>
                                                    <div class="input-groupicon">
                                                        <input type="text" name="toDate" value="${toDate}"
                                                               placeholder="DD-MM-YYYY" class="datetimepicker">
                                                        <div class="addonset">
                                                            <img src="assets/img/icons/calendars.svg" alt="calendar">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <label>Movement Type</label>
                                                    <select name="typeFilter" class="select">
                                                        <option value="ALL" ${typeFilter == 'ALL' ? 'selected' : ''}>All</option>
                                                        <option value="INCREASED" ${typeFilter == 'INCREASED' ? 'selected' : ''}>Import (Increased)</option>
                                                        <option value="DECREASED" ${typeFilter == 'DECREASED' ? 'selected' : ''}>Export (Decreased)</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-lg-1 col-sm-6 col-12">
                                                <div class="form-group">
                                                    <label>&nbsp;</label>
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

                        <!-- Transactions Table -->
                        <div class="table-responsive" id="inventory-summary-detail-table">
                            <table class="table report-summary-table">
                                <thead>
                                    <tr>
                                        <th>No.</th>
                                        <th>Date & Time</th>
                                        <th>Movement Type</th>
                                        <th>Reference Type</th>
                                        <th>Quantity</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${movementList}" var="item" varStatus="v">
                                        <tr>
                                            <td>${v.index + 1}</td>
                                            <td><fmt:formatDate value="${item.createdAt}" pattern="dd-MM-yyyy HH:mm:ss"/></td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${item.type == 'INCREASED'}">
                                                        <span class="badge-import">Import</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge-export">Export</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${item.reference_type}</td>
                                            <td style="font-weight: 600; color: ${item.type == 'INCREASED' ? '#28a745' : '#e74c3c'};">
                                                ${item.type == 'INCREASED' ? '+' : '-'}<fmt:formatNumber value="${item.quantity}" pattern="#,##0"/>
                                            </td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/viewStockMovementDetail?movementId=${item.id}"
                                                   class="btn btn-sm btn-outline-info" title="View Detail">
                                                    <i class="fas fa-eye me-1"></i> View Detail
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                    <c:if test="${empty movementList}">
                                        <tr>
                                            <td colspan="6" class="text-center py-4">
                                                <p class="mb-0" style="color: #999;">No import/export transactions found in this period</p>
                                            </td>
                                        </tr>
                                    </c:if>
                                </tbody>
                                <c:if test="${not empty movementList}">
                                    <tfoot>
                                        <tr style="font-weight: 700; background-color: #f8f9fa;">
                                            <td colspan="4" style="text-align: right;">Total Import:</td>
                                            <td style="text-align: center; color: #28a745;">+<fmt:formatNumber value="${totalImportQty}" pattern="#,##0"/></td>
                                            <td></td>
                                        </tr>
                                        <tr style="font-weight: 700; background-color: #f8f9fa;">
                                            <td colspan="4" style="text-align: right;">Total Export:</td>
                                            <td style="text-align: center; color: #e74c3c;">-<fmt:formatNumber value="${totalExportQty}" pattern="#,##0"/></td>
                                            <td></td>
                                        </tr>
                                    </tfoot>
                                </c:if>
                            </table>
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
<script src="assets/js/moment.min.js"></script>
<script src="assets/js/bootstrap-datetimepicker.min.js"></script>
<script src="assets/plugins/select2/js/select2.min.js"></script>
<script src="assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
<script src="assets/plugins/sweetalert/sweetalerts.min.js"></script>
<script src="assets/js/script.js"></script>
</body>
</html>
