<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <title>Inventory Summary Report</title>

    <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.jpg">
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/animate.css">
    <link rel="stylesheet" href="assets/plugins/select2/css/select2.min.css">
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

        .report-summary-table tbody td:nth-child(3) {
            text-align: left;
        }

        .report-summary-table tbody tr:hover {
            background-color: #fff5ec;
        }

        .report-header-info {
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
            align-items: flex-end;
            margin-bottom: 20px;
        }

        .report-header-info .form-group {
            margin-bottom: 0;
        }

        .report-header-info label {
            font-weight: 600;
            font-size: 13px;
            margin-bottom: 5px;
            color: #333;
        }

        .report-title-section {
            text-align: center;
            margin-bottom: 25px;
        }

        .report-title-section h3 {
            font-weight: 700;
            color: #333;
            text-transform: uppercase;
            margin-bottom: 5px;
        }

        .report-title-section p {
            color: #777;
            font-size: 14px;
        }

        .btn-export {
            background-color: #28a745;
            color: #fff;
            border: none;
            padding: 8px 20px;
            border-radius: 5px;
            font-size: 13px;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .btn-export:hover {
            background-color: #218838;
            color: #fff;
        }

        .btn-print {
            background-color: #17a2b8;
            color: #fff;
            border: none;
            padding: 8px 20px;
            border-radius: 5px;
            font-size: 13px;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .btn-print:hover {
            background-color: #138496;
            color: #fff;
        }

        .status-badge {
            padding: 4px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 500;
        }

        .status-active {
            background-color: #e6f9ee;
            color: #28a745;
        }

        .status-inactive {
            background-color: #fce4e4;
            color: #e74c3c;
        }

        .status-warning {
            background-color: #fff8e1;
            color: #f39c12;
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
                    <h4>Inventory Summary Report</h4>
                    <h6>View inventory import/export summary report</h6>
                </div>
                <div class="page-btn">
                    <button onclick="window.print();" class="btn btn-print me-2">
                        <i class="fas fa-print me-1"></i> Print Report
                    </button>
                    <a href="${pageContext.request.contextPath}/ExportInventorySummary" class="btn btn-export">
                        <i class="fas fa-file-excel me-1"></i> Export Excel
                    </a>
                </div>
            </div>

            <div class="card">
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/InventorySummaryReport" method="get">
                        <!-- Filter Section -->
                        <div class="card mb-0" id="filter_inputs" style="display: block !important;">
                            <div class="card-body pb-0">
                                <div class="row">
                                    <div class="col-lg-12 col-sm-12">
                                        <div class="row">
                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <label>From Date</label>
                                                    <input type="date" name="fromDate" value="${param.fromDate}" class="form-control">
                                                </div>
                                            </div>
                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <label>To Date</label>
                                                    <input type="date" name="toDate" value="${param.toDate}" class="form-control">
                                                </div>
                                            </div>
                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <label>Search</label>
                                                    <input type="text" name="keyword" value="${param.keyword}" placeholder="Code or product name...">
                                                </div>
                                            </div>
                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <label>Status</label>
                                                    <select class="select" name="status">
                                                        <option value="">All</option>
                                                        <option value="active" ${param.status == 'active' ? 'selected' : ''}>Active</option>
                                                        <option value="inactive" ${param.status == 'inactive' ? 'selected' : ''}>Inactive</option>
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

                        <!-- Report Title -->
                        <div class="report-title-section mt-3">
                            <h3>Inventory Summary Report</h3>
                            <p>
                                <c:choose>
                                    <c:when test="${not empty fromDate && not empty toDate}">
                                        From <fmt:formatDate value="${fromDate}" pattern="dd/MM/yyyy"/> 
                                        to <fmt:formatDate value="${toDate}" pattern="dd/MM/yyyy"/>
                                    </c:when>
                                    <c:otherwise>
                                        All Time
                                    </c:otherwise>
                                </c:choose>
                            </p>
                        </div>

                        <!-- Report Table -->
                        <div class="table-responsive">
                            <table class="table report-summary-table">
                                <thead>
                                    <tr>
                                        <th rowspan="2">No.</th>
                                        <th rowspan="2">Product Code</th>
                                        <th rowspan="2">Product Name</th>
                                        <th rowspan="2">Status</th>
                                        <th rowspan="2">Unit</th>
                                        <th colspan="1">Opening</th>
                                        <th colspan="2">During Period</th>
                                        <th colspan="1">Closing</th>
                                        <th rowspan="2">Note</th>
                                    </tr>
                                    <tr>
                                        <th>Stock</th>
                                        <th>Import</th>
                                        <th>Export</th>
                                        <th>Stock</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${reportList}" var="item" varStatus="v">
                                        <tr>
                                            <td>${(page - 1) * pageSize + v.index + 1}</td>
                                            <td>${item.productCode}</td>
                                            <td>${item.productName}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${item.status == 'ACTIVE'}">
                                                        <span class="status-badge status-active">Active</span>
                                                    </c:when>
                                                    <c:when test="${item.status == 'LOW_STOCK'}">
                                                        <span class="status-badge status-warning">Low Stock</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="status-badge status-inactive">Inactive</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${item.unit}</td>
                                            <td><fmt:formatNumber value="${item.openingStock}" pattern="#,##0"/></td>
                                            <td><fmt:formatNumber value="${item.importQty}" pattern="#,##0"/></td>
                                            <td><fmt:formatNumber value="${item.exportQty}" pattern="#,##0"/></td>
                                            <td><fmt:formatNumber value="${item.closingStock}" pattern="#,##0"/></td>
                                            <td>${item.note}</td>
                                        </tr>
                                    </c:forEach>

                                    <c:if test="${empty reportList}">
                                        <tr>
                                            <td colspan="10" class="text-center py-4">
                                                <p class="mb-0" style="color: #999;">No data to display</p>
                                            </td>
                                        </tr>
                                    </c:if>
                                </tbody>
                                <c:if test="${not empty reportList}">
                                    <tfoot>
                                        <tr style="font-weight: 700; background-color: #f8f9fa;">
                                            <td colspan="5" style="text-align: right;">Total</td>
                                            <td><fmt:formatNumber value="${totalOpeningStock}" pattern="#,##0"/></td>
                                            <td><fmt:formatNumber value="${totalImportQty}" pattern="#,##0"/></td>
                                            <td><fmt:formatNumber value="${totalExportQty}" pattern="#,##0"/></td>
                                            <td><fmt:formatNumber value="${totalClosingStock}" pattern="#,##0"/></td>
                                            <td></td>
                                        </tr>
                                    </tfoot>
                                </c:if>
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
