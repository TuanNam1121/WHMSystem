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

                    /* ===== Chart Section ===== */
                    .chart-card {
                        border: 1px solid #e8ebed;
                        border-radius: 10px;
                        padding: 20px;
                        background: #fff;
                        height: 100%;
                    }

                    .chart-card-title {
                        font-weight: 700;
                        font-size: 14px;
                        color: #333;
                        margin-bottom: 16px;
                        display: flex;
                        align-items: center;
                        gap: 8px;
                    }

                    .chart-card-title i {
                        color: #ff9f43;
                    }

                    /* ===== Top-5 Ranking Table ===== */
                    .ranking-table thead th {
                        background-color: #ff9f43;
                        color: #fff;
                        font-weight: 600;
                        font-size: 12px;
                        text-align: center;
                        vertical-align: middle;
                        white-space: nowrap;
                        border: 1px solid #e8e8e8;
                        padding: 8px 10px;
                    }

                    .ranking-table tbody td {
                        font-size: 12px;
                        text-align: center;
                        vertical-align: middle;
                        border: 1px solid #eee;
                        padding: 7px 10px;
                    }

                    .ranking-table tbody td.td-name {
                        text-align: left;
                    }

                    .ranking-table tbody tr:hover {
                        background-color: #fff5ec;
                    }

                    .rank-badge {
                        display: inline-flex;
                        align-items: center;
                        justify-content: center;
                        width: 24px;
                        height: 24px;
                        border-radius: 50%;
                        font-weight: 700;
                        font-size: 12px;
                        color: #fff;
                    }

                    .rank-1 { background: #f39c12; }
                    .rank-2 { background: #95a5a6; }
                    .rank-3 { background: #cd7f32; }
                    .rank-4 { background: #7367f0; }
                    .rank-5 { background: #00cfe8; }

                    /* ===== No data placeholder ===== */
                    .no-data-placeholder {
                        text-align: center;
                        padding: 40px 20px;
                        color: #aaa;
                    }

                    .no-data-placeholder i {
                        font-size: 40px;
                        margin-bottom: 10px;
                        display: block;
                        color: #ddd;
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
                                    <a href="${pageContext.request.contextPath}/ExportInventorySummary?keyword=${param.keyword}&fromDate=${param.fromDate}&toDate=${param.toDate}"
                                        class="btn btn-export">
                                        <i class="fas fa-file-excel me-1"></i> Export Excel
                                    </a>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-lg-3 col-sm-6 col-12 mb-3">
                                    <div class="inventory-summary-card">
                                        <div class="inventory-summary-icon inventory-purple">
                                            <i class="fas fa-cubes"></i>
                                        </div>
                                        <div>
                                            <p>Total Opening</p>
                                            <h4>
                                                <fmt:formatNumber value="${totalOpeningStock}" pattern="#,##0" />
                                            </h4>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-lg-3 col-sm-6 col-12 mb-3">
                                    <div class="inventory-summary-card">
                                        <div class="inventory-summary-icon inventory-green">
                                            <i class="fas fa-arrow-down"></i>
                                        </div>
                                        <div>
                                            <p>Total Import</p>
                                            <h4>
                                                <fmt:formatNumber value="${totalImportQty}" pattern="#,##0" />
                                            </h4>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-lg-3 col-sm-6 col-12 mb-3">
                                    <div class="inventory-summary-card">
                                        <div class="inventory-summary-icon inventory-red">
                                            <i class="fas fa-arrow-up"></i>
                                        </div>
                                        <div>
                                            <p>Total Export</p>
                                            <h4>
                                                <fmt:formatNumber value="${totalExportQty}" pattern="#,##0" />
                                            </h4>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-lg-3 col-sm-6 col-12 mb-3">
                                    <div class="inventory-summary-card">
                                        <div class="inventory-summary-icon inventory-yellow">
                                            <i class="fas fa-box-open"></i>
                                        </div>
                                        <div>
                                            <p>Total Closing</p>
                                            <h4>
                                                <fmt:formatNumber value="${totalClosingStock}" pattern="#,##0" />
                                            </h4>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- ===== CHARTS + RANKING ===== -->
                            <div class="row mb-3">
                                <!-- Pie Chart -->
                                <div class="col-lg-5 col-sm-12 mb-3">
                                    <div class="chart-card">
                                        <div class="chart-card-title">
                                            <i class="fas fa-chart-pie"></i>
                                            Distribution of Transaction Quantities
                                        </div>
                                        <div style="position: relative; height: 260px; display:flex; align-items:center; justify-content:center;">
                                            <canvas id="transactionPieChart"></canvas>
                                        </div>
                                        <div class="d-flex justify-content-center gap-3 mt-3" style="font-size:12px;">
                                            <span><span style="display:inline-block;width:12px;height:12px;border-radius:50%;background:#28c76f;margin-right:4px;"></span>Import</span>
                                            <span><span style="display:inline-block;width:12px;height:12px;border-radius:50%;background:#ea5455;margin-right:4px;"></span>Export</span>
                                            <span><span style="display:inline-block;width:12px;height:12px;border-radius:50%;background:#ff9f43;margin-right:4px;"></span>Adjustment</span>
                                        </div>
                                    </div>
                                </div>

                                <!-- Top 5 Ranking -->
                                <div class="col-lg-7 col-sm-12 mb-3">
                                    <div class="chart-card">
                                        <div class="chart-card-title">
                                            <i class="fas fa-trophy"></i>
                                            Top 5 Most Imported / Exported Products
                                        </div>

                                        <ul class="nav nav-tabs mb-3" id="rankingTab" role="tablist" style="font-size:13px;">
                                            <li class="nav-item" role="presentation">
                                                <button class="nav-link active" id="tab-import-btn" data-bs-toggle="tab"
                                                    data-bs-target="#tab-import" type="button" role="tab"
                                                    aria-selected="true" style="color:#28a745; font-weight:600;">
                                                    <i class="fas fa-arrow-down me-1"></i> Top Imported
                                                </button>
                                            </li>
                                            <li class="nav-item" role="presentation">
                                                <button class="nav-link" id="tab-export-btn" data-bs-toggle="tab"
                                                    data-bs-target="#tab-export" type="button" role="tab"
                                                    aria-selected="false" style="color:#ea5455; font-weight:600;">
                                                    <i class="fas fa-arrow-up me-1"></i> Top Exported
                                                </button>
                                            </li>
                                        </ul>

                                        <div class="tab-content" id="rankingTabContent">
                                            <!-- Top Import -->
                                            <div class="tab-pane fade show active" id="tab-import" role="tabpanel">
                                                <div class="table-responsive">
                                                    <table class="table ranking-table mb-0">
                                                        <thead>
                                                            <tr>
                                                                <th style="width:40px;">Rank</th>
                                                                <th>SKU</th>
                                                                <th>Product Name</th>
                                                                <th>Unit</th>
                                                                <th>Import Qty</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:choose>
                                                                <c:when test="${not empty top5Import}">
                                                                    <c:forEach items="${top5Import}" var="item" varStatus="v">
                                                                        <tr>
                                                                            <td><span class="rank-badge rank-${v.index + 1}">${v.index + 1}</span></td>
                                                                            <td>${item.sku}</td>
                                                                            <td class="td-name">${item.productName}</td>
                                                                            <td>${item.unit}</td>
                                                                            <td><strong style="color:#28a745;"><fmt:formatNumber value="${item.importStock}" pattern="#,##0" /></strong></td>
                                                                        </tr>
                                                                    </c:forEach>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <tr>
                                                                        <td colspan="5" class="no-data-placeholder">
                                                                            <i class="fas fa-inbox"></i>
                                                                            No import data in this period
                                                                        </td>
                                                                    </tr>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>

                                            <!-- Top Export -->
                                            <div class="tab-pane fade" id="tab-export" role="tabpanel">
                                                <div class="table-responsive">
                                                    <table class="table ranking-table mb-0">
                                                        <thead>
                                                            <tr>
                                                                <th style="width:40px;">Rank</th>
                                                                <th>SKU</th>
                                                                <th>Product Name</th>
                                                                <th>Unit</th>
                                                                <th>Export Qty</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:choose>
                                                                <c:when test="${not empty top5Export}">
                                                                    <c:forEach items="${top5Export}" var="item" varStatus="v">
                                                                        <tr>
                                                                            <td><span class="rank-badge rank-${v.index + 1}">${v.index + 1}</span></td>
                                                                            <td>${item.sku}</td>
                                                                            <td class="td-name">${item.productName}</td>
                                                                            <td>${item.unit}</td>
                                                                            <td><strong style="color:#ea5455;"><fmt:formatNumber value="${item.exportStock}" pattern="#,##0" /></strong></td>
                                                                        </tr>
                                                                    </c:forEach>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <tr>
                                                                        <td colspan="5" class="no-data-placeholder">
                                                                            <i class="fas fa-inbox"></i>
                                                                            No export data in this period
                                                                        </td>
                                                                    </tr>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="card">
                                <div class="card-body">
                                    <form action="${pageContext.request.contextPath}/inventorySummaryReport"
                                        method="get">
                                        <!-- Filter Section -->
                                        <div class="card mb-0" id="filter_inputs" style="display: block !important;">
                                            <div class="card-body pb-0">
                                                <div class="row">
                                                    <div class="col-lg-12 col-sm-12">
                                                        <div class="row">
                                                            <div class="col-lg col-sm-6 col-12">
                                                                <div class="form-group">
                                                                    <label>From Date</label>
                                                                    <div class="input-groupicon">
                                                                        <input type="text" name="fromDate"
                                                                            value="${param.fromDate}"
                                                                            placeholder="DD-MM-YYYY"
                                                                            class="datetimepicker">
                                                                        <div class="addonset">
                                                                            <img src="assets/img/icons/calendars.svg"
                                                                                alt="calendar">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg col-sm-6 col-12">
                                                                <div class="form-group">
                                                                    <label>To Date</label>
                                                                    <div class="input-groupicon">
                                                                        <input type="text" name="toDate"
                                                                            value="${param.toDate}"
                                                                            placeholder="DD-MM-YYYY"
                                                                            class="datetimepicker">
                                                                        <div class="addonset">
                                                                            <img src="assets/img/icons/calendars.svg"
                                                                                alt="calendar">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg col-sm-6 col-12">
                                                                <div class="form-group">
                                                                    <label>Search</label>
                                                                    <input type="text" name="keyword"
                                                                        value="${param.keyword}"
                                                                        placeholder="Code or product name...">
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-1 col-sm-6 col-12">
                                                                <div class="form-group">
                                                                    <label>&nbsp;</label>
                                                                    <button type="submit"
                                                                        class="btn btn-filters ms-auto"
                                                                        style="border: none; padding: 0;">
                                                                        <img src="assets/img/icons/search-whites.svg"
                                                                            alt="img">
                                                                    </button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <!-- Report Table -->
                                        <div class="table-responsive" id="inventory-summary-table" tabindex="-1">
                                            <table class="table report-summary-table">
                                                <thead>
                                                    <tr>
                                                        <th rowspan="2">No.</th>
                                                        <th rowspan="2">SKU</th>
                                                        <th rowspan="2">Product Name</th>
                                                        <th rowspan="2">Category</th>
                                                        <th rowspan="2">Unit</th>
                                                        <th colspan="1">Opening</th>
                                                        <th colspan="2">During Period</th>
                                                        <th colspan="1">Closing</th>
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
                                                            <td>${item.sku}</td>
                                                            <td>
                                                                <a href="${pageContext.request.contextPath}/inventorySummaryDetail?productId=${item.productId}&fromDate=${param.fromDate}&toDate=${param.toDate}"
                                                                    class="text-dark fw-bold"
                                                                    style="text-decoration: none;"
                                                                    title="View import/export transactions history">
                                                                    ${item.productName}
                                                                </a>
                                                            </td>
                                                            <td>${item.category}</td>
                                                            <td>${item.unit}</td>
                                                            <td>
                                                                <fmt:formatNumber value="${item.openingStock}"
                                                                    pattern="#,##0" />
                                                            </td>
                                                            <td>
                                                                <fmt:formatNumber value="${item.importStock}"
                                                                    pattern="#,##0" />
                                                            </td>
                                                            <td>
                                                                <fmt:formatNumber value="${item.exportStock}"
                                                                    pattern="#,##0" />
                                                            </td>
                                                            <td>
                                                                <fmt:formatNumber value="${item.closingStock}"
                                                                    pattern="#,##0" />
                                                            </td>
                                                        </tr>
                                                    </c:forEach>

                                                    <c:if test="${empty reportList}">
                                                        <tr>
                                                            <td colspan="9" class="text-center py-4">
                                                                <p class="mb-0" style="color: #999;">No data to display
                                                                </p>
                                                            </td>
                                                        </tr>
                                                    </c:if>
                                                </tbody>
                                            </table>
                                        </div>

                                        <jsp:include page="/WEB-INF/common/pagination.jsp" />
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
                <c:if test="${focusTable}">
                    <script>
                        window.addEventListener("load", function () {
                            const table = document.getElementById("inventory-summary-table");
                            table.scrollIntoView({ behavior: "smooth", block: "start" });
                            table.focus({ preventScroll: true });
                        });
                    </script>
                </c:if>
                <script>
                    document.querySelector('form[action$="inventorySummaryReport"]').addEventListener('submit', function (e) {
                        var fromDateStr = document.querySelector('input[name="fromDate"]').value;
                        var toDateStr = document.querySelector('input[name="toDate"]').value;

                        if (fromDateStr && toDateStr) {
                            var fromParts = fromDateStr.split('-');
                            var toParts = toDateStr.split('-');

                            if (fromParts.length === 3 && toParts.length === 3) {
                                var fromDate = new Date(fromParts[2], fromParts[1] - 1, fromParts[0]);
                                var toDate = new Date(toParts[2], toParts[1] - 1, toParts[0]);

                                if (fromDate > toDate) {
                                    e.preventDefault();
                                    Swal.fire({
                                        title: 'Invalid Date Range!',
                                        text: 'From Date cannot be after To Date.',
                                        icon: 'error',
                                        type: 'error',
                                        confirmButtonClass: 'btn btn-primary'
                                    });
                                }
                            }
                        }
                    });
                </script>
                <script>
                    (function () {
                        var s = document.createElement('script');
                        s.src = 'https://cdn.jsdelivr.net/npm/chart.js@4.4.0/dist/chart.umd.min.js';
                        s.onerror = function () {
                            window.__chartJsFailed = true;
                            var canvas = document.getElementById('transactionPieChart');
                            if (canvas) {
                                canvas.parentNode.innerHTML =
                                    '<div style="text-align:center;color:#aaa;padding:40px 0;">' +
                                    '<i class="fas fa-exclamation-circle" style="font-size:36px;color:#f39c12;display:block;margin-bottom:8px;"></i>' +
                                    'Cannot load chart (check network connection)' +
                                    '</div>';
                            }
                        };
                        document.head.appendChild(s);
                    })();
                </script>
                <script>
                    function initPieChart() {
                        if (window.__chartJsFailed) return;

                        var importQty = parseInt('${totalImportQty != null ? totalImportQty : 0}') || 0;
                        var exportQty = parseInt('${totalExportQty != null ? totalExportQty : 0}') || 0;
                        var adjustQty = parseInt('${totalAdjustQty != null ? totalAdjustQty : 0}') || 0;

                        var total = importQty + exportQty + adjustQty;
                        var canvas = document.getElementById('transactionPieChart');
                        if (!canvas) return;

                        if (total === 0) {
                            canvas.parentNode.innerHTML =
                                '<div style="text-align:center;color:#aaa;padding:40px 0;">' +
                                '<i class="fas fa-chart-pie" style="font-size:40px;color:#eee;display:block;margin-bottom:10px;"></i>' +
                                'No data to display chart' +
                                '</div>';
                            return;
                        }

                        var ctx = canvas.getContext('2d');
                        new Chart(ctx, {
                            type: 'doughnut',
                            data: {
                                labels: ['Import', 'Export', 'Adjustment'],
                                datasets: [{
                                    data: [importQty, exportQty, adjustQty],
                                    backgroundColor: ['#28c76f', '#ea5455', '#ff9f43'],
                                    borderColor: ['#fff', '#fff', '#fff'],
                                    borderWidth: 3,
                                    hoverOffset: 8
                                }]
                            },
                            options: {
                                responsive: true,
                                maintainAspectRatio: false,
                                cutout: '65%',
                                plugins: {
                                    legend: { display: false },
                                    tooltip: {
                                        callbacks: {
                                            label: function (context) {
                                                var label = context.label || '';
                                                var value = context.parsed;
                                                var pct = total > 0 ? ((value / total) * 100).toFixed(1) : 0;
                                                return ' ' + label + ': ' + value.toLocaleString() + ' (' + pct + '%)';
                                            }
                                        }
                                    }
                                }
                            }
                        });
                    }

                    (function waitForChartJs() {
                        var scripts = document.head.querySelectorAll('script[src*="chart.js"]');
                        if (scripts.length === 0) {
                            setTimeout(waitForChartJs, 50);
                            return;
                        }
                        var tag = scripts[scripts.length - 1];
                        if (tag.readyState === 'loaded' || tag.readyState === 'complete') {
                            initPieChart();
                        } else {
                            tag.onload = initPieChart;
                            tag.onreadystatechange = function () {
                                if (this.readyState === 'loaded' || this.readyState === 'complete') initPieChart();
                            };
                        }
                    })();
                </script>
            </body>

            </html>